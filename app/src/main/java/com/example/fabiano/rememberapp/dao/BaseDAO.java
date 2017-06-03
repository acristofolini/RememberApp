package com.example.fabiano.rememberapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Fabiano on 6/3/2017.
 */
public class BaseDAO extends SQLiteOpenHelper {

    public BaseDAO(Context context) {
        super(context, "remember.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE groups (id INTEGER PRIMARY KEY, name TEXT NOT NULL);";
        db.execSQL(sql);
        sql = "CREATE TABLE history (id INTEGER PRIMARY KEY, id_group INTEGER, title TEXT NOT NULL, detail TEXT, date INTEGER NOT NULL );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS groups;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS history;";
        db.execSQL(sql);
        onCreate(db);
    }
}
