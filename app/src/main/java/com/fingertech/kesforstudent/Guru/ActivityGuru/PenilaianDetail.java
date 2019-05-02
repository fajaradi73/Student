package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterPenilaian;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterTanggal;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelPenilaian;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelTanggal;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;
import com.google.gson.JsonElement;
import com.rey.material.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PenilaianDetail extends AppCompatActivity {

    String authorization,school_code,member_id,scyear_id,edulevel_id,semester_id,cources_id,exam_name,code;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    Spinner sp_exam;
    Toolbar toolbar;
    RecyclerView rv_exam,rv_tanggal;
    CardView btn_lihat;
    Auth mApiInterface;
    int status;
    List<String> listExam           = new ArrayList<String>();
    List<JSONResponse.DataExam> dataExamList;
    JsonElement jsonElement;
    JSONObject jsonObject,studentObject,student_object,examObject,examsObject;
    JSONArray dataobject,jsonArray1,dataarray;
    String date,exam_type,exam_date,nilai_exam,tanggal_ujian;
    String nis,student, nama_anak,type_id;
    AdapterTanggal adapterTanggal;
    ModelTanggal modelTanggal;
    List<ModelTanggal> modelTanggalList = new ArrayList<>();
    ModelPenilaian modelPenilaian;
    List<ModelPenilaian> modelPenilaianList = new ArrayList<>();
    AdapterPenilaian adapterPenilaian;

    TextView hint_penilaian;
    LinearLayout ll_penilaian;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penilaian_detail);

        sp_exam         = findViewById(R.id.sp_exam);
        toolbar         = findViewById(R.id.toolbar_penilaian);
        rv_exam         = findViewById(R.id.rv_penilaian);
        rv_tanggal      = findViewById(R.id.rv_tanggal);
        btn_lihat       = findViewById(R.id.btn_lihat);
        hint_penilaian  = findViewById(R.id.hint_penilaian);
        ll_penilaian    = findViewById(R.id.ll_penilaian);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        edulevel_id         = sharedpreferences.getString("classroom_id","");
        cources_id          = sharedpreferences.getString("cources_id","");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        dapat_exam();
    }

    private void dapat_exam(){
        Call<JSONResponse.ListExam> call = mApiInterface.kes_exam_type_get(authorization,school_code.toLowerCase(),member_id);
        call.enqueue(new Callback<JSONResponse.ListExam>() {
            @Override
            public void onResponse(Call<JSONResponse.ListExam> call, Response<JSONResponse.ListExam> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()){
                    JSONResponse.ListExam lists = response.body();
                    status  = lists.status;
                    code    = lists.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        dataExamList = response.body().getData();
                        for (JSONResponse.DataExam dataExam : dataExamList){
                            exam_name = dataExam.getType_name();
                            listExam.add(exam_name);
                        }
                        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(PenilaianDetail.this,R.layout.spinner_full,listExam);
                        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_exam.setAdapter(adapterMapel);
                        sp_exam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                type_id = dataExamList.get(position).getTypeid();
                                dapat_nilai();
                            }
                        });
                        type_id = dataExamList.get(sp_exam.getSelectedItemPosition()).getTypeid();
                        Check_Semester();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListExam> call, Throwable t) {
                Log.e("Eror",t.toString());
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

    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),edulevel_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.CheckSemester resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    semester_id = response.body().getData();
                    dapat_nilai();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dapat_nilai(){
        progressBar();
        showDialog();
        Call<JsonElement> call = mApiInterface.kes_score_get(authorization,school_code.toLowerCase(),member_id,scyear_id,edulevel_id,semester_id,cources_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("Sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    jsonElement = response.body();
                    try {
                        jsonObject    = new JSONObject(String.valueOf(jsonElement.getAsJsonObject()));
                        code          = jsonObject.getString("code");
                        status        = jsonObject.getInt("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1&& code.equals("DTS_SCS_0001")){
                        try {
                            dataobject = new JSONArray(String.valueOf(jsonElement.getAsJsonObject().get("data")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0 ; i < dataobject.length() ; i++) {
                            try {
                                studentObject = dataobject.getJSONObject(i).getJSONObject("students");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator<String> iterator = studentObject.keys();
                            JSONArray jsonArray = new JSONArray();

                            while (iterator.hasNext()) {
                                Object obj = iterator.next();
                                student_object = null;
                                try {
                                    student_object = studentObject.getJSONObject(obj.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonArray.put(student_object);
                            }
                            if (modelPenilaianList != null) {
                                modelPenilaianList.clear();
                                for (int p = 0; p < jsonArray.length(); p++) {
                                    try {
                                        student = jsonArray.getJSONObject(p).getString("student_name");
                                        examObject = jsonArray.getJSONObject(p).getJSONObject("exams");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    student = student.replace("(", ":");
                                    student = student.replace(")", ":");
                                    String[] arrayString = student.split(":");
                                    nama_anak = arrayString[0];
                                    nis = arrayString[1];
                                    jsonArray1 = new JSONArray();
                                    Iterator<String> iterator1 = examObject.keys();
                                    if (modelTanggalList != null) {
                                        modelTanggalList.clear();

                                        while (iterator1.hasNext()) {
                                            Object objs = iterator1.next();
                                            examsObject = null;
                                            try {
                                                examsObject = examObject.getJSONObject(objs.toString());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray1.put(examsObject);
                                        }
                                            List<String> stringList = new ArrayList<>();
                                            for (int k = 0 ; k < jsonArray1.length();k++) {
                                                try {
                                                    exam_type = jsonArray1.getJSONObject(k).getString("exam_type");
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                stringList.add(exam_type);
                                                dataarray = new JSONArray();
                                                if (containsName(stringList, type_id)) {
                                                    if (exam_type.equals(type_id)) {
                                                        ll_penilaian.setVisibility(View.VISIBLE);
                                                        hint_penilaian.setVisibility(View.GONE);
                                                        try {
                                                            exam_date = jsonArray1.getJSONObject(k).getString("exam_date");
                                                            nilai_exam = jsonArray1.getJSONObject(k).getString("score_value");
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                        modelTanggal = new ModelTanggal();
                                                        modelTanggal.setTanggal(exam_date);
                                                        modelTanggalList.add(modelTanggal);
                                                        adapterTanggal = new AdapterTanggal(PenilaianDetail.this, modelTanggalList);
                                                        LinearLayoutManager layoutManager = new LinearLayoutManager(PenilaianDetail.this);
                                                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                                        adapterTanggal.select_row(0);
                                                        tanggal_ujian = modelTanggalList.get(0).getTanggal();
                                                        rv_tanggal.setLayoutManager(layoutManager);
                                                        rv_tanggal.setAdapter(adapterTanggal);
                                                        adapterTanggal.setOnItemClickListener(new AdapterTanggal.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(View view, int position) {
                                                                adapterTanggal.notifyDataSetChanged();
                                                                adapterTanggal.select_row(position);
                                                                tanggal_ujian = modelTanggalList.get(position).getTanggal();
                                                                if (modelPenilaianList!=null) {
                                                                    modelPenilaianList.clear();
                                                                    for (int p = 0; p < jsonArray.length(); p++) {
                                                                        try {
                                                                            student = jsonArray.getJSONObject(p).getString("student_name");
                                                                            examObject = jsonArray.getJSONObject(p).getJSONObject("exams");
                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        student = student.replace("(", ":");
                                                                        student = student.replace(")", ":");
                                                                        String[] arrayString = student.split(":");
                                                                        nama_anak = arrayString[0];
                                                                        nis = arrayString[1];
                                                                        Iterator<String> iterator1 = examObject.keys();
                                                                        while (iterator1.hasNext()) {
                                                                            Object objs = iterator1.next();
                                                                            examsObject = null;
                                                                            try {
                                                                                examsObject = examObject.getJSONObject(objs.toString());
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            try {
                                                                                exam_type = examsObject.getString("exam_type");
                                                                            } catch (JSONException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            if (exam_type.equals(type_id)) {
                                                                                try {
                                                                                    exam_date = examsObject.getString("exam_date");
                                                                                    nilai_exam = examsObject.getString("score_value");
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                if (exam_date.equals(tanggal_ujian)) {
                                                                                    modelPenilaian = new ModelPenilaian();
                                                                                    modelPenilaian.setNama(nama_anak);
                                                                                    modelPenilaian.setNis(nis);
                                                                                    modelPenilaian.setNilai(nilai_exam);
                                                                                    modelPenilaianList.add(modelPenilaian);
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                adapterPenilaian.notifyDataSetChanged();
                                                            }
                                                        });
                                                        if (exam_date.equals(tanggal_ujian)) {
                                                            modelPenilaian = new ModelPenilaian();
                                                            modelPenilaian.setNilai(nilai_exam);
                                                            modelPenilaian.setNama(nama_anak);
                                                            modelPenilaian.setNis(nis);
                                                            modelPenilaianList.add(modelPenilaian);
                                                        }
                                                    }
                                                } else {
                                                    ll_penilaian.setVisibility(View.GONE);
                                                    hint_penilaian.setVisibility(View.VISIBLE);
                                                }
                                            }
                                            adapterPenilaian = new AdapterPenilaian(PenilaianDetail.this, modelPenilaianList);
                                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(PenilaianDetail.this);
                                            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                                            rv_exam.setLayoutManager(layoutManager1);
                                            rv_exam.setAdapter(adapterPenilaian);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("erordata",t.toString());
                hideDialog();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    public boolean containsName(final List<String> list, final String name){
        return list.stream().map(String::toString).anyMatch(name::equals);
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
        dialog = new ProgressDialog(PenilaianDetail.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
