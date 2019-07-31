package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
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

public class KirimPesan extends AppCompatActivity {

    Spinner spinner;
    Spinner  spn_kelas, spn_siswa, spn_mapel, spn_teacher, spn_admin;
    EditText kt_judul, kt_pesan;
    Toolbar toolbar;

    SharedPreferences sharedpreferences,sharedViewpager;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id,classroom_id,edulevel_name,cources_id,cources_name,namamurid,student_id,type_id;
    String namaAdmin,adminId;

    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";

    List<String> listEdulevel           = new ArrayList<String>();
    List<String> listMapel              = new ArrayList<String>();
    List<String> listMurid              = new ArrayList<>();
    List<String> listAdmin              = new ArrayList<>();
    private List<JSONResponse.DataKelas> dataEdulevelList;
    private List<JSONResponse.DataMapelEdu> dataMapelEduList;
    private List<JSONResponse.DataMurid> dataMuridList;
    private List<JSONResponse.DataAdmin> dataAdminList;
    ProgressDialog dialog;
    Auth mApiInterface;
    int status;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_pesan);
        spinner = findViewById(R.id.sp_kirim_kepada);
        spn_kelas = findViewById(R.id.sp_kelas);
        spn_siswa = findViewById(R.id.sp_siswa);
        spn_mapel = findViewById(R.id.sp_mapel);
        spn_teacher = findViewById(R.id.sp_teacher);
        spn_admin = findViewById(R.id.sp_admin);
        kt_judul = findViewById(R.id.et_judul);
        kt_pesan = findViewById(R.id.et_pesan);
        toolbar = findViewById(R.id.toolbarTulisPesan);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kirim_kepada, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        sharedpreferences = this.getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");
