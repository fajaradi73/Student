package com.fingertech.kesforstudent.Service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class PositionTable {
    private Position data;
    boolean check;

    public PositionTable(){
        data = new Position();
    }

    public static String createTable(){
        return "CREATE TABLE " + Position.TABLE + " (" +
                Position.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                Position.KEY_Name + " STRING NOT NULL," +
                Position.KEY_Status + "STRING" +
                " )";
    }
    public void insert(Position data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Position.KEY_CourseId, data.getId());
        values.put(Position.KEY_Name, data.getName());
        values.put(Position.KEY_Status,data.getStatus());
        // Inserting Row
        db.insert(Position.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String getData()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String[] columns = {Position.KEY_CourseId,Position.KEY_Name};
        String selectQuery = "SELECT * FROM " + Position.TABLE;
        Cursor cursor = db.query(selectQuery,columns,null,null,null,null,null);
//        Cursor cursor =db.query("kodetable",columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            String name =cursor.getString(cursor.getColumnIndex("position"));
            buffer.append(name);
        }
        return buffer.toString();
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Position.KEY_Name,newName);
        String[] whereArgs= {oldName};
        int count =db.update(Position.TABLE,contentValues, Position.KEY_Name+" = ?",whereArgs );
        return count;
    }
    public int updateStatus(String oldName , String newName)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Position.KEY_Status,newName);
        String[] whereArgs= {oldName};
        int count =db.update(Position.TABLE,contentValues, Position.KEY_Status+" = ?",whereArgs );
        return count;
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + Position.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Position.KEY_CourseId, cursor.getString(0));
                map.put(Position.KEY_Name, cursor.getString(1));
                map.put(Position.KEY_Status,cursor.getString(2));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
