package com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen;

import java.util.List;

public class ModelAbsenGuru {

    private  String nama,nis,picture,id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<ModelArrayAbsen> modelArrayAbsenList;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<ModelArrayAbsen> getModelArrayAbsenList() {
        return modelArrayAbsenList;
    }

    public void setModelArrayAbsenList(List<ModelArrayAbsen> modelArrayAbsenList) {
        this.modelArrayAbsenList = modelArrayAbsenList;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }
}