//
//
        kt_judul.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        kt_pesan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (position == 0)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                }
                else if (position == 1)
                {
                    spn_kelas.setVisibility(View.VISIBLE);
                    type_id = "2";
                    dapat_edulevel();
                }
                else if (position == 2)
                {
                    spn_kelas.setVisibility(View.VISIBLE);
                    type_id = "3";
                    dapat_edulevel();
                }
                else if (position == 3)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    type_id = "4";
                }
                else if (position == 4)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.GONE);
                    type_id = "5";
                }
                else if (position == 5)
                {
                    spn_kelas.setVisibility(View.GONE);
                    spn_siswa.setVisibility(View.GONE);
                    spn_mapel.setVisibility(View.GONE);
                    spn_teacher.setVisibility(View.GONE);
                    spn_admin.setVisibility(View.VISIBLE);
                    type_id = "6";
                    dapat_admin();
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
                if (type_id == null){
                    FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }else if (type_id.equals("6")){
                    if (adminId == null){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else {
                        classroom_id = "-1";
                        student_id = "-1";
                        cources_id = "-1";
                        kirim_pesan();
                    }
                }else if (type_id.equals("4") || type_id.equals("5")){
                    adminId = "-1";
                    classroom_id = "-1";
                    student_id = "-1";
                    cources_id = "-1";
                    kirim_pesan();
                }
                else {
                    if (classroom_id == null){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else if (student_id == null){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else if (cources_id == null){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else if (kt_judul.getText().toString().trim().isEmpty()){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else if (kt_pesan.getText().toString().trim().isEmpty()){
                        FancyToast.makeText(getApplicationContext(),"harap diisi terlebih dahulu",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }else {
                        adminId = "-1";
                        kirim_pesan();
                    }
                }
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

    private void dapat_edulevel(){
        Call<JSONResponse.ListKelas> call = mApiInterface.kes_get_classroom_get(authorization,school_code.toLowerCase(),memberid,scyear_id);
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
                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(KirimPesan.this, R.layout.spinner_full, listEdulevel);
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            spn_kelas.setAdapter(adapterRaport);
                            spn_kelas.setOnItemSelectedListener((parent, view, position, id) -> {
                                if (position == 0) {
                                    classroom_id = null;
                                    spn_mapel.setEnabled(false);
                                } else {
                                    classroom_id = dataEdulevelList.get(position - 1).getClassroomid();
                                    spn_mapel.setEnabled(true);
                                    dapat_murid();
                                    spn_siswa.setVisibility(View.VISIBLE);
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
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),memberid, classroom_id,scyear_id);
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
                                final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(KirimPesan.this, R.layout.spinner_full, listMapel);
                                adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                spn_mapel.setAdapter(adapterMapel);
                                spn_mapel.setOnItemSelectedListener((parent, view, position, id) -> {
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
    private void dapat_murid(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListMurid> call = mApiInterface.kes_get_student_get(authorization,school_code.toLowerCase(),memberid,scyear_id,classroom_id);
        call.enqueue(new Callback<JSONResponse.ListMurid>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMurid> call, Response<JSONResponse.ListMurid> response) {
                Log.d("muridsukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.ListMurid resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        dapat_mapel();
                        dataMuridList   = response.body().getData();
                        if (listMurid!=null){
                            listMurid.clear();
                            listMurid.add("Pilih Murid");
                            for (int i = 0 ; i < response.body().getData().size();i++){
                                namamurid   = response.body().getData().get(i).getFullname();
                                listMurid.add(namamurid);
                                final ArrayAdapter<String> adapterMurid = new ArrayAdapter<String>(KirimPesan.this, R.layout.spinner_full, listMurid);
                                adapterMurid.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                spn_siswa.setAdapter(adapterMurid);
                                spn_siswa.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(Spinner parent, View view, int position, long id) {
                                        if (position == 0){
                                            student_id = null;
                                        }else {
                                            student_id  = dataMuridList.get(position-1).getMember_id();
                                            spn_mapel.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMurid> call, Throwable t) {
                Log.e("gagal",t.toString());
                hideDialog();
            }
        });
    }

    private void dapat_admin(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListAdmin> call   = mApiInterface.kes_get_admin_get(authorization,school_code.toLowerCase(),memberid,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListAdmin>() {
            @Override
            public void onResponse(Call<JSONResponse.ListAdmin> call, Response<JSONResponse.ListAdmin> response) {
                Log.d("ResponseAdmin",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.ListAdmin resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        dataAdminList   = response.body().getData();
                        if (listAdmin!=null){
                            listAdmin.clear();
                            listAdmin.add("Pilih Admin");
                            for (int i = 0 ; i < response.body().getData().size();i++){
                                namaAdmin   = response.body().getData().get(i).getFullname();
                                listAdmin.add(namaAdmin);
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(KirimPesan.this, R.layout.spinner_full, listAdmin);
                                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                spn_admin.setAdapter(adapter);
                                spn_admin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(Spinner parent, View view, int position, long id) {
                                        if (position == 0){
                                            adminId = null;
                                        }else {
                                            adminId = dataAdminList.get(position-1).getMemberid();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListAdmin> call, Throwable t) {
                Log.e("admingagal",t.toString());
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
        dialog = new ProgressDialog(KirimPesan.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    private void kirim_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.KirimPesanGuru> call = mApiInterface.kes_send_message(authorization,school_code.toLowerCase(),memberid,type_id,adminId,classroom_id,student_id,cources_id,kt_judul.getText().toString(),kt_pesan.getText().toString());
        call.enqueue(new Callback<JSONResponse.KirimPesanGuru>() {
            @Override
            public void onResponse(Call<JSONResponse.KirimPesanGuru> call, Response<JSONResponse.KirimPesanGuru> response) {
                Log.d("sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.KirimPesanGuru resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        FancyToast.makeText(getApplicationContext(),"Pesan berhasil terkirim",Toast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.KirimPesanGuru> call, Throwable t) {
                Log.e("gagal",t.toString());
                hideDialog();
            }
        });
    }
}
