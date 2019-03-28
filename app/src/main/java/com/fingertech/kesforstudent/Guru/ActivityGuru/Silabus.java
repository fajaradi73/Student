package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.fingertech.kesforstudent.Student.Activity.RaporAnak;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Silabus extends AppCompatActivity {

    Auth mApiInterface;
    RecyclerView rv_silabus;
    Spinner sp_edulevel,sp_mapel;
    String authorization,school_code,member_id,scyear_id,edulevel_id,edulevel_name,cources_id,cources_name,code;
    int status;
    Toolbar toolbar;
    TextView tv_hint;
    CardView btn_go;
    List<String> listEdulevel   = new ArrayList<String>();
    List<String> listMapel      = new ArrayList<String>();
    private List<JSONResponse.DataEdulevel> dataEdulevelList;
    private List<JSONResponse.DataMapelEdu> dataMapelEduList;
    ProgressDialog dialog;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silabus);
        toolbar     = findViewById(R.id.toolbarSilabus);
        rv_silabus  = findViewById(R.id.rv_silabus);
        tv_hint     = findViewById(R.id.hint_silabus);
        btn_go      = findViewById(R.id.btn_go);
        sp_edulevel = findViewById(R.id.sp_tingkatan_kelas);
        sp_mapel    = findViewById(R.id.sp_mapel);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);

        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        listEdulevel.add("Semua Tingkatan Kelas");
        listMapel.add("Semua Mata Pelajaran");
        dapat_edulevel();

        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_white,listEdulevel);
        int spinnerPosition = adapterRaport.getPosition("Semua Tingkatan Kelas");
        adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_edulevel.setAdapter(adapterRaport);
        sp_edulevel.setSelection(spinnerPosition);
        sp_edulevel.setOnItemSelectedListener((parent, view, position, id) ->{
            if (position == 0){
                edulevel_id = "-1";
                cources_id  = "-1";
                sp_mapel.setEnabled(false);
            }else {
                edulevel_id = dataEdulevelList.get(position - 1).getEdulevelid();
                sp_mapel.setEnabled(true);
                dapat_mapel();
            }
        });
        Log.d("edulevel",sp_edulevel.getSelectedItem().toString()+"/"+sp_edulevel.getSelectedItemPosition());

        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_white,listMapel);
        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_mapel.setAdapter(adapterMapel);
        sp_mapel.setOnItemSelectedListener((parent, view, position, id) ->{
            if (position == 0){
                cources_id = "-1";
            }else {
                cources_id = dataEdulevelList.get(position - 1).getEdulevelid();
            }
        });

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DataSilabus",cources_id+"/"+edulevel_id);
            }
        });
    }

    private void dapat_edulevel(){
        Call<JSONResponse.ListEdulevel> call = mApiInterface.kes_get_edulevel_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListEdulevel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListEdulevel> call, Response<JSONResponse.ListEdulevel> response) {
                Log.d("Sukses",response.code()+"");
                JSONResponse.ListEdulevel resource = response.body();
                status  = resource.status;
                code    = resource.code;
                if (status  == 1   &&  code.equals("DTS_SCS_0001")){
                    dataEdulevelList    = response.body().getData();
                    for (int i = 0; i < dataEdulevelList.size();i++){
                        edulevel_name   = dataEdulevelList.get(i).getEdulevel_name();
                        listEdulevel.add(edulevel_name);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListEdulevel> call, Throwable t) {
                Log.d("Gagal",t.toString());
            }
        });
    }

    private void dapat_mapel(){
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),member_id,edulevel_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListMapelEdu>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapelEdu> call, Response<JSONResponse.ListMapelEdu> response) {
                Log.d("Sukses",response.code()+"");
                JSONResponse.ListMapelEdu resource = response.body();
                status  = resource.status;
                code    = resource.code;
                if (status==1 && code.equals("DTS_SCS_0001")){
                    dataMapelEduList    = response.body().getData();
                    if (listMapel!=null) {
                        listMapel.clear();
                        listMapel.add("Semua Mata Pelajaran");
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cources_name = response.body().getData().get(i).getCources_name();
                            listMapel.add(cources_name);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.d("Gagal",t.toString());
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
        dialog = new ProgressDialog(Silabus.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
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
