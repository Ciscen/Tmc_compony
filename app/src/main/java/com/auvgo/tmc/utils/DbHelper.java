package com.auvgo.tmc.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LC on
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "tmcdatabase";
    private static final int VERSION = 3;
    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists  traincities (_id INTEGER PRIMARY KEY autoincrement,json varchar)";
        String sql2 = "create table if not exists  planecities (_id INTEGER PRIMARY KEY autoincrement,json varchar)";
        String sql3 = "create table if not exists  hotelcities (_id INTEGER PRIMARY KEY autoincrement,json varchar)";
        String createTrainCityHistory = "CREATE TABLE IF NOT EXISTS  TRAINCITYHISTORY " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,CODE VARCHAR,NAME VARCHAR)";
        String createPlaneCityHistory = "CREATE TABLE IF NOT EXISTS  PLANECITYHISTORY " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,CODE VARCHAR,NAME VARCHAR)";
        String createHotelCityHistory = "CREATE TABLE IF NOT EXISTS  HOTELCITYHISTORY " +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,CODE VARCHAR,NAME VARCHAR)";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(createTrainCityHistory);
        db.execSQL(createPlaneCityHistory);
        db.execSQL(createHotelCityHistory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists traincities ");
        db.execSQL("drop table if exists planecities ");
        db.execSQL("drop table if exists hotelcities ");
        db.execSQL("drop table if exists TRAINCITYHISTORY ");
        db.execSQL("drop table if exists HOTELCITYHISTORY ");
        db.execSQL("drop table if exists PLANECITYHISTORY ");
        onCreate(db);
    }

    public long add(String table, ContentValues values) {
        long insert = db.insert(table, null, values);
        if (insert != -1) {
            LogFactory.d("db insert success!");
        }
        return insert;
    }

    public int delete(String table, String where, String[] args) {
        int n = db.delete(table, where, args);
        if (n != 0) {
            LogFactory.d("db delete success!");
            return n;
        } else {
            LogFactory.d("删除数据库文件失败!");
            return -1;
        }
    }

    public void update(String table, ContentValues values, String where, String[] args) {
        int i = db.update(table, values, where, args);
        if (i != 0) {
            LogFactory.d("db insert success!");
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs) {
        return db.query(table, columns, selection, selectionArgs, null, null, null);
    }

    public void close() {
        db.close();
    }
}
