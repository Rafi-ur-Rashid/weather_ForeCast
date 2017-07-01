package com.example.rf.weather_forecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class weather_offline_Class extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MyWeatherDb";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="Weather_data";
    private static final String ID="_id";
    private static final String CITY="City";
    private static final String DAY="Day";
    private static final String DATE="Date";
    private static final String MIN="Min_temp";
    private static final String MAX="Max_temp";
    private static final String HUMIDITY="Humidity";
    private static final String SPEED="Speed";
    private static final String PRESSURE="Pressure";
    private static final String DESCRIPTION="Description";
    private static final String ICON="Icon";
    private static final String MAIN="Main";
    private static final String CREATE_TABLE_QUERY="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY,"+DAY+" TEXT,"+DATE+" TEXT,"+MIN+" TEXT,"+MAX+" TEXT,"+HUMIDITY+" TEXT,"+SPEED+" TEXT,"+PRESSURE+" TEXT,"+MAIN+" TEXT,"+DESCRIPTION+" TEXT,"+ICON+" TEXT,"+CITY+" TEXT"+")";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    Context context;
    static SQLiteDatabase sqLiteDatabase;
    public weather_offline_Class(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            sqLiteDatabase=db;
            db.execSQL(CREATE_TABLE_QUERY);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void insertData(String cityName,String day,String date,String max_temp,String min_temp,String humidity,String pressure,String description,String icon,String main,String speed,SQLiteDatabase db){
        ContentValues contentValues=new ContentValues();
        contentValues.put(CITY,cityName);
        contentValues.put(DAY,day);
        contentValues.put(DATE,date);
        contentValues.put(MAX,max_temp);
        contentValues.put(MIN,min_temp);
        contentValues.put(HUMIDITY,humidity);
        contentValues.put(PRESSURE,pressure);
        contentValues.put(SPEED,speed);
        contentValues.put(ICON,icon);
        contentValues.put(DESCRIPTION,description);
        contentValues.put(MAIN,main);
        long id=db.insert(TABLE_NAME,null,contentValues);
    }
    public int getID(SQLiteDatabase db,String Day){
        String[]  columns={ID};
        String[] selectionArgs={""+Day};
        int id=0;
        Cursor cursor=db.query(TABLE_NAME,columns,DAY+" =?",selectionArgs,null,null,null,null);
        while (cursor.moveToNext()){
            id=cursor.getInt(cursor.getColumnIndex(DAY));
        }
        return id;
    }
    public String getCity(SQLiteDatabase db,int id){
        String[]  columns={CITY};
        String[] selectionArgs={""+id};
        String City=null;
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        while (cursor.moveToNext()){
            City=cursor.getString(cursor.getColumnIndex(CITY));
        }
        return City;
    }
    public String getDay(SQLiteDatabase db,int id){
        String[]  columns={DAY};
        String[] selectionArgs={""+id};
        String Date=null;
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        while (cursor.moveToNext()){
            Date=cursor.getString(cursor.getColumnIndex(DAY));
        }
        return Date;
    }
    public String getMax(SQLiteDatabase db,int id){
        String[]  columns={MAX};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Max=cursor.getString(cursor.getColumnIndex(MAX));
            stringBuffer.append(Max);
        }
        return stringBuffer.toString();
    }
    public String getMin(SQLiteDatabase db,int id){
        String[]  columns={MIN};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Min=cursor.getString(cursor.getColumnIndex(MIN));
            stringBuffer.append(Min);
        }
        return stringBuffer.toString();
    }
    public String getMain(SQLiteDatabase db,int id) {
        String[] columns = {MAIN};
        String[] selectionArgs = {"" + id};
        Cursor cursor = db.query(TABLE_NAME, columns, ID + " =?", selectionArgs, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String Main =cursor.getString(cursor.getColumnIndex(MAIN));
            stringBuffer.append(Main);
        }
        return stringBuffer.toString();
    }
    public int deleteData(SQLiteDatabase db,int id){
        String[] whereArgs={""+id};
        int count=db.delete(TABLE_NAME,ID+" =?",whereArgs);
        return count;
    }
    public String getHumidity(SQLiteDatabase db,int id){
        String[]  columns={HUMIDITY};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Humid=""+cursor.getString(cursor.getColumnIndex(HUMIDITY));
            stringBuffer.append(Humid);
        }
        return stringBuffer.toString();
    }
    public String getSpeed(SQLiteDatabase db,int id){
        String[]  columns={SPEED};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Speed=""+cursor.getString(cursor.getColumnIndex(SPEED));
            stringBuffer.append(Speed);
        }
        return stringBuffer.toString();
    }
    public String getPressure(SQLiteDatabase db,int id){
        String[]  columns={PRESSURE};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Press=""+cursor.getString(cursor.getColumnIndex(PRESSURE));
            stringBuffer.append(Press);
        }
        return stringBuffer.toString();
    }
    public String getIcon(SQLiteDatabase db,int id){
        String[]  columns={ICON};
        String[] selectionArgs={""+id};
        Cursor cursor=db.query(TABLE_NAME,columns,ID+" =?",selectionArgs,null,null,null,null);
        StringBuffer stringBuffer=new StringBuffer();
        while (cursor.moveToNext()){
            String Icon=""+cursor.getString(cursor.getColumnIndex(ICON));
            stringBuffer.append(Icon);
        }
        return stringBuffer.toString();
    }
    public String getDescription(SQLiteDatabase db,int id) {
        String[] columns = {DESCRIPTION};
        String[] selectionArgs = {"" + id};
        Cursor cursor = db.query(TABLE_NAME, columns, ID + " =?", selectionArgs, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String Desc = "" + cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            stringBuffer.append(Desc);
        }
        return stringBuffer.toString();

    }

}
