package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;

public class MenuUtamaGuru extends AppCompatActivity {

    SharedPreferences sharedpreferences,sharedViewpager;
    String picture, Base_anak;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id;
    Auth mApiInterface;
    int status;
    String code;
    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";
    CardView btn_absensi,btn_penilaian,btn_silabus,btn_pesan,btn_jadwal,btn_kalendar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu__utama__guru);
        toolbar     = findViewById(R.id.toolbarJadwalGuru);
        btn_absensi = findViewById(R.id.btn_absensi);
        btn_penilaian   = findViewById(R.id.btn_penilaian);
        btn_jadwal      = findViewById(R.id.btn_jadwal_mengajar);
        btn_silabus     = findViewById(R.id.btn_silabus);
        btn_pesan       = findViewById(R.id.btn_pesan);
        btn_kalendar    = findViewById(R.id.btn_kalender);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");

        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtamaGuru.this,JadwalGuru.class);
                startActivity(intent);
            }
        });
    }
}
