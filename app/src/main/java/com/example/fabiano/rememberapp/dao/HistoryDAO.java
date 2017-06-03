package com.example.fabiano.rememberapp.dao;

/**
 * Created by Ana on 5/28/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.fabiano.rememberapp.data.Group;
import com.example.fabiano.rememberapp.data.History;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryDAO extends BaseDAO {

    public HistoryDAO(Context context) {
        super(context);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE history (id INTEGER PRIMARY KEY, id_group INTEGER, title TEXT NOT NULL, detail TEXT, date INTEGER NOT NULL );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS history;";
        db.execSQL(sql);
        onCreate(db);
    }*/

    public void persist(History history) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = getDataHistory(history);
        db.insert(History.TABLE_NAME, null, cv);
    }

    public List<History> fetchAll(Long id_group) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM history ", null);
        List<History> historys = new ArrayList<History>();
        while (c.moveToNext()) {
            History history = new History();
            history.setId(c.getLong(c.getColumnIndex(History.ID_COLUMN)));
            history.setIdGroup(c.getLong(c.getColumnIndex(History.ID_GROUP_COLUMN)));
            history.setTitle(c.getString(c.getColumnIndex(History.TITLE_COLUMN)));
            history.setDetail(c.getString(c.getColumnIndex(History.DETAIL_COLUMN)));
            history.setDateHistory(new Date(c.getLong(c.getColumnIndex(History.DATE_COLUMN))));
            historys.add(history);
        }
        c.close();
        return historys;
    }

    public void remove(History history) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = { history.getId().toString() };
        db.delete(Group.TABLE_NAME, "id = ?", params);
    }

    public void update(History history) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = getDataHistory(history);
        String[] params = { history.getId().toString() };
        db.update(History.TABLE_NAME, cv, "id = ?", params);
    }

    @NonNull
    private ContentValues getDataHistory(History history) {
        ContentValues cv = new ContentValues();

        cv.put(History.ID_GROUP_COLUMN, history.getIdGroup());
        cv.put(History.TITLE_COLUMN, history.getTitle());
        cv.put(History.DATE_COLUMN, history.getDateHistory().getTime());
        cv.put(History.DETAIL_COLUMN, history.getDetail());
        return cv;
    }
}
