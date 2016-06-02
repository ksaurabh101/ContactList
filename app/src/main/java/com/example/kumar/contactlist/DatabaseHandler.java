package com.example.kumar.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar on 01-Jun-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Details.db";

    //user table
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MOBILE = "mobile";
    private static final String COLUMN_OTP = "otp";
    private static final String COLUMN_TIME = "time";

    private static final String TABLE_NAME = "details";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table " + TABLE_NAME + "(" + COLUMN_NAME + " text not null, " + COLUMN_MOBILE + " text not null, " + COLUMN_OTP + " text not null, " + COLUMN_TIME + " datetime default current_timestamp)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE);
        Log.d("Database", "Table is created");
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void inserDetails(String name, String number, String otp, String time) {

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MOBILE, number);
        values.put(COLUMN_OTP, otp);
        values.put(COLUMN_TIME, time);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<ContactDetails> getDetails() {
        List<ContactDetails> list=new ArrayList<>();
        db = this.getReadableDatabase();
        String projection[]={COLUMN_NAME,COLUMN_MOBILE,COLUMN_OTP,COLUMN_TIME};
        String selection="";
        String selectionArgs[]={};
        Cursor cursor=db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, "time DESC");
        if(cursor.moveToFirst())
        {
            do{
                ContactDetails obj=new ContactDetails();
                obj.setName(cursor.getString(0));
                obj.setNumber(cursor.getString(1));
                obj.setOtp(cursor.getString(2));
                obj.setTime(cursor.getString(3));
                list.add(obj);
            }
            while(cursor.moveToNext());
        }
        return  list;
    }
}
