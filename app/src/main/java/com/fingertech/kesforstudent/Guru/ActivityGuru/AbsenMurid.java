package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsenGuru;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenMurid extends AppCompatActivity {
    CardView btninfo;
    RecyclerView rv_absen;
    AdapterAbsen adapterAbsen;
    List<ModelAbsenGuru> modelAbsenGuruList = new ArrayList<>();
    ModelAbsenGuru modelAbsenGuru;
    Toolbar toolbar;
    Auth mApiInterface;
    String authorization,school_code,member_id,scyear_id, classroom,code,nama;
    int status;
    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_CLASS_ID     = "classroom_id";
    public static final String TAG_YEAR_ID      = "scyear_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_murid);
        btninfo             = findViewById(R.id.Cv_informasi);
        rv_absen            = findViewById(R.id.rv_absenguru);
        toolbar             = findViewById(R.id.toolbarAbsen);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString(TAG_YEAR_ID,"");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        classroom           = sharedpreferences.getString(TAG_CLASS_ID,"");

//
//        modelAbsenGuruList = new ArrayList<>();
//
//
//        rv_absen.setHasFixedSize(true);
//        rv_absen.setLayoutManager(new LinearLayoutManager(this));
//        ModelAbsenGuru modelAbsenGuru = new ModelAbsenGuru("");
//        modelAbsenGuru.setNama("Vina Sonia");
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        modelAbsenGuruList.add(modelAbsenGuru);
//        adapterAbsen = new AdapterAbsen(this,modelAbsenGuruList);
//        rv_absen.setAdapter(adapterAbsen);

        classroom = "1";
        Getmurid();
        Dialog();

    }

    private void Getmurid(){
        Call<JSONResponse.ListMurid> call =mApiInterface.kes_get_student_get(authorization,school_code,member_id,scyear_id, classroom);
        call.enqueue(new Callback<JSONResponse.ListMurid>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMurid> call, Response<JSONResponse.ListMurid> response) {
                Log.d("muridsukses",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListMurid resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            nama = response.body().getData().get(i).getFullname();
                            modelAbsenGuru = new ModelAbsenGuru();
                            modelAbsenGuru.setNama(nama);
                            modelAbsenGuruList.add(modelAbsenGuru);
                        }
                        adapterAbsen = new AdapterAbsen(AbsenMurid.this,modelAbsenGuruList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AbsenMurid.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_absen.setLayoutManager(linearLayoutManager);
                        rv_absen.setAdapter(adapterAbsen);

                    }
                }


            }

            @Override
            public void onFailure(Call<JSONResponse.ListMurid> call, Throwable t) {
                Log.d("muridsukses",t.toString());
            }
        });



    }



    public void Dialog(){

        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder showdialog = new AlertDialog.Builder(AbsenMurid.this);
                TextView btnclose;
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
