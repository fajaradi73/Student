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
import com.fingertech.kesforstudent.Student.Adapter.LessonAdapter;
import com.fingertech.kesforstudent.Student.Model.LessonModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kesforstudent.Student.Activity.JadwalPelajaran.my_lesson_preferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatFragment extends Fragment {


    public RiwayatFragment() {
        // Required empty public constructor
    }

    private SharedPreferences sharedPreferences;
    private String authorization,school_code,classroom_id,student_id,cources_id;
    private Auth mApiInterface;
    private RecyclerView rv_lesson;
    private TextView tv_no_lesson;
    private String tanggal,nama,mapel,lampiran,title,desc,materi;
    private int status;
    private String code;
    private LessonAdapter lessonAdapter;
    private LessonModel lessonModel;
    private List<LessonModel> lessonModelList = new ArrayList<>();
    private ProgressDialog dialog;
    private List<JSONResponse.ListLesson> listLessonList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);
        tv_no_lesson    = view.findViewById(R.id.hint_lesson);
        rv_lesson       = view.findViewById(R.id.rv_lesson);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

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
                        if (listLessonList.size() > 0) {
                            tv_no_lesson.setVisibility(View.GONE);
                            rv_lesson.setVisibility(View.VISIBLE);
                            mapel = response.body().getData().getCources_name();
                            for (JSONResponse.ListLesson lessonData : listLessonList) {
                                tanggal     = lessonData.getReview_date();
                                nama        = lessonData.getFullname();
                                title       = lessonData.getReview_title();
                                lampiran    = lessonData.getReview_file();
                                desc        = lessonData.getReview_desc();
                                materi      = lessonData.getReview_materi();
                                lessonModel = new LessonModel();
                                lessonModel.setTanggal(convertTanggal(tanggal));
                                lessonModel.setNama(nama);
                                lessonModel.setMapel(mapel);
                                lessonModel.setTitle(title);
                                lessonModel.setLampiran(ApiClient.BASE_LESSON + lampiran);
                                lessonModel.setDesc(desc);
                                lessonModel.setMateri(materi);
                                lessonModelList.add(lessonModel);
                            }
                            lessonAdapter = new LessonAdapter(getContext(),lessonModelList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            rv_lesson.setLayoutManager(layoutManager);
                            rv_lesson.setAdapter(lessonAdapter);
                        }else {
                            tv_no_lesson.setVisibility(View.VISIBLE);
                            rv_lesson.setVisibility(View.GONE);
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


    private String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}
