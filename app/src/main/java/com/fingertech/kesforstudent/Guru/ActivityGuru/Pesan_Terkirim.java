package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fingertech.kesforstudent.R;

public class Pesan_Terkirim extends AppCompatActivity {

    TextView tv_contohView, tv_contohView2;
    String  data, data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan__terkirim);

        tv_contohView =  findViewById(R.id.contoh_view);
        tv_contohView2 = findViewById(R.id.contoh_view2);

        data = getIntent().getStringExtra("Judul");
        data2 = getIntent().getStringExtra("Pesan");

        tv_contohView.setText(data);
        tv_contohView2.setText(data2);

    }
}
