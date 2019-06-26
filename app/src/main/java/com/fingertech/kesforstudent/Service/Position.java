package com.fingertech.kesforstudent.Service;

public class Position {
    public static final String TABLE            = "table_kode";
    public static final String KEY_CourseId     = "saveId";
    public static final String KEY_Name         = "save_name";
    public static final String KEY_Status       = "save_status";

    private String id;
    private String name;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public Position() {
    }


    public Position(String id, String name,String status) {
        this.id     = id;
        this.name   = name;
        this.status = status;

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
