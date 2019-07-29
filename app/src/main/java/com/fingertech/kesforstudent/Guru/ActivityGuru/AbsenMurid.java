package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.CustomView.CustomViewPager;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsen;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi.AdapterDetailAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAbsenGuru;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelArrayAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAtitude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDetailAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAttendance;
import com.fingertech.kesforstudent.MainActivity;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.github.florent37.shapeofview.shapes.RoundRectView;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
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
    List<ModelDataAttidude> modelDataAttidudes;
    AdapterAbsen adapterAbsen;
    List<ModelAbsenGuru> modelAbsenGuruList = new ArrayList<>();
//    List<ModelCodeAttidude> modelCodeAttidudes = new ArrayList<>();
    List<ModelArrayAbsen> modelArrayAbsenList;
    ModelDetailAbsen modelDetailAbsen;
    List<ModelDetailAbsen> modelDetailAbsenList;
    ModelAbsenGuru modelAbsenGuru;
    EditText et_search;
    ModelDataAttidude modelDataAttidude;
//    ModelCodeAttidude modelCodeAttidude;
    ModelArrayAbsen modelArrayAbsen;
    Toolbar toolbar;
    Auth mApiInterface;
    String  schedule_id,authorization,school_code,member_id,scyear_id, classroom,code,nama,id_kelas,student_id,attidude,absentcode,absentwarna;
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
    private List<ModelAtitude> modelAtitudeList;
    private ModelAtitude modelAtitude;
    private String attidudename,attidude_id,attidude_color,grade_code,grade_id,picture;
    private String[] namaabsen  = new String[]{"H","A","I","S","T","D"};
    private String[] idabsen     = new String[]{"6","5","3","4","2","1"};
    private String[] colorabsen     = new String[]{"#A2FB5E","#CF2138","#EFE138","#36B2E9","#2C3039","#529FBF"};
    JSONObject jsonObject;
    JSONArray jsonArray,arrayDetails;
    Dialog mBottomSheetDialog;
    String absen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_murid);

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
        mBottomSheetDialog  = new Dialog(AbsenMurid.this);

        getAtitude();
        getatitude();
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
                                                schedule_id     = jadwalKelasGuru.getCscheduletimeid();
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
            case R.id.item_info:
                Dialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_absen_guru, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        if (mBottomSheetDialog.isShowing()){
            AlertDialog.Builder builder = new AlertDialog.Builder(AbsenMurid.this,R.style.DialogTheme);
            builder.setTitle("Apakah anda ingin keluar?");
            builder.setMessage("Jika anda keluar, absen yang sudah anda input akan hilang.");
            builder.setIcon(R.drawable.ic_info_kes);
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mBottomSheetDialog.dismiss();
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
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
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        ll_nojadwal.setVisibility(View.GONE);
                        ll_absen.setVisibility(View.VISIBLE);
                        modelAbsenGuruList.clear();
                        for (int i = 0 ;i < response.body().getData().getStudentAbsent().size();i++){
                            nama        = response.body().getData().getStudentAbsent().get(i).getFullname();
                            nis         = response.body().getData().getStudentAbsent().get(i).getMemberCode();
                            picture     = response.body().getData().getStudentAbsent().get(i).getPicture();
                            student_id  = response.body().getData().getStudentAbsent().get(i).getMemberid();
                            if (response.body().getData().getStudentAbsent().get(i).getAbsenDetail() != null) {
                                absen = "update";
                                modelArrayAbsenList = new ArrayList<>();
                                for (JSONResponse.AbsenDetailItem dataAbsen : response.body().getData().getStudentAbsent().get(i).getAbsenDetail()) {
                                    for (JSONResponse.AttendanceDetailItem attendanceDetailItem : dataAbsen.getAttendanceDetail()) {
                                        absentcode  = attendanceDetailItem.getTypeText();
                                        absentwarna = attendanceDetailItem.getBgcolor();
                                        modelArrayAbsen = new ModelArrayAbsen();
                                        modelArrayAbsen.setCodeabsen(absentcode);
                                        modelArrayAbsen.setWarna(absentwarna);
                                        modelArrayAbsen.setNis(nis);
                                        modelArrayAbsenList.add(modelArrayAbsen);
                                    }
                                }
                            } else {
                                absen = "insert";
                                if (dataAttidudeList != null){
                                    modelArrayAbsenList = new ArrayList<>();
                                    modelArrayAbsen = new ModelArrayAbsen();
                                    modelArrayAbsen.setCodeabsen("H");
                                    modelArrayAbsen.setWarna("#B6F883");
                                    modelArrayAbsen.setNis(nis);
                                    modelArrayAbsenList.add(modelArrayAbsen);
                                    for (JSONResponse.DataAttidude attendanceDetailItem : dataAttidudeList) {
                                        absentwarna = attendanceDetailItem.getColour_code();
                                        modelArrayAbsen = new ModelArrayAbsen();
                                        modelArrayAbsen.setCodeabsen("A");
                                        modelArrayAbsen.setWarna(absentwarna);
                                        modelArrayAbsen.setNis(nis);
                                        modelArrayAbsenList.add(modelArrayAbsen);
                                    }
                                }
                            }
                            modelAbsenGuru = new ModelAbsenGuru();
                            modelAbsenGuru.setNama(nama);
                            modelAbsenGuru.setId(student_id);
                            modelAbsenGuru.setNis(nis);
                            modelAbsenGuru.setPicture(ApiClient.BASE_IMAGE+picture);
                            modelAbsenGuru.setModelArrayAbsenList(modelArrayAbsenList);
                            modelAbsenGuruList.add(modelAbsenGuru);
                        }
                        adapterAbsen = new AdapterAbsen(AbsenMurid.this,modelAbsenGuruList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(AbsenMurid.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rv_absen.setLayoutManager(layoutManager);
                        rv_absen.setAdapter(adapterAbsen);
                        adapterAbsen.setOnItemClickListener((view, position) -> {
                            CardView btn_info;
                            RoundRectView iv_close;
                            CustomViewPager viewpager;
                            AdapterDetailAbsen adapterDetailAbsen;

                            view        = getLayoutInflater().inflate(R.layout.activity_detail_absen_guru,null);
                            viewpager   = view.findViewById(R.id.pagerabsen);
                            iv_close    = view.findViewById(R.id.iv_close);
                            btn_info    = view.findViewById(R.id.cv_informasi);

                            mBottomSheetDialog.setContentView(view);
                            mBottomSheetDialog.setCancelable(false);
                            mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT);
                            mBottomSheetDialog.getWindow().setGravity(Gravity.CENTER);
                            mBottomSheetDialog.show();

                            modelDetailAbsenList = new ArrayList<>();
                            for (JSONResponse.StudentAbsentItem studentAbsentItem : response.body().getData().getStudentAbsent()) {
                                nama    = studentAbsentItem.getFullname();
                                nis     = studentAbsentItem.getMemberCode();
                                student_id  = studentAbsentItem.getMemberid();
                                if (dataAttidudeList != null && detailList != null) {
                                    modelAtitudeList = new ArrayList<>();
                                    for (int o = 0; o < namaabsen.length; o++) {
                                        modelAtitude = new ModelAtitude();
                                        modelAtitude.setId("0");
                                        modelAtitude.setId_atitude(idabsen[o]);
                                        modelAtitude.setNama(namaabsen[o]);
                                        modelAtitude.setColor(colorabsen[o]);
                                        modelAtitudeList.add(modelAtitude);
                                    }
                                    modelDataAttidudes = new ArrayList<>();
                                    modelDataAttidude   = new ModelDataAttidude();
                                    modelDataAttidude.setColour_code("#A2FB5E");
                                    modelDataAttidude.setAttitude_name("Kehadiran");
                                    modelDataAttidude.setNis(nis);
                                    modelDataAttidude.setAttitudeid("0");
                                    modelDataAttidude.setModelAttidudeList(modelAtitudeList);
                                    modelDataAttidudes.add(modelDataAttidude);
                                    for (int i = 0 ; i < jsonArray.length();i++){
                                        try {
                                            attidudename    = jsonArray.getJSONObject(i).getString("attitude_name");
                                            attidude_id     = jsonArray.getJSONObject(i).getString("attitudeid");
                                            attidude_color  = jsonArray.getJSONObject(i).getString("colour_code");
                                            arrayDetails    = jsonArray.getJSONObject(i).getJSONArray("details");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        for (int o = 0 ; o < arrayDetails.length() ; o++){
                                            try {
                                                grade_code  = arrayDetails.getJSONObject(o).getString("attitude_grade_code");
                                                grade_id    = arrayDetails.getJSONObject(o).getString("attitudegradeid");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            modelAtitude = new ModelAtitude();
                                            modelAtitude.setColor(attidude_color);
                                            modelAtitude.setId(attidude_id);
                                            modelAtitude.setId_atitude(grade_id);
                                            modelAtitude.setNama(grade_code);
                                            modelAtitude.setNis(nis);
                                            modelAtitudeList.add(modelAtitude);
                                        }
                                        modelDataAttidude = new ModelDataAttidude();
                                        modelDataAttidude.setColour_code(attidude_color);
                                        modelDataAttidude.setAttitude_name(attidudename);
                                        modelDataAttidude.setNis(nis);
                                        modelDataAttidude.setAttitudeid(attidude_id);
                                        modelDataAttidude.setModelAttidudeList(modelAtitudeList);
                                        modelDataAttidudes.add(modelDataAttidude);
                                    }
                                }
                                modelDetailAbsen = new ModelDetailAbsen();
                                modelDetailAbsen.setNama(nama);
                                modelDetailAbsen.setId(student_id);
                                modelDetailAbsen.setNis(nis);
                                modelDetailAbsen.setModelDataAttidudeList(modelDataAttidudes);
                                modelDetailAbsenList.add(modelDetailAbsen);
                            }
                            adapterDetailAbsen = new AdapterDetailAbsen(AbsenMurid.this,modelDetailAbsenList,viewpager,view,schedule_id,mBottomSheetDialog,AbsenMurid.this,absen);
                            viewpager.setAdapter(adapterDetailAbsen);
                            viewpager.setCurrentItem(position);
                            viewpager.setOffscreenPageLimit(modelDetailAbsenList.size());
                            iv_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(AbsenMurid.this,R.style.DialogTheme);
                                    builder.setTitle("Apakah anda ingin keluar?");
                                    builder.setMessage("Jika anda keluar, absen yang sudah anda input akan hilang.");
                                    builder.setIcon(R.drawable.ic_info_kes);
                                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            mBottomSheetDialog.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            });
                            btn_info.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Dialog();
                                }
                            });
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
    private void getatitude(){

        Call<JsonElement> call = mApiInterface.kes_attitudes_get(authorization,school_code.toLowerCase(),classroom,scyear_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("sukses",response.code()+"");
                if (response.isSuccessful()){
                    JsonElement jsonElement = response.body();
                    try {
                        jsonObject    = new JSONObject(String.valueOf(jsonElement.getAsJsonObject()));
                        code          = jsonObject.getString("code");
                        status        = jsonObject.getInt("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        try {
                            jsonArray = new JSONArray(String.valueOf(jsonElement.getAsJsonObject().get("data")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("gagal",t.toString());
            }
        });
    }

}
