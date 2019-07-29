package com.fingertech.kesforstudent.Sqlite;

public class Sound {
    public static final String TABLE            = "table_sound";
    public static final String KEY_CourseId     = "soundId";
    public static final String KEY_Name         = "sound";

    private String id, name;

    public Sound() {
    }


    public Sound(String id, String name) {
        this.id             = id;
        this.name           = name;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
