package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Activity.Masuk;

public class PesanGuru extends AppCompatActivity {

    Toolbar toolbar;
    CardView cv_pesanmasuk, cv_kirimpesan, cv_pesanterkirim;
    SharedPreferences sharedpreferences,sharedViewpager;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id;

    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_guru);

        sharedpreferences = this.getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");

        toolbar = findViewById(R.id.toolbarTulisPesan);
        cv_pesanmasuk = findViewById(R.id.btn_pesan_masuk);
        cv_kirimpesan = findViewById(R.id.btn_kirim_pesan);
        cv_pesanterkirim = findViewById(R.id.btn_pesan_terkirim);

        cv_kirimpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesanGuru.this, TulisPesan.class);
                startActivity(intent);

            }
        });
        cv_pesanterkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PesanGuru.this, Pesan_Terkirim.class);
                startActivity(intent);

            }
        });

    }
}