package com.fingertech.kesforstudent.Sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fingertech.kesforstudent.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class NotifikasiGuruTable {
    private NotifikasiGuru data;
    boolean check;

    public NotifikasiGuruTable(){
        data = new NotifikasiGuru();
    }

    public static String createTable(){
        return "CREATE TABLE " + NotifikasiGuru.TABLE + " (" +
                NotifikasiGuru.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                NotifikasiGuru.KEY_NotifId  + " INTEGER NOT NULL," +
                NotifikasiGuru.KEY_Title + " STRING NOT NULL," +
                NotifikasiGuru.KEY_Body + " STRING NOT NULL," +
                NotifikasiGuru.KEY_School_code + " STRING," +
                NotifikasiGuru.KEY_message_id + " STRING," +
                NotifikasiGuru.KEY_parent_message_id + " STRING," +
                NotifikasiGuru.KEY_type + " STRING," +
                NotifikasiGuru.KEY_time + " STRING," +
                NotifikasiGuru.KEY_status + " STRING," +
                NotifikasiGuru.KEY_member_id + " STRING," +
                NotifikasiGuru.KEY_student_id + " STRING," +
                NotifikasiGuru.KEY_classroom_id + " STRING," +
                NotifikasiGuru.KEY_agenda_date + " STRING" +
                " )";
    }
    public void insert(NotifikasiGuru data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(NotifikasiGuru.KEY_CourseId, data.getId());
        values.put(NotifikasiGuru.KEY_NotifId, data.getId_notifikasi());
        values.put(NotifikasiGuru.KEY_Title, data.getTitle());
        values.put(NotifikasiGuru.KEY_Body, data.getBody());
        values.put(NotifikasiGuru.KEY_School_code, data.getSchool_code());
        values.put(NotifikasiGuru.KEY_message_id, data.getMessage_id());
        values.put(NotifikasiGuru.KEY_parent_message_id, data.getParent_message_id());
        values.put(NotifikasiGuru.KEY_type,data.getType());
        values.put(NotifikasiGuru.KEY_time,data.getTime());
        values.put(NotifikasiGuru.KEY_status,data.getStatus());
        values.put(NotifikasiGuru.KEY_member_id,data.getMember_id());
        values.put(NotifikasiGuru.KEY_student_id,data.getStudent_id());
        values.put(NotifikasiGuru.KEY_classroom_id,data.getClassroom_id());
        values.put(NotifikasiGuru.KEY_agenda_date,data.getAgenda_date());
        // Inserting Row
        db.insert(NotifikasiGuru.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public String getData()
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String[] columns = {NotifikasiGuru.KEY_CourseId,NotifikasiGuru.KEY_Title};
        String selectQuery = "SELECT * FROM " + NotifikasiGuru.TABLE;
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
        contentValues.put(NotifikasiGuru.KEY_Title,newName);
        String[] whereArgs= {oldName};
        return db.update(NotifikasiGuru.TABLE,contentValues, NotifikasiGuru.KEY_Title+" = ?",whereArgs );
    }
    public void updateStatus(int id_notifikasi, String oldName , String newName)
    {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotifikasiGuru.KEY_status,newName);
        final String whereClause = NotifikasiGuru.KEY_NotifId + " =? AND " + NotifikasiGuru.KEY_status + " =?";
        final String[] whereArgs = {
                String.valueOf(id_notifikasi), String.valueOf(oldName)
        };
        db.update(NotifikasiGuru.TABLE, contentValues, whereClause, whereArgs);
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + NotifikasiGuru.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(NotifikasiGuru.KEY_CourseId, cursor.getString(0));
                map.put(NotifikasiGuru.KEY_NotifId,cursor.getString(1));
                map.put(NotifikasiGuru.KEY_Title, cursor.getString(2));
                map.put(NotifikasiGuru.KEY_Body, cursor.getString(3));
                map.put(NotifikasiGuru.KEY_School_code, cursor.getString(4));
                map.put(NotifikasiGuru.KEY_message_id, cursor.getString(5));
                map.put(NotifikasiGuru.KEY_parent_message_id, cursor.getString(6));
                map.put(NotifikasiGuru.KEY_type,cursor.getString(7));
                map.put(NotifikasiGuru.KEY_time,cursor.getString(8));
                map.put(NotifikasiGuru.KEY_status,cursor.getString(9));
                map.put(NotifikasiGuru.KEY_member_id,cursor.getString(10));
                map.put(NotifikasiGuru.KEY_student_id,cursor.getString(11));
                map.put(NotifikasiGuru.KEY_classroom_id,cursor.getString(12));
                map.put(NotifikasiGuru.KEY_agenda_date,cursor.getString(13));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}
