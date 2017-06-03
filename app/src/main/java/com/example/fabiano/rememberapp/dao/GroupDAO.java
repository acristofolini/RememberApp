package com.example.fabiano.rememberapp.dao;

/**
 * Created by Ana on 5/28/2017.
 * Arquivo responsável pela manipulação de dados na tabela GROUPS
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.fabiano.rememberapp.data.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends BaseDAO {

    public GroupDAO(Context context) {
        super(context);
    }

    /*@Override
    public void onCreate(SQLiteDatabase db) {

        // ao criar o método, verifica se a tabela GROUPS já existe no banco GROUP.DB
        try{
            String sql = "CREATE TABLE groups (id INTEGER PRIMARY KEY, name TEXT NOT NULL);";
            db.execSQL(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // método implementado para eliminar o conteúdo da tabela GROUPS
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS groups;";
        db.execSQL(sql);
        onCreate(db);
    }*/

    public void persist(Group group) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = getGroupData(group);
        db.insert(Group.TABLE_NAME, null, cv);
    }

    // método que busca todos os registros da tabel GROUP e devolve a lista destes registros para a tela
    public List<Group> fetchAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM groups", null);
        List<Group> groups = new ArrayList<Group>();
        while (c.moveToNext()) {
            Group group = new Group();
            group.setId(c.getLong(c.getColumnIndex(Group.ID_COLUMN)));
            group.setGroup(c.getString(c.getColumnIndex(Group.NAME_COLUMN)));
            groups.add(group);
        }
        c.close();
        return groups;
    }

    // método para eliminar um registro da tabela GROUP
    public void remove(Group group) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = { group.getId().toString() };
        db.delete(Group.TABLE_NAME, "id = ?", params);
    }

    // método para alterar um registro da tabela GROUP
    public void update(Group group) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = getGroupData(group);
        String[] params = { group.getId().toString() };
        db.update(Group.TABLE_NAME, cv, "id = ?", params);
    }

    @NonNull
    private ContentValues getGroupData(Group group) {
        ContentValues cv = new ContentValues();
        cv.put(Group.NAME_COLUMN, group.getGroup());
        return cv;
    }
}
