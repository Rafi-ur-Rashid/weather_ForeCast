package com.example.rf.weather_forecast;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<MyWeatherData> list;
    weather_offline_Class weather_dat;
    int today=getDay(System.currentTimeMillis());
    TextView today_max;
    TextView today_min;
    TextView today_weather;
    ImageView today_icon;
    String today_main,icon;
    public MyAdapter(Context c, ArrayList<MyWeatherData> list) {
        this.c = c;
        this.list = list;
        today_max=(TextView)((Activity)c).findViewById(R.id.today_Max);
        today_min=(TextView)((Activity)c).findViewById(R.id.today_Min);
        today_weather=(TextView)((Activity)c).findViewById(R.id.todaysWeather);
        today_icon= (ImageView)((Activity)c).findViewById(R.id.todaysIcon);
        for (int i=0;i<list.size();i++){
            int dayNum=getDay(Long.parseLong(list.get(i).getDt())*1000L);
            if(dayNum==today){
                today_main=list.get(i).getMain();
                today_weather.setText(today_main);
                today_max.setText(list.get(i).getMax());
                today_min.setText(list.get(i).getMin());
                switch (today_main){
                    case "Clear":today_icon.setImageResource(R.mipmap.today_sunny);
                        break;
                    case "Rain":today_icon.setImageResource(R.mipmap.today_rain);
                        break;
                    case "Cloud":today_icon.setImageResource(R.mipmap.today_cloud);
                        break;
                    default: today_icon.setImageResource(R.mipmap.cloud);

                }
                list.remove(i);
                break;
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.list_element,parent,false);
        TextView day=(TextView)convertView.findViewById(R.id.Day);
        TextView weather=(TextView)convertView.findViewById(R.id.weather);
        TextView max=(TextView)convertView.findViewById(R.id.today_Max);
        TextView min=(TextView)convertView.findViewById(R.id.today_Min);
        ImageView icon= (ImageView)convertView.findViewById(R.id.icon);
        String main=list.get(position).getMain();
        int dayNum=getDay(Long.parseLong(list.get(position).getDt())*1000L);
        weather.setText(main);
        max.setText(list.get(position).getMax());
        min.setText(list.get(position).getMin());
        String weekDay=new String();
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

        switch (main){
            case "Clear":icon.setImageResource(R.mipmap.sunny);
                break;
            case "Rain":icon.setImageResource(R.mipmap.rain);
                break;
            case "Cloud":icon.setImageResource(R.mipmap.cloud);
                break;
            default: break;

        }
        if(dayNum==today-1) day.setText("Yesterday");
        else if(dayNum==today+1) day.setText("Tomorrow");
        else
            day.setText(weekDay);

        return convertView;
    }
    int getDay(long mil){

        Calendar cl=Calendar.getInstance();
        cl.setTimeInMillis(mil);
        return cl.get(Calendar.DAY_OF_WEEK);
    }
}

