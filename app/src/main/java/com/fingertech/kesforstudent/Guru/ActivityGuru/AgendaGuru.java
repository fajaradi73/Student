package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaGuru extends AppCompatActivity {

    Toolbar toolbar;
    ImageView iv_close;
    CardView btn_lihat;
    Spinner sp_edulevel,sp_mapel;
    Auth mApiInterface;
    String authorization,school_code,member_id,scyear_id, classroom_id,edulevel_name,cources_id,cources_name,code;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    ProgressDialog dialog;
    int status;
    List<String> listEdulevel           = new ArrayList<String>();
    List<String> listMapel              = new ArrayList<String>();
    private List<JSONResponse.DataKelas> dataEdulevelList;
    private List<JSONResponse.DataMapelEdu> dataMapelEduList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_guru);
        toolbar     = findViewById(R.id.toolbar_agenda);
        sp_edulevel     = findViewById(R.id.sp_tingkatan_kelas);
        sp_mapel        = findViewById(R.id.sp_mapel);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        btn_lihat       = findViewById(R.id.btn_lihat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        dapat_edulevel();
        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classroom_id ==null){
                    FancyToast.makeText(getApplicationContext(),"Harap pilih tingkatan kelas terlebih dahulu", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }else if (cources_id == null){
                    FancyToast.makeText(getApplicationContext(),"Harap pilih mata pelajaran terlebih dahulu", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("authorization",authorization);
                    editor.putString("member_id",member_id);
                    editor.putString("school_code",school_code);
                    editor.putString("scyear_id",scyear_id);
                    editor.putString("cources_id",cources_id);
                    editor.putString("classroom_id", classroom_id);
                    editor.apply();
                    Intent intent = new Intent(AgendaGuru.this,AgendaDetail.class);
                    intent.putExtra("authorization",authorization);
                    intent.putExtra("member_id",member_id);
                    intent.putExtra("school_code",school_code);
                    intent.putExtra("scyear_id",scyear_id);
                    intent.putExtra("cources_id",cources_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
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
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void dapat_edulevel(){
        Call<JSONResponse.ListKelas> call = mApiInterface.kes_get_classroom_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListKelas>() {
            @Override
            public void onResponse(Call<JSONResponse.ListKelas> call, Response<JSONResponse.ListKelas> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListKelas resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataEdulevelList = response.body().getData();
                        listEdulevel.add("Pilih Kelas");
                        for (int i = 0; i < dataEdulevelList.size(); i++) {
                            edulevel_name = dataEdulevelList.get(i).getClassroom_name();
                            listEdulevel.add(edulevel_name);
                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(AgendaGuru.this, R.layout.spinner_full, listEdulevel);
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_edulevel.setAdapter(adapterRaport);
                            sp_edulevel.setOnItemSelectedListener((parent, view, position, id) -> {
                                if (position == 0) {
                                    classroom_id = null;
                                    sp_mapel.setEnabled(false);
                                } else {
                                    classroom_id = dataEdulevelList.get(position - 1).getClassroomid();
                                    sp_mapel.setEnabled(true);
                                    dapat_mapel();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListKelas> call, Throwable t) {
                Log.d("GagalClass",t.toString());
            }
        });
    }

    private void dapat_mapel(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),member_id, classroom_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListMapelEdu>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapelEdu> call, Response<JSONResponse.ListMapelEdu> response) {
                Log.d("MataPelajaran",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.ListMapelEdu resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataMapelEduList = response.body().getData();
                        if (listMapel != null) {
                            listMapel.clear();
                            listMapel.add("Pilih Mata Pelajaran");
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                cources_name = response.body().getData().get(i).getCources_name();
                                listMapel.add(cources_name);
                                final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(AgendaGuru.this, R.layout.spinner_full, listMapel);
                                adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                sp_mapel.setAdapter(adapterMapel);
                                sp_mapel.setOnItemSelectedListener((parent, view, position, id) -> {
                                    if (position == 0) {
                                        cources_id = null;
                                    } else {
                                        cources_id = dataMapelEduList.get(position - 1).getCourcesid();
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.d("GagalMapel",t.toString());
                hideDialog();
            }
        });
    }
    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
        dialog.setContentView(R.layout.progressbar);
    }
    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }
    public void progressBar(){
        dialog = new ProgressDialog(AgendaGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}