package com.fingertech.kesforstudent.Student.Fragment.LessonFragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Adapter.LastEventAdapter;
import com.fingertech.kesforstudent.Student.Model.LastEventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kesforstudent.Student.Activity.JadwalPelajaran.my_lesson_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class KegiatanFragment extends Fragment {


    public KegiatanFragment() {
        // Required empty public constructor
    }


    private SharedPreferences sharedPreferences;
    private String authorization,school_code,classroom_id,student_id,cources_id;
    private Auth mApiInterface;
    private RecyclerView rv_last_event;
    private TextView tv_no_last_event;
    private int status;
    private String code;
    private ProgressDialog dialog;
    private List<JSONResponse.ListLesson> listLessonList;
    private LastEventAdapter lastEventAdapter;
    private List<LastEventModel> lastEventModelList = new ArrayList<>();
    private LastEventModel lastEventModel;
    private String tanggal_event,nama_event;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kegiatan, container, false);
        tv_no_last_event    = view.findViewById(R.id.hint_kegiatan);
        rv_last_event       = view.findViewById(R.id.rv_kegiatan);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        sharedPreferences   = getContext().getSharedPreferences(my_lesson_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        cources_id          = sharedPreferences.getString("cources_id",null);

        dapat_lesson();

        return view;
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
        dialog = new ProgressDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    private void dapat_lesson(){
        progressBar();
        showDialog();
        Call<JSONResponse.LessonReview> call = mApiInterface.kes_lesson_review_get(authorization,school_code.toLowerCase(),student_id,classroom_id,cources_id);
        call.enqueue(new Callback<JSONResponse.LessonReview>() {
            @Override
            public void onResponse(Call<JSONResponse.LessonReview> call, Response<JSONResponse.LessonReview> response) {
                Log.d("lessonSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.LessonReview lessonReview = response.body();
                    status  = lessonReview.status;
                    code    = lessonReview.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        listLessonList  = response.body().getData().getListLessons();
                        if (response.body().getData().getLast_event().size() > 0){
                            tv_no_last_event.setVisibility(View.GONE);
                            rv_last_event.setVisibility(View.VISIBLE);
                            Collections.sort(response.body().getData().getLast_event(), byDate);
                            for (JSONResponse.ListLastEvent lastEvent : response.body().getData().getLast_event()){
                                tanggal_event   = lastEvent.getExam_date();
                                nama_event      = lastEvent.getExam_tipe();
                                lastEventModel  = new LastEventModel();
                                lastEventModel.setNama(nama_event);
                                lastEventModel.setTanggal(converDate(convertTanggal(tanggal_event)));
                                lastEventModel.setBulan(converBulan(convertTanggal(tanggal_event)));
                                lastEventModel.setTahun(converTahun(convertTanggal(tanggal_event)));
                                lastEventModelList.add(lastEventModel);
                            }
                            lastEventAdapter = new LastEventAdapter(lastEventModelList,getContext());
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            rv_last_event.setHasFixedSize(true);
                            rv_last_event.setLayoutManager(layoutManager);
                            rv_last_event.setAdapter(lastEventAdapter);
                        }else {
                            tv_no_last_event.setVisibility(View.VISIBLE);
                            rv_last_event.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.LessonReview> call, Throwable t) {
                hideDialog();
                Log.e("lessonGagal",t.toString());

            }
        });
    }

    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
        try {
            return calendarDateFormat.format(newDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converBulan(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM",new Locale("in","ID"));
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converTahun(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    static final Comparator<JSONResponse.ListLastEvent> byDate = new Comparator<JSONResponse.ListLastEvent>() {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);

        public int compare(JSONResponse.ListLastEvent ord1, JSONResponse.ListLastEvent ord2) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = newDateFormat.parse(ord1.getExam_date());
                d2 = newDateFormat.parse(ord2.getExam_date());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
//            return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
        }
    };
}
