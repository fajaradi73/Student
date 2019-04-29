package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;


public class TulisPesan extends AppCompatActivity {

    Spinner spinner;
    Spinner spn_kepada, spn_kelas, spn_siswa, spn_mapel, spn_teacher, spn_admin;
    TextView ht_kelas, ht_siswa, ht_mapel, ht_teacher, ht_admin;
    EditText kt_judul, kt_pesan;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis_pesan);

        spinner = findViewById(R.id.sp_kirim_kepada);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kirim_kepada, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spn_kepada = findViewById(R.id.sp_kirim_kepada);
        spn_kelas = findViewById(R.id.sp_kelas);
        spn_siswa = findViewById(R.id.sp_siswa);
        spn_mapel = findViewById(R.id.sp_mapel);
        spn_teacher = findViewById(R.id.sp_teacher);
        spn_admin = findViewById(R.id.sp_admin);

        ht_kelas = findViewById(R.id.hint_sp_kelas);
        ht_siswa = findViewById(R.id.hint_sp_siswa);
        ht_mapel = findViewById(R.id.hint_sp_mapel);
        ht_teacher = findViewById(R.id.hint_sp_teacher);
        ht_admin     = findViewById(R.id.hint_sp_admin);

        kt_judul = findViewById(R.id.kt_judul);
        kt_pesan = findViewById(R.id.kt_pesan);

        toolbar = findViewById(R.id.toolbarTulisPesan);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spn_kepada.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (position == 0)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.GONE);
                    ht_siswa.setVisibility(View.GONE);
                    ht_mapel.setVisibility(View.GONE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 1)
                {
                    spn_kelas.setVisibility(View.VISIBLE);
                    spn_siswa.setVisibility(View.VISIBLE);
                    spn_mapel.setVisibility(View.VISIBLE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.VISIBLE);
                    ht_siswa.setVisibility(View.VISIBLE);
                    ht_mapel.setVisibility(View.VISIBLE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 2)
                {
                    spn_kelas.setVisibility(View.VISIBLE);
                    spn_siswa.setVisibility(View.VISIBLE);
                    spn_mapel.setVisibility(View.VISIBLE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.VISIBLE);
                    ht_siswa.setVisibility(View.VISIBLE);
                    ht_mapel.setVisibility(View.VISIBLE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 3)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.GONE);
                    ht_siswa.setVisibility(View.GONE);
                    ht_mapel.setVisibility(View.GONE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 4)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.GONE);
                    ht_siswa.setVisibility(View.GONE);
                    ht_mapel.setVisibility(View.GONE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 5)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.VISIBLE);
                    spn_admin.setVisibility(View.GONE);
                    ht_kelas.setVisibility(View.GONE);
                    ht_siswa.setVisibility(View.GONE);
                    ht_mapel.setVisibility(View.GONE);
                    ht_teacher.setVisibility(View.VISIBLE);
                    ht_admin.setVisibility(View.GONE);
                }
                else if (position == 6)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.VISIBLE);
                    ht_kelas.setVisibility(View.GONE);
                    ht_siswa.setVisibility(View.GONE);
                    ht_mapel.setVisibility(View.GONE);
                    ht_teacher.setVisibility(View.GONE);
                    ht_admin.setVisibility(View.VISIBLE);
                }

                }

            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_kirim:
                Intent intent = new Intent(TulisPesan.this, Pesan_Terkirim.class);
                intent.putExtra("Judul", kt_judul.getText().toString());
                intent.putExtra("Pesan", kt_pesan.getText().toString());
                startActivity(intent);

                FancyToast.makeText(getApplicationContext(),"Pesan Terkirim", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                finish();
                    return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pesan, menu);
        return true;
    }
}