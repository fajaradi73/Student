package com.fingertech.kesforstudent.Service;

public class Position {
    public static final String TABLE            = "table_kode";
    public static final String KEY_CourseId     = "saveId";
    public static final String KEY_Name         = "save_name";

    private String id, name;

    public Position() {
    }


    public Position(String id, String name) {
        this.id     = id;
        this.name   = name;

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
