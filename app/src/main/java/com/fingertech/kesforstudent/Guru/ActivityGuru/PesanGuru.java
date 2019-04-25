package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fingertech.kesforstudent.R;

public class PesanGuru extends AppCompatActivity {

    Toolbar toolbar;
    CardView cv_pesanmasuk, cv_kirimpesan, cv_pesanterkirim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_guru);

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