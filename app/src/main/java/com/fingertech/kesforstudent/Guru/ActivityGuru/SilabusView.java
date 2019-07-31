package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterSilabus;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelSilabus;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SilabusView extends AppCompatActivity {

    Auth mApiInterface;
    RecyclerView rv_silabus;
    Spinner sp_edulevel,sp_mapel;
    String authorization,school_code,member_id,scyear_id,edulevel_id,edulevel_name,cources_id,cources_name,code;
    String mapel,datez,kelas,files,base_silabus;
    int status;
    Toolbar toolbar;
    LinearLayout tv_hint;
    CardView btn_go;
    List<String> listEdulevel           = new ArrayList<String>();
    List<String> listMapel              = new ArrayList<String>();
    List<ModelSilabus> modelSilabusList = new ArrayList<>();
    ModelSilabus modelSilabus;
    AdapterSilabus adapterSilabus;
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
    CardView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silabus_view);
        toolbar     = findViewById(R.id.toolbarSilabus);
        iv_close        = findViewById(R.id.iv_close);
        tv_hint     = findViewById(R.id.hint_silabus);
        btn_go      = findViewById(R.id.btn_go);
        sp_edulevel = findViewById(R.id.sp_tingkatan_kelas);
        sp_mapel    = findViewById(R.id.sp_mapel);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        listMapel.add("Semua Mata Pelajaran");
        dapat_edulevel();

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edulevel_id==null){
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
                    editor.putString("classroom_id",edulevel_id);
                    editor.apply();
                    Intent intent = new Intent(SilabusView.this,Silabus.class);
                    intent.putExtra("authorization",authorization);
                    intent.putExtra("member_id",member_id);
                    intent.putExtra("school_code",school_code);
                    intent.putExtra("scyear_id",scyear_id);
                    intent.putExtra("cources_id",cources_id);
                    intent.putExtra("classroom_id",edulevel_id);
                    startActivity(intent);
                }
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void dapat_edulevel(){
        Call<JSONResponse.ListEdulevel> call = mApiInterface.kes_get_edulevel_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListEdulevel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListEdulevel> call, Response<JSONResponse.ListEdulevel> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListEdulevel resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataEdulevelList = response.body().getData();
                        listEdulevel.add("Semua Tingkatan Kelas");
                        for (int i = 0; i < dataEdulevelList.size(); i++) {
                            edulevel_name = dataEdulevelList.get(i).getEdulevel_name();
                            listEdulevel.add(edulevel_name);
                        }
                        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                SilabusView.this, R.layout.spinner_full, listEdulevel) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        int spinnerPosition = adapterRaport.getPosition("Semua Tingkatan Kelas");
                        adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_edulevel.setAdapter(adapterRaport);
                        sp_edulevel.setSelection(spinnerPosition);
                        sp_edulevel.setOnItemSelectedListener((parent, view, position, id) ->{
                            if (position == 0){
                                sp_mapel.setEnabled(false);
                            }else {
                                edulevel_id = dataEdulevelList.get(position - 1).getEdulevelid();
                                sp_mapel.setEnabled(true);
                                listMapel.clear();
                                dapat_mapel();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListEdulevel> call, Throwable t) {
                Log.e("Gagal",t.toString());
            }
        });
    }

    private void dapat_mapel(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cource_get(authorization,school_code.toLowerCase(),member_id,edulevel_id,scyear_id);
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
                        listMapel.add("Semua Mata Pelajaran");
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cources_name = response.body().getData().get(i).getCources_name();
                            listMapel.add(cources_name);
                        }
                        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(
                                SilabusView.this, R.layout.spinner_full, listMapel) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_mapel.setAdapter(adapterMapel);
                        sp_mapel.setOnItemSelectedListener((parent, view, position, id) ->{
                            if (position > 0){
                                cources_id = dataMapelEduList.get(position - 1).getCourcesid();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.e("Gagal",t.toString());
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
        dialog = new ProgressDialog(SilabusView.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
