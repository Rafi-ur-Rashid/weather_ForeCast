package com.example.rf.weather_forecast;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class POPup_Details extends AppCompatActivity {
    TextView Max,Min,Day,Desc,Pressure,Date,Humidity,Speed, Location;
    ImageView Icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup__details);
        Bundle bundle = getIntent().getExtras();
        String max = bundle.getString("com.example.rf.weatherupdate.maxTemp");
        String min=bundle.getString("com.example.rf.weatherupdate.minTemp");
        String desc=bundle.getString("com.example.rf.weatherupdate.main");
        String press=bundle.getString("com.example.rf.weatherupdate.pressure");
        String humid=bundle.getString("com.example.rf.weatherupdate.humid");
        String date=bundle.getString("com.example.rf.weatherupdate.date");
        String speed=bundle.getString("com.example.rf.weatherupdate.speed");
        String day=getDay(Long.parseLong(date)*1000L);
        String location=bundle.getString("com.example.rf.weatherupdate.city");
        String MonthDate=getDate(Long.parseLong(date)*1000L);
        Max=(TextView)findViewById(R.id.max);
        Min=(TextView)findViewById(R.id.min);
        Day=(TextView)findViewById(R.id.dayName);
        Date=(TextView)findViewById(R.id.date);
        Desc=(TextView)findViewById(R.id.weather_desc);
        Pressure=(TextView)findViewById(R.id.pressure);
        Humidity=(TextView)findViewById(R.id.humidity);
        Speed=(TextView)findViewById(R.id.wind);
        Icon=(ImageView)findViewById(R.id.weather_icon);
        Location=(TextView)findViewById(R.id.location);
        switch (desc){
            case "Clear":Icon.setImageResource(R.mipmap.today_sunny);
                break;
            case "Rain":Icon.setImageResource(R.mipmap.today_rain);
                break;
            case "Cloud":Icon.setImageResource(R.mipmap.today_cloud);
                break;
            default: break;

        }
        Max.setText(max);
        Min.setText(min);
        Day.setText(day);
        Date.setText(MonthDate);
        Desc.setText(desc);
        Pressure.setText("Pressure: "+press+" hPa");
        Humidity.setText("Humidity: "+humid+" %");
        Speed.setText("Wind: "+speed+" km/H NE");
        Location.setText("Location: "+location);
    }
    String getDate(long mil){

        Calendar cl=Calendar.getInstance();
        cl.setTimeInMillis(mil);
        String month=null;
        switch (cl.get(Calendar.MONTH)+1){
            case 1: month="January";
                break;
            case 2: month="February";
                break;
            case 3: month="March";
                break;
            case 4: month="April";
                break;
            case 5: month="May";
                break;
            case 6: month="June";
                break;
            case 7: month="July";
                break;
            case 8: month="August";
                break;
            case 9: month="September";
                break;
            case 10: month="October";
                break;
            case 12: month="November";
                break;
            case 13: month="December";
                break;
        }
        String date = month+"  " + cl.get(Calendar.DAY_OF_MONTH);
        return date;
    }
    String getDay(long mil){

        Calendar cl=Calendar.getInstance();
        cl.setTimeInMillis(mil);
        String weekDay=null;
        int dayNum=cl.get(Calendar.DAY_OF_WEEK);
        switch (dayNum){
            case 1:weekDay="Sunday";
                break;
            case 2:weekDay="Monday";
                break;
            case 3:weekDay="Tuesday";
                break;
            case 4:weekDay="Wednesday";
                break;
            case 5:weekDay="Thursday";
                break;
            case 6:weekDay="Friday";
                break;
            case 7:weekDay="Saturday";
                break;
            default: break;
        }
        return weekDay;
    }
}

