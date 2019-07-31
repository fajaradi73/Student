package com.fingertech.kesforstudent.Guru.ModelGuru;

public class ModelAgenda {
    private String date;
    private String type;
    private String desc;
    private String content;
    private String colour;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_data() {
        return type_data;
    }

    public void setType_data(String type_data) {
        this.type_data = type_data;
    }

    private String type_data;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
