package com.fingertech.kesforstudent.Guru.ModelGuru;

public class ModelAttendance {
    private String codeabsen;
    private String warna;
    private String id,id_attitude;

    public String getId_attitude() {
        return id_attitude;
    }

    public void setId_attitude(String id_attitude) {
        this.id_attitude = id_attitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    private String nis;

    public String getCodeabsen() {
        return codeabsen;
    }

    public void setCodeabsen(String codeabsen) {
        this.codeabsen = codeabsen;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }
}
