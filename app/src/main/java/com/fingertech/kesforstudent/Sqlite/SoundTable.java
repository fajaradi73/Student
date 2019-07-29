package com.fingertech.kesforstudent.Sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fingertech.kesforstudent.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class SoundTable {
    private Sound data;
    boolean check;

    public SoundTable(){
        data = new Sound();
    }

    public static String createTable(){
        return "CREATE TABLE " + Sound.TABLE + " (" +
                Sound.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                Sound.KEY_Name + " STRING" +
                " )";
    }
    public void insert(Sound data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Sound.KEY_CourseId, data.getId());
        values.put(Sound.KEY_Name, data.getName());
        // Inserting Row
        db.insert(Sound.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String getData()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String[] columns = {Sound.KEY_CourseId,Sound.KEY_Name};
        String selectQuery = "SELECT * FROM " + Sound.TABLE;
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
        contentValues.put(Sound.KEY_Name,newName);
        String[] whereArgs= {oldName};
        int count =db.update(Sound.TABLE,contentValues, Sound.KEY_Name+" = ?",whereArgs );
        return count;
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + Sound.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Sound.KEY_CourseId, cursor.getString(0));
                map.put(Sound.KEY_Name, cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
