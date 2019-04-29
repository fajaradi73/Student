package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsenGuru;
import com.fingertech.kesforstudent.R;

import java.util.ArrayList;
import java.util.List;

public class AbsenMurid extends AppCompatActivity {
    CardView btninfo;
    RecyclerView rv_absen;
    AdapterAbsen adapterAbsen;
    List<ModelAbsenGuru> viewitemlist;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_murid);
        btninfo = findViewById(R.id.Cv_informasi);
        rv_absen = findViewById(R.id.rv_absenguru);
        toolbar = findViewById(R.id.toolbarAbsen);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);



        viewitemlist = new ArrayList<>();
        rv_absen.setHasFixedSize(true);
        rv_absen.setLayoutManager(new LinearLayoutManager(this));
        ModelAbsenGuru modelAbsenGuru = new ModelAbsenGuru("");
        modelAbsenGuru.setNama("Vina Sonia");
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        viewitemlist.add(modelAbsenGuru);
        adapterAbsen = new AdapterAbsen(this,viewitemlist);
        rv_absen.setAdapter(adapterAbsen);





        Dialog();

    }



    public void Dialog(){

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder showdialog = new AlertDialog.Builder(AbsenMurid.this);
                Button btnclose;
                View view = getLayoutInflater().inflate(R.layout.layout_info_absen,null);
                btnclose = view.findViewById(R.id.btnclose);


                showdialog.setView(view);
                AlertDialog dialog = showdialog.create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }


        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
