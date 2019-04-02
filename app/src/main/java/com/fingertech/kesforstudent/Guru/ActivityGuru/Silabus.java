package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
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
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterSilabus;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelSilabus;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Activity.JadwalUjian;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.fingertech.kesforstudent.Student.Activity.RaporAnak;
import com.fingertech.kesforstudent.Util.FileUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stone.vega.library.VegaLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Silabus extends AppCompatActivity {

    Auth mApiInterface;
    RecyclerView rv_silabus;
    Spinner sp_edulevel,sp_mapel,sp_kelas,sp_matapelajaran;
    String authorization,school_code,member_id,scyear_id,edulevel_id,edulevel_name,cources_id,cources_name,code;
    String mapel,datez,kelas,files,base_silabus;
    int status;
    Toolbar toolbar;
    TextView tv_hint;
    CardView btn_go;
    List<String> listEdulevel           = new ArrayList<String>();
    List<String> listMapel              = new ArrayList<String>();
    List<String> listKelas              = new ArrayList<String>();
    List<String> listMatapelajaran      = new ArrayList<String>();
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

    SlidingUpPanelLayout slidingUpPanelLayout;
    ImageView arrow;
    LinearLayout drag;
    TextView tv_nama_file;

    File file;
    SharedPreferences sharedpreferences;
    Intent intent;
    public final int SELECT_FILE = 12;
    CardView btn_upload,btn_simpan;
    Uri fileUri,uri;
    String kelas_id,mapel_id;
    EditText et_judul_materi;
    TextInputLayout til_judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silabus_view);
        toolbar     = findViewById(R.id.toolbarSilabus);
        rv_silabus  = findViewById(R.id.rv_silabus);
        tv_hint     = findViewById(R.id.hint_silabus);
        btn_go      = findViewById(R.id.btn_go);
        sp_edulevel = findViewById(R.id.sp_tingkatan_kelas);
        sp_mapel    = findViewById(R.id.sp_mapel);
        sp_kelas    = findViewById(R.id.sp_kelas);
        btn_simpan  = findViewById(R.id.btn_simpan);
        til_judul   = findViewById(R.id.til_judul);
        sp_matapelajaran        = findViewById(R.id.sp_matapelajaran);
        slidingUpPanelLayout    = findViewById(R.id.sliding_layout);
        drag                    = findViewById(R.id.dragView);
        arrow                   = findViewById(R.id.arrow);
        btn_upload              = findViewById(R.id.btn_upload);
        tv_nama_file            = findViewById(R.id.nama_file);
        et_judul_materi         = findViewById(R.id.et_judul_materi);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        base_silabus    = "https://www.kes.co.id/schoolc/assets/images/silabus/";
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

        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_full,listEdulevel);
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

        if (sp_edulevel.getSelectedItemPosition() == 0){
            edulevel_id = "-1";
            cources_id  = "-1";
            sp_mapel.setEnabled(false);
        }

        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_full,listMapel);
        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_mapel.setAdapter(adapterMapel);
        sp_mapel.setOnItemSelectedListener((parent, view, position, id) ->{
            if (position == 0){
                cources_id = "-1";
            }else {
                cources_id = dataMapelEduList.get(position - 1).getCourcesid();
            }
        });
        dapat_silabus();
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dapat_silabus();
            }
        });

        slidingUpPanelLayout.setFadeOnClickListener(view -> {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            arrow.setImageResource(R.drawable.ic_up_arrow);
        });
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {
                if (panelState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                    arrow.setImageResource(R.drawable.ic_up_arrow);
                }else if (panelState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                    arrow.setImageResource(R.drawable.ic_down_arrow);
                }
            }
        });

        drag.setOnClickListener(v -> {
            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);
            }else if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                arrow.setImageResource(R.drawable.ic_down_arrow);
            }
        });
        bottom_sheet();
    }

    private void bottom_sheet(){
        listKelas.add("-");
        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_full,listKelas);
        adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_kelas.setAdapter(adapterRaport);
        sp_kelas.setOnItemSelectedListener((parent, view, position, id) ->{
            if (position == 0){
                kelas_id  = null;
                mapel_id  = null;
                sp_matapelajaran.setEnabled(false);
            }else {
                kelas_id = dataEdulevelList.get(position - 1).getEdulevelid();
                sp_matapelajaran.setEnabled(true);
                dapat_mapel2();
            }
        });
        if (sp_kelas.getSelectedItemPosition() == 0){
//           edulevel_id = "-1";
//           cources_id  = "-1";
            sp_matapelajaran.setEnabled(false);
        }
        listMatapelajaran.add("-");
        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(Silabus.this,R.layout.spinner_full,listMatapelajaran);
        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_matapelajaran.setAdapter(adapterMapel);
        sp_matapelajaran.setOnItemSelectedListener((parent, view, position, id) ->{
            if (position == 0){
                mapel_id = "";
            }else {
                mapel_id = dataMapelEduList.get(position - 1).getCourcesid();
                Log.d("mapelid",mapel_id);
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        intent = new Intent();
                        intent.setType("documents/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                    }
                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(Silabus.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                //check all needed permissions together
                TedPermission.with(Silabus.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("Jika Anda menolak izin, Anda tidak dapat menggunakan layanan ini\n\nSilakan aktifkan izin di [Pengaturan] > [Izin]")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();
            }
        });
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void submit(){
        if (!validateKelas()){
            return;
        }else if (!validateMapel()){
            return;
        }else if (!validateJudul()){
            return;
        }else if (!validateFile()){
            return;
        }else {
            upload_file();
        }
    }
    private boolean validateMapel() {
        if (mapel_id == null) {
            Toast.makeText(getApplicationContext(),"Harap memilih mata pelajaran terlebih dahulu",Toast.LENGTH_LONG).show();
            return false;
        }else {
            Log.d("KES","Sukses");
        }
        return true;
    }
    private boolean validateKelas() {
        if (kelas_id == null) {
            Toast.makeText(getApplicationContext(),"Harap memilih Kelas terlebih dahulu",Toast.LENGTH_LONG).show();
            return false;
        }else {
            Log.d("KES","Sukses");
        }
        return true;
    }
    private boolean validateFile() {
        if (tv_nama_file.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap mengupload file terlebih dahulu",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean validateJudul() {
        if (et_judul_materi.getText().toString().trim().isEmpty()) {
            requestFocus(et_judul_materi);
            return false;
        } else {
            til_judul.setErrorEnabled(false);
        }
        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                uri = data.getData();
                file = FileUtils.getFile(Silabus.this, uri);
                files   = String.valueOf(file);
                tv_nama_file.setText(files);
            }
        }
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
                        listKelas.add(edulevel_name);
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
        progressBar();
        showDialog();
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),member_id,edulevel_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListMapelEdu>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapelEdu> call, Response<JSONResponse.ListMapelEdu> response) {
                Log.d("MataPelajaran",response.code()+"");
                hideDialog();
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
                            listMatapelajaran.add(cources_name);
                        }
                    }
                    if (listMatapelajaran!=null){
                        listMatapelajaran.clear();
                        listMatapelajaran.add("-");
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cources_name = response.body().getData().get(i).getCources_name();
                            listMatapelajaran.add(cources_name);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.d("Gagal",t.toString());
                hideDialog();
            }
        });
    }
    private void dapat_mapel2(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),member_id,kelas_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListMapelEdu>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapelEdu> call, Response<JSONResponse.ListMapelEdu> response) {
                Log.d("MataPelajaran",response.code()+"");
                hideDialog();
                JSONResponse.ListMapelEdu resource = response.body();
                status  = resource.status;
                code    = resource.code;
                if (status==1 && code.equals("DTS_SCS_0001")){
                    dataMapelEduList    = response.body().getData();
                    if (listMatapelajaran!=null){
                        listMatapelajaran.clear();
                        listMatapelajaran.add("-");
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            cources_name = response.body().getData().get(i).getCources_name();
                            listMatapelajaran.add(cources_name);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.d("Gagal",t.toString());
                hideDialog();
            }
        });
    }
    private void dapat_silabus(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListSilabus> call = mApiInterface.kes_silabus_get(authorization,school_code.toLowerCase(),member_id,cources_id,edulevel_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListSilabus>() {
            @Override
            public void onResponse(Call<JSONResponse.ListSilabus> call, Response<JSONResponse.ListSilabus> response) {
                Log.v("Sukses",response.code()+"");
                JSONResponse.ListSilabus resource = response.body();
                hideDialog();
                status  = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    if (modelSilabusList!=null){
                        modelSilabusList.clear();
                        for (int i = 0 ; i < response.body().getData().size();i++){
                            mapel       = response.body().getData().get(i).getCources_name();
                            kelas       = response.body().getData().get(i).getEdulevel_name();
                            datez       = response.body().getData().get(i).getDatez();
                            files       = response.body().getData().get(i).getSilabus_file();
                            modelSilabus    = new ModelSilabus();
                            modelSilabus.setFile(base_silabus+files);
                            modelSilabus.setKelas(kelas);
                            modelSilabus.setMapel(mapel);
                            modelSilabus.setTanggal(datez);
                            modelSilabusList.add(modelSilabus);
                        }

                        tv_hint.setVisibility(View.GONE);
                        rv_silabus.setVisibility(View.VISIBLE);
                        adapterSilabus  = new AdapterSilabus(Silabus.this,modelSilabusList);
                        rv_silabus.setOnFlingListener(null);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Silabus.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_silabus.setLayoutManager(layoutManager);
                        rv_silabus.setAdapter(adapterSilabus);
                        adapterSilabus.notifyDataSetChanged();
                    }

                }else if (status == 0 && code.equals("DTS_ERR_0001")){
                    tv_hint.setVisibility(View.VISIBLE);
                    rv_silabus.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSilabus> call, Throwable t) {
            hideDialog();
            Log.e("Eror",t.toString());
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

    private void upload_file(){
        progressBar();
        showDialog();
        RequestBody photoBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("filename",
                file.getName(), photoBody);
        RequestBody ids         = RequestBody.create(MediaType.parse("multipart/form-data"), member_id);
        RequestBody courcesid   = RequestBody.create(MediaType.parse("multipart/form-data"), mapel_id);
        RequestBody schoolcode  = RequestBody.create(MediaType.parse("multipart/form-data"), school_code);
        RequestBody kelasid     = RequestBody.create(MediaType.parse("multipart/form-data"), kelas_id);
        RequestBody scyearid    = RequestBody.create(MediaType.parse("multipart/form-data"), scyear_id);
        RequestBody title       = RequestBody.create(MediaType.parse("multipart/form-data"), et_judul_materi.getText().toString());
        Call<JSONResponse.UploadSilabus> call = mApiInterface.kes_add_silabus_post(authorization.toString(),schoolcode,ids,courcesid,kelasid,photoPart,title,scyearid);
        call.enqueue(new Callback<JSONResponse.UploadSilabus>() {
            @Override
            public void onResponse(retrofit2.Call<JSONResponse.UploadSilabus> call, final Response<JSONResponse.UploadSilabus> response) {
                hideDialog();
                Log.i("Upload", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.UploadSilabus resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1&& code.equals("DTS_SCS_0001")) {
                        sp_kelas.setSelection(0);
                        sp_matapelajaran.setSelection(0);
                        et_judul_materi.setText("");
                        et_judul_materi.clearFocus();
                        tv_nama_file.setText("");
                        FancyToast.makeText(getApplicationContext(), "File berhasil diupload", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        arrow.setImageResource(R.drawable.ic_up_arrow);
                        dapat_silabus();
                    }
                }else {
                    FancyToast.makeText(getApplicationContext(), "File gagal diupload", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.UploadSilabus> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
    }
}
