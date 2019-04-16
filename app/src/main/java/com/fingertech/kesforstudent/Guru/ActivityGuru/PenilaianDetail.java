package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Service.App;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.google.android.gms.vision.L;
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
    RecyclerView rv_exam;
    CardView btn_lihat;
    Auth mApiInterface;
    int status;
    List<String> listExam           = new ArrayList<String>();
    List<JSONResponse.DataExam> dataExamList;
    JsonElement jsonElement;
    JSONObject jsonObject,studentObject,student_object,examObject,examsObject;
    JSONArray dataobject;
    String date,exam_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penilaian_detail);

        sp_exam         = findViewById(R.id.sp_exam);
        toolbar         = findViewById(R.id.toolbar_penilaian);
        rv_exam         = findViewById(R.id.rv_penilaian);
        btn_lihat       = findViewById(R.id.btn_lihat);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        edulevel_id         = sharedpreferences.getString("edulevel_id","");
        cources_id          = sharedpreferences.getString("cources_id","");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        dapat_exam();
        Check_Semester();
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
                                Log.d("data",position+"");
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListExam> call, Throwable t) {
                Log.e("Eror",t.toString());
            }
        });
    }

    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),edulevel_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {

                Log.i("KES", response.code() + "");

                JSONResponse.CheckSemester resource = response.body();

                status = resource.status;
                code    = resource.code;
                semester_id = response.body().getData();
                dapat_nilai();
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }

    private void dapat_nilai(){
        Call<JsonElement> call = mApiInterface.kes_score_get(authorization,school_code.toLowerCase(),member_id,scyear_id,edulevel_id,semester_id,cources_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("Sukses",response.code()+"");
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
                        for (int i = 0 ; i < dataobject.length() ; i++){
                            try {
                                studentObject = dataobject.getJSONObject(i).getJSONObject("students");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Iterator<String> iterator = studentObject.keys();
                            while (iterator.hasNext()){
                                Object obj = iterator.next();
                                student_object = null;
                                try {
                                    student_object = studentObject.getJSONObject(obj.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    examObject = student_object.getJSONObject("exams");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Iterator<String> iterator1 = examObject.keys();
                                while (iterator1.hasNext()){
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
                                    if (exam_type.equals("1")){
                                        Log.d("examobject",examsObject+"");
                                    }
                                }
                            }
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
