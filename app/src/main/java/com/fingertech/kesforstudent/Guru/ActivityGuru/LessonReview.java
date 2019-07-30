package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.developer.kalert.KAlertDialog;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterLesson;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelLesson;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LessonReview extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_lesson;
    TextView tv_no_lesson;
    Auth mApiInterface;
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    String authorization,school_code,teacher_id,scyear_id,cources_id,classroom_id,code;
    int status;
    SharedPreferences sharedpreferences;
    List<ModelLesson> modelLessonList = new ArrayList<>();
    ModelLesson modelLesson;
    AdapterLesson adapterLesson;
    String tanggal,nama,mapel,lampiran,title,desc,materi;
    NotificationManagerCompat notificationManager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_review);
        toolbar         = findViewById(R.id.toolbar_lesson);
        rv_lesson       = findViewById(R.id.rv_lesson);
        tv_no_lesson    = findViewById(R.id.no_lesson);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN, "");
        teacher_id          = sharedpreferences.getString(TAG_MEMBER_ID, "");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        cources_id          = getIntent().getStringExtra("cources_id");
        classroom_id        = getIntent().getStringExtra("classroom_id");

        notificationManager = NotificationManagerCompat.from(this);
        get_lesson();
    }

    private void get_lesson(){
        progressBar();
        showDialog();
        Call<JSONResponse.LessonReview> call = mApiInterface.kes_dashboard_lesson_get(authorization,school_code.toLowerCase(),teacher_id,cources_id,classroom_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.LessonReview>() {
            @Override
            public void onResponse(Call<JSONResponse.LessonReview> call, Response<JSONResponse.LessonReview> response) {
                Log.d("lesson_sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")) {
                            if (response.body().getData().getListLessonData().size() > 0) {
                                tv_no_lesson.setVisibility(View.GONE);
                                rv_lesson.setVisibility(View.VISIBLE);
                                for (JSONResponse.ListLesson listLesson : response.body().getData().getListLessonData()) {
                                    tanggal     = listLesson.getReview_date();
                                    nama        = listLesson.getFullname();
                                    mapel       = listLesson.getCources_name();
                                    title       = listLesson.getReview_title();
                                    lampiran    = listLesson.getReview_file();
                                    desc        = listLesson.getReview_desc();
                                    materi      = listLesson.getReview_materi();
                                    modelLesson = new ModelLesson();
                                    modelLesson.setTanggal(convertTanggal(tanggal));
                                    modelLesson.setNama(nama);
                                    modelLesson.setMapel(mapel);
                                    modelLesson.setTitle(title);
                                    modelLesson.setLampiran(ApiClient.BASE_LESSON + lampiran);
                                    modelLesson.setDesc(desc);
                                    modelLesson.setMateri(materi);
                                    modelLessonList.add(modelLesson);
                                }
                                adapterLesson = new AdapterLesson(LessonReview.this, modelLessonList,notificationManager,LessonReview.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(LessonReview.this);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                rv_lesson.setLayoutManager(layoutManager);
                                rv_lesson.setAdapter(adapterLesson);
                            }else {
                                tv_no_lesson.setVisibility(View.VISIBLE);
                                rv_lesson.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.LessonReview> call, Throwable t) {
                Log.e("ErrorLesson",t.toString());
                hideDialog();
            }
        });
    }


    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
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
        dialog = new ProgressDialog(LessonReview.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
