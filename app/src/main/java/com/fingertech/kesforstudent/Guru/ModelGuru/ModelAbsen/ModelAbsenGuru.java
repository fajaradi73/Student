package com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen;

import java.util.List;

public class ModelAbsenGuru {

private  String nama,nis;
private List<ModelArrayAbsen> modelArrayAbsenList;

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
