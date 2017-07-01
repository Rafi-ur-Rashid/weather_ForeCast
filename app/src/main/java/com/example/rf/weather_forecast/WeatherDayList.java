package com.example.rf.weather_forecast;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class WeatherDayList extends AppCompatActivity {
    static SQLiteDatabase sqLiteDatabase;
    ArrayList<MyWeatherData> myList;
    ArrayList<MyWeatherData> tempList;
    weather_offline_Class weather_dat;
    ListView l;
    String url="http://api.openweathermap.org/data/2.5/forecast/daily?APPID=559bb07495558a36c6fa8dc5f8aa8054&q=dhaka&mode=JSON&units=metric&cnt=7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weather_dat=new weather_offline_Class(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_day_list);
        l=(ListView)findViewById(R.id.myList);
            if(sqLiteDatabase!=null)
                offLineUpdate();

        else {
            this.deleteDatabase("MyWeatherDb");
            new updateWeather().execute();
        }
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tempmax=myList.get(position).getMax();
                String max=tempmax;
                if(tempmax.indexOf('.')!=-1)
                    max=tempmax.substring(0,tempmax.indexOf('.'));
                String tempmin=myList.get(position).getMin();
                String min=tempmin.substring(0,tempmin.indexOf('.'));
                String city=myList.get(position).getCity();
                String main=myList.get(position).getMain();
                String Date=myList.get(position).getDt();
                String Humidity=myList.get(position).getHumidity();
                String Pressure=myList.get(position).getPressure();
                String Speed=myList.get(position).getSpeed();
                Intent intent=new Intent(WeatherDayList.this,POPup_Details.class);
                intent.putExtra("com.example.rf.weatherupdate.maxTemp",max);
                intent.putExtra("com.example.rf.weatherupdate.minTemp",min);
                intent.putExtra("com.example.rf.weatherupdate.main",main);
                intent.putExtra("com.example.rf.weatherupdate.humid",Humidity);
                intent.putExtra("com.example.rf.weatherupdate.date",Date);
                intent.putExtra("com.example.rf.weatherupdate.pressure",Pressure);
                intent.putExtra("com.example.rf.weatherupdate.speed",Speed);
                intent.putExtra("com.example.rf.weatherupdate.city",city);
                startActivity(intent);
            }
        });

    }
    String getDataFromUrl(String url){
        String line = "";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL urls = new URL(url);
            connection = (HttpURLConnection) urls.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(stream);
            reader = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line);

            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return line;
    }
    public class updateWeather extends AsyncTask<Void,Void,Void>{
        String data;
        @Override
        protected Void doInBackground(Void... params) {
            data=getDataFromUrl(url);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONparse(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//
            MyAdapter my=new MyAdapter(WeatherDayList.this,myList);
            l.setAdapter(my);

        }

        @Override
        protected void onProgressUpdate(Void... values) {


            super.onProgressUpdate(values);
        }
    }
    void JSONparse(String data) throws JSONException {
        sqLiteDatabase=weather_dat.getWritableDatabase();
        myList=new ArrayList<>();
        tempList=new ArrayList<>();
        JSONObject mainObj=new JSONObject(data);
        JSONObject location=mainObj.getJSONObject("city");
        String cityName=location.getString("name");
        JSONArray daysData=mainObj.getJSONArray("list");
        for (int i=0;i<daysData.length();i++){
            JSONObject eachDay=daysData.getJSONObject(i);
            String date=eachDay.getString("dt");
            String pressure=eachDay.getString("pressure");
            String humid=eachDay.getString("humidity");
            String speed=eachDay.getString("speed");
            JSONObject tempObj = eachDay.getJSONObject("temp");
            String minTemp = tempObj.getString("min");
            String maxTemp = tempObj.getString("max");
            JSONArray weatherData=eachDay.getJSONArray("weather");
            String description=weatherData.getJSONObject(0).getString("description");
            String icon=weatherData.getJSONObject(0).getString("icon");
            String main=weatherData.getJSONObject(0).getString("main");
            MyWeatherData myWeatherData=new MyWeatherData(date,minTemp,maxTemp,humid,speed,pressure,description,icon,main,cityName);
            myList.add(myWeatherData);
            tempList.add(myWeatherData);
            weather_dat.insertData(cityName,date,date,maxTemp,minTemp,humid,pressure,description,icon,main,speed,sqLiteDatabase);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void offLineUpdate(){
        sqLiteDatabase=weather_dat.getWritableDatabase();
        myList=new ArrayList<>();
        tempList=new ArrayList<>();
        MyWeatherData myWeatherData=null;
        for (int i=1;i<=7;i++) {
            myWeatherData =new MyWeatherData(weather_dat.getDay(sqLiteDatabase, i), weather_dat.getMin(sqLiteDatabase, i), weather_dat.getMax(sqLiteDatabase, i),weather_dat.getHumidity(sqLiteDatabase, i) ,weather_dat.getSpeed(sqLiteDatabase, i), weather_dat.getPressure(sqLiteDatabase, i), weather_dat.getDescription(sqLiteDatabase, i), weather_dat.getIcon(sqLiteDatabase, i), weather_dat.getMain(sqLiteDatabase, i), weather_dat.getCity(sqLiteDatabase, i));
            System.out.println("Mytest: "+i+" "+weather_dat.getDay(sqLiteDatabase,i));
            myList.add(myWeatherData);
            tempList.add(myWeatherData);
        }
        MyAdapter my=new MyAdapter(WeatherDayList.this,myList);
        l.setAdapter(my);
    }
    private int getDay(long mil){

        Calendar cl=Calendar.getInstance();
        cl.setTimeInMillis(mil);
        return cl.get(Calendar.DAY_OF_WEEK);
    }
    public void todaysDetail(View view){
        int today=getDay(System.currentTimeMillis());
        for (int i=0;i<6;i++) {
            int dayNum = getDay(Long.parseLong(tempList.get(i).getDt()) * 1000L);
            if (dayNum == today) {
                //Toast.makeText(WeatherDayList.this,max,Toast.LENGTH_SHORT).show();
                String tempmax=tempList.get(i).getMax();
                String max=tempmax;
                if(tempmax.indexOf('.')!=-1)
                    max=tempmax.substring(0,tempmax.indexOf('.'));
                String tempmin=tempList.get(i).getMin();
                String min=tempmin.substring(0,tempmin.indexOf('.'));
                String city=tempList.get(i).getCity();
                String main=tempList.get(i).getMain();
                String Date=tempList.get(i).getDt();
                String Humidity=tempList.get(i).getHumidity();
                String Pressure=tempList.get(i).getPressure();
                String Speed=tempList.get(i).getSpeed();
                Intent intent=new Intent(WeatherDayList.this,POPup_Details.class);
                intent.putExtra("com.example.rf.weatherupdate.maxTemp",max);
                intent.putExtra("com.example.rf.weatherupdate.minTemp",min);
                intent.putExtra("com.example.rf.weatherupdate.main",main);
                intent.putExtra("com.example.rf.weatherupdate.humid",Humidity);
                intent.putExtra("com.example.rf.weatherupdate.date",Date);
                intent.putExtra("com.example.rf.weatherupdate.pressure",Pressure);
                intent.putExtra("com.example.rf.weatherupdate.speed",Speed);
                intent.putExtra("com.example.rf.weatherupdate.city",city);
                startActivity(intent);
                break;
            }
        }
    }
}


