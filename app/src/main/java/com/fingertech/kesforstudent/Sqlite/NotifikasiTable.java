package com.fingertech.kesforstudent.Sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fingertech.kesforstudent.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class NotifikasiTable {
    private Notifikasi data;
    boolean check;

    public NotifikasiTable(){
        data = new Notifikasi();
    }

    public static String createTable(){
        return "CREATE TABLE " + Notifikasi.TABLE + " (" +
                Notifikasi.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                Notifikasi.KEY_NotifId  + " INTEGER NOT NULL," +
                Notifikasi.KEY_Title + " STRING NOT NULL," +
                Notifikasi.KEY_Body + " STRING NOT NULL," +
                Notifikasi.KEY_School_code + " STRING," +
                Notifikasi.KEY_message_id + " STRING," +
                Notifikasi.KEY_parent_message_id + " STRING," +
                Notifikasi.KEY_type + " STRING," +
                Notifikasi.KEY_time + " STRING," +
                Notifikasi.KEY_status + " STRING," +
                Notifikasi.KEY_member_id + " STRING," +
                Notifikasi.KEY_student_id + " STRING," +
                Notifikasi.KEY_classroom_id + " STRING," +
                Notifikasi.KEY_agenda_date + " STRING" +
                " )";
    }
    public void insert(Notifikasi data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Notifikasi.KEY_CourseId, data.getId());
        values.put(Notifikasi.KEY_NotifId, data.getId_notifikasi());
        values.put(Notifikasi.KEY_Title, data.getTitle());
        values.put(Notifikasi.KEY_Body, data.getBody());
        values.put(Notifikasi.KEY_School_code, data.getSchool_code());
        values.put(Notifikasi.KEY_message_id, data.getMessage_id());
        values.put(Notifikasi.KEY_parent_message_id, data.getParent_message_id());
        values.put(Notifikasi.KEY_type,data.getType());
        values.put(Notifikasi.KEY_time,data.getTime());
        values.put(Notifikasi.KEY_status,data.getStatus());
        values.put(Notifikasi.KEY_member_id,data.getMember_id());
        values.put(Notifikasi.KEY_student_id,data.getStudent_id());
        values.put(Notifikasi.KEY_classroom_id,data.getClassroom_id());
        values.put(Notifikasi.KEY_agenda_date,data.getAgenda_date());
        // Inserting Row
        db.insert(Notifikasi.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String getData()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String[] columns = {Notifikasi.KEY_CourseId,Notifikasi.KEY_Title};
        String selectQuery = "SELECT * FROM " + Notifikasi.TABLE;
        Cursor cursor = db.query(selectQuery,columns,null,null,null,null,null);
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
        contentValues.put(Notifikasi.KEY_Title,newName);
        String[] whereArgs= {oldName};
        return db.update(Notifikasi.TABLE,contentValues, Notifikasi.KEY_Title+" = ?",whereArgs );
    }
    public void updateStatus(int id_notifikasi, String oldName , String newName)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Notifikasi.KEY_status,newName);
        final String whereClause = Notifikasi.KEY_NotifId + " =? AND " + Notifikasi.KEY_status + " =?";
        final String[] whereArgs = {
                String.valueOf(id_notifikasi), String.valueOf(oldName)
        };
        db.update(Notifikasi.TABLE, contentValues, whereClause, whereArgs);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + Notifikasi.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Notifikasi.KEY_CourseId, cursor.getString(0));
                map.put(Notifikasi.KEY_NotifId,cursor.getString(1));
                map.put(Notifikasi.KEY_Title, cursor.getString(2));
                map.put(Notifikasi.KEY_Body, cursor.getString(3));
                map.put(Notifikasi.KEY_School_code, cursor.getString(4));
                map.put(Notifikasi.KEY_message_id, cursor.getString(5));
                map.put(Notifikasi.KEY_parent_message_id, cursor.getString(6));
                map.put(Notifikasi.KEY_type,cursor.getString(7));
                map.put(Notifikasi.KEY_time,cursor.getString(8));
                map.put(Notifikasi.KEY_status,cursor.getString(9));
                map.put(Notifikasi.KEY_member_id,cursor.getString(10));
                map.put(Notifikasi.KEY_student_id,cursor.getString(11));
                map.put(Notifikasi.KEY_classroom_id,cursor.getString(12));
                map.put(Notifikasi.KEY_agenda_date,cursor.getString(13));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
