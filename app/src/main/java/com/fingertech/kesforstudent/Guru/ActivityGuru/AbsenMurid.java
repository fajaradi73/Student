package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.CustomView.CustomViewPager;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi.AdapterAttidudes;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi.AdapterCodeAbsen;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsen;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi.AdapterDetailAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAbsenGuru;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelArrayAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAtitude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDetailAbsen;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenMurid extends AppCompatActivity {
    CardView btninfo;
    RecyclerView rv_absen;
    List<ModelDataAttidude> modelDataAttidudes = new ArrayList<>();
    AdapterAbsen adapterAbsen;
    List<ModelAbsenGuru> modelAbsenGuruList = new ArrayList<>();
//    List<ModelCodeAttidude> modelCodeAttidudes = new ArrayList<>();
    List<ModelArrayAbsen> modelArrayAbsenList = new ArrayList<>();
    ModelDetailAbsen modelDetailAbsen;
    List<ModelDetailAbsen> modelDetailAbsenList = new ArrayList<>();
    ModelAbsenGuru modelAbsenGuru;
    EditText et_search;
    ModelDataAttidude modelDataAttidude;
//    ModelCodeAttidude modelCodeAttidude;
    ModelArrayAbsen modelArrayAbsen;
    AdapterCodeAbsen adapterCodeAbsen;
    Toolbar toolbar;
    Auth mApiInterface;
    String  schedule_id,authorization,school_code,member_id,scyear_id, classroom,code,nama,id_kelas,bgcolor,attidude,absentcode,absentwarna;
    int status,statusattidude;
    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_CLASS_ID     = "classroom_id";
    public static final String TAG_YEAR_ID      = "scyear_id";
    String nis,days_name,day;
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", new Locale("in","ID"));
    LinearLayout ll_nojadwal,ll_absen,ll_loading;
    List<JSONResponse.DataAttidude> dataAttidudeList,listAttitude;
    List<JSONResponse.Detail> detailList;
    private List<ModelAtitude> modelAtitudeList = new ArrayList<>();
    private ModelAtitude modelAtitude;
    private String attidudename,attidude_id,attidude_color,grade_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_murid);
        btninfo             = findViewById(R.id.Cv_informasi);
        rv_absen            = findViewById(R.id.rv_absenguru);
        toolbar             = findViewById(R.id.toolbarAbsen);
        ll_nojadwal         = findViewById(R.id.no_jadwal);
        ll_absen            = findViewById(R.id.ll_absen);
        ll_loading          = findViewById(R.id.spin_kits);
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

        getAtitude();
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
            }
        });
        day = dateFormat.format(Calendar.getInstance().getTime());
        getMapel();

    }
    private void getMapel(){

        Call<JSONResponse.JadwalGuru> call = mApiInterface.kes_class_schedule_teacher_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.JadwalGuru>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalGuru> call, Response<JSONResponse.JadwalGuru> response) {
                Log.d("MapelSukses",response.code()+"");
                ll_loading.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body() != null){
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            for (JSONResponse.JadwalDataGuru jadwalDataGuru : response.body().getData().getSchedule_class()){
                                days_name   = jadwalDataGuru.getDay_name();
                                if (days_name.equals(day)){
                                    for (JSONResponse.JadwalKelasGuru jadwalKelasGuru : jadwalDataGuru.getSchedule_class()){
                                        id_kelas = jadwalKelasGuru.getClassroom_id();
                                        if (contains(jadwalDataGuru.getSchedule_class(),classroom)) {
                                            if (id_kelas.equals(classroom)) {
                                                ll_loading.setVisibility(View.VISIBLE);
                                                schedule_id = jadwalKelasGuru.getCscheduletimeid();
                                                GetStudent();
                                            }
                                        }else {
                                            ll_nojadwal.setVisibility(View.VISIBLE);
                                            ll_absen.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalGuru> call, Throwable t) {
                Log.e("eror",t.toString());
                ll_loading.setVisibility(View.GONE);
            }
        });
    }

    boolean contains(List<JSONResponse.JadwalKelasGuru> list,String id_kelas) {
        for (JSONResponse.JadwalKelasGuru item : list) {
            if (item.getClassroom_id().equals(id_kelas)) {
                return true;
            }
        }
        return false;
    }

    public void Dialog(){
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

    private void GetStudent(){
        Call<JSONResponse.Absen> call = mApiInterface.kes_classroom_absent_get(authorization,school_code,member_id,scyear_id,classroom,schedule_id);
        call.enqueue(new Callback<JSONResponse.Absen>() {
            @Override
            public void onResponse(Call<JSONResponse.Absen> call, Response<JSONResponse.Absen> response) {
                Log.d("suksesabsent",response.code()+"");
                ll_loading.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    JSONResponse.Absen resource = response.body();
                    status                      = resource.status;
                    code                        = resource.code;
                    if (status==1 && code.equals("DTS_SCS_0001")){
                        ll_nojadwal.setVisibility(View.GONE);
                        ll_absen.setVisibility(View.VISIBLE);
                        modelAbsenGuruList.clear();
                        for (JSONResponse.StudentAbsentItem studentAbsentItem : response.body().getData().getStudentAbsent()){
                            nama    = studentAbsentItem.getFullname();
                            nis     = studentAbsentItem.getMemberCode();
                            if (studentAbsentItem.getAbsenDetail() != null) {
                                for (JSONResponse.AbsenDetailItem dataAbsen : studentAbsentItem.getAbsenDetail()) {
                                    for (JSONResponse.AttendanceDetailItem attendanceDetailItem : dataAbsen.getAttendanceDetail()) {
                                        absentcode = attendanceDetailItem.getTypeText();
                                        absentwarna = attendanceDetailItem.getBgcolor();
                                        modelArrayAbsen = new ModelArrayAbsen();
                                        modelArrayAbsen.setCodeabsen(absentcode);
                                        modelArrayAbsen.setWarna(absentwarna);
                                        modelArrayAbsen.setNis(nis);
                                        modelArrayAbsenList.add(modelArrayAbsen);
                                    }
                                }
                            }else {
                                if (dataAttidudeList != null){
                                    modelArrayAbsen = new ModelArrayAbsen();
                                    modelArrayAbsen.setCodeabsen("H");
                                    modelArrayAbsen.setWarna("#B6F883");
                                    modelArrayAbsen.setNis(nis);
                                    modelArrayAbsenList.add(modelArrayAbsen);
                                    for (JSONResponse.DataAttidude attendanceDetailItem : dataAttidudeList) {
                                        absentwarna = attendanceDetailItem.getColour_code();
                                        modelArrayAbsen = new ModelArrayAbsen();
                                        modelArrayAbsen.setCodeabsen("B");
                                        modelArrayAbsen.setWarna(absentwarna);
                                        modelArrayAbsen.setNis(nis);
                                        modelArrayAbsenList.add(modelArrayAbsen);
                                    }
                                }
                            }
                            modelAbsenGuru = new ModelAbsenGuru();
                            modelAbsenGuru.setNama(nama);
                            modelAbsenGuru.setNis(nis);
                            modelAbsenGuru.setModelArrayAbsenList(modelArrayAbsenList);
                            modelAbsenGuruList.add(modelAbsenGuru);
                        }
                        adapterAbsen = new AdapterAbsen(AbsenMurid.this,modelAbsenGuruList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AbsenMurid.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rv_absen.setLayoutManager(layoutManager);
                        rv_absen.setAdapter(adapterAbsen);

                        adapterAbsen.setOnItemClickListener(new AdapterAbsen.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                CardView iv_close,btn_info;
                                CustomViewPager viewpager;
                                AdapterDetailAbsen adapterDetailAbsen;

                                view        = getLayoutInflater().inflate(R.layout.activity_detail_absen_guru,null);
                                viewpager   = view.findViewById(R.id.pagerabsen);
                                iv_close    = view.findViewById(R.id.iv_close);
                                btn_info    = view.findViewById(R.id.cv_informasi);
                                final Dialog mBottomSheetDialog = new Dialog(AbsenMurid.this);
                                mBottomSheetDialog.setContentView(view);
                                mBottomSheetDialog.setCancelable(true);
                                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
                                mBottomSheetDialog.show();
                                if (modelDetailAbsenList != null){
                                    modelDetailAbsenList.clear();
                                }

                                for (JSONResponse.StudentAbsentItem studentAbsentItem : response.body().getData().getStudentAbsent()) {
                                    nama    = studentAbsentItem.getFullname();
                                    nis     = studentAbsentItem.getMemberCode();
                                    if (modelDataAttidudes != null){
                                        modelDataAttidudes.clear();
                                    }
                                    modelDataAttidude   = new ModelDataAttidude();
                                    modelDataAttidude.setColour_code("#A2FB5E");
                                    modelDataAttidude.setAttitude_name("Kehadiran");
                                    modelDataAttidude.setNis(nis);
                                    modelDataAttidude.setAttitudeid("0");
                                    modelDataAttidudes.add(modelDataAttidude);
                                    for (JSONResponse.DataAttidude dataAttidude : dataAttidudeList){
                                        attidudename        = dataAttidude.getAttitude_grade_name();
                                        attidude_id         = dataAttidude.getAttitudeid();
                                        attidude_color      = dataAttidude.getColour_code();
                                        modelDataAttidude   = new ModelDataAttidude();
                                        modelDataAttidude.setColour_code(attidude_color);
                                        modelDataAttidude.setAttitude_name(attidudename);
                                        modelDataAttidude.setNis(nis);
                                        modelDataAttidude.setAttitudeid(attidude_id);
                                        modelDataAttidudes.add(modelDataAttidude);
                                    }
                                    modelDetailAbsen = new ModelDetailAbsen();
                                    modelDetailAbsen.setNama(nama);
                                    modelDetailAbsen.setNis(nis);
                                    modelDetailAbsen.setModelDataAttidudeList(modelDataAttidudes);
                                    modelDetailAbsenList.add(modelDetailAbsen);
                                }
                                if (modelAtitudeList != null){
                                    modelAtitudeList.clear();
                                }
                                for (JSONResponse.Detail detail : detailList){
                                    grade_code  = detail.getAttitude_grade_code();
                                    modelAtitude = new ModelAtitude();
                                    modelAtitude.setColor(attidude_color);
                                    modelAtitude.setId(attidude_id);
                                    modelAtitude.setNama(grade_code);
                                    modelAtitude.setNis(nis);
                                    modelAtitudeList.add(modelAtitude);
                                }
                                adapterDetailAbsen = new AdapterDetailAbsen(AbsenMurid.this,modelDetailAbsenList,viewpager,modelAtitudeList,view);
                                viewpager.setAdapter(adapterDetailAbsen);
                                viewpager.setCurrentItem(position);
                                iv_close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mBottomSheetDialog.dismiss();
                                    }
                                });
                                btn_info.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Dialog();
                                    }
                                });
                            }
                        });


                    }

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Absen> call, Throwable t) {
                Log.e("eror_absen",t.toString());
                ll_loading.setVisibility(View.GONE);
            }
        });
    }

    private void getAtitude(){
        Call<JSONResponse.Attidude> call = mApiInterface.kes_attitude_get(authorization,school_code.toLowerCase(),classroom,scyear_id);
        call.enqueue(new Callback<JSONResponse.Attidude>() {
            @Override
            public void onResponse(Call<JSONResponse.Attidude> call, Response<JSONResponse.Attidude> response) {
                Log.d("attitude_sukses",response.code()+"");
                if (response.isSuccessful()){
                    if (response.body() != null){
                        status  = response.body().statusattidude;
                        code    = response.body().codeattidude;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            dataAttidudeList = response.body().getDataattidude();
                            for (JSONResponse.DataAttidude dataAttidude : dataAttidudeList){
                                detailList = dataAttidude.getData();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Attidude> call, Throwable t) {
                Log.e("erorAtitude",t.toString());
            }
        });
    }

}
