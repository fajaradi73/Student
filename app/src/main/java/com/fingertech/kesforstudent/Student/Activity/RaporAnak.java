package com.fingertech.kesforstudent.Student.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.dant.centersnapreyclerview.SnappingRecyclerView;
import com.fingertech.kesforstudent.Student.Adapter.RaporAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Student.Model.RaportModel;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaporAnak extends AppCompatActivity {

    TextView tv_semester,no_rapor,nama_kelas;
    Button btn_download,btn_go;
    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String statusrapor,peringkat,kritik;
    String authorization,school_code,classroom_id,student_id,semester_id;
    TextView tv_teori,tv_ulangan_harian,tv_praktikum,tv_eskul,tv_ujian_sekolah,tv_ujian_negara,tv_nilai_akhir,tv_rata_rata;

    RaporAdapter raporAdapter;
    RaportModel raportModel;
    List<RaportModel> raportModelList;
    SnappingRecyclerView snappyRecycleView;
    String teori,ulangan_harian,praktikum,eskul,ujian_sekolah,ujian_negara,mapel,nilai_akhir,rata_rata;
    CardView cardView;
    String semester_nama;
    private List<JSONResponse.DataSemester> dataSemesters;

    Spinner sp_semester;
    TextView status_rapor,tv_peringkat,tv_kritik;
    TableLayout tableLayout;

    String date,namakelas,walikelas;
    String guru,tanggal,type,nilai,deskripsi,start_date,end_date,semester,start_year,start_end;
    SharedPreferences sharedPreferences;
    SlidingUpPanelLayout slidingUpPanelLayout;
    ImageView star;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    ImageView arrow;
    LinearLayout drag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raport_view);
        toolbar         = findViewById(R.id.toolbar_rapor);
        snappyRecycleView    = findViewById(R.id.rv_rapor);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        sp_semester     = findViewById(R.id.sp_semester);
        tv_semester     = findViewById(R.id.tv_semester);
        no_rapor        = findViewById(R.id.tv_no_rapor);
        status_rapor    = findViewById(R.id.status_raport);
        tv_peringkat    = findViewById(R.id.peringkat);
        tv_kritik       = findViewById(R.id.kritik_saran);
        btn_go          = findViewById(R.id.btn_pilih);
        star            = findViewById(R.id.star);
        slidingUpPanelLayout    = findViewById(R.id.sliding_layout);
        tv_teori           = findViewById(R.id.nilai_teori);
        tv_ulangan_harian  = findViewById(R.id.ulangan_harian);
        tv_praktikum       = findViewById(R.id.latihan_praktikum);
        tv_eskul           = findViewById(R.id.eskul);
        tv_ujian_sekolah   = findViewById(R.id.ujian_sekolah);
        tv_ujian_negara    = findViewById(R.id.ujian_negara);
        tv_rata_rata       = findViewById(R.id.rata_rata);
        tv_nilai_akhir     = findViewById(R.id.nilai_akhir);
        cardView           = findViewById(R.id.card);
        arrow              = findViewById(R.id.arrow);
        drag               = findViewById(R.id.dragView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());

        Check_Semester();
        Classroom_detail();

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
                    arrow.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        btn_go.setOnClickListener(v -> {
            RaportAnak();
            dapat_semester();
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            arrow.setImageResource(R.drawable.ic_up_arrow);
        });

        drag.setOnClickListener(v -> {
            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);
            }else if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            }
        });

    }
    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {

                Log.i("KES", response.code() + "");

                JSONResponse.CheckSemester resource = response.body();

                status = resource.status;
                code    = resource.code;
                semester_id = response.body().getData();
                dapat_semester();
                RaportAnak();
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.ListSemester resource = response.body();

                status = resource.status;
                code = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    dataSemesters = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();
                    listSpinner.add("Pilih Semester...");
                    for (int i = 0; i < dataSemesters.size(); i++){
                        listSpinner.add(dataSemesters.get(i).getSemester_name());
                        if (dataSemesters.get(i).getSemester_id().equals(semester_id)){
                            semester_nama    = response.body().getData().get(i).getSemester_name();
                            start_date  = response.body().getData().get(i).getStart_date();
                            end_date    = response.body().getData().get(i).getEnd_date();
                            start_year  = response.body().getData().get(0).getStart_date();
                            start_end   = response.body().getData().get(1).getEnd_date();

                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                    RaporAnak.this,R.layout.spinner_full,listSpinner){
                                @Override
                                public boolean isEnabled(int position){
                                    if(position == 0)
                                    {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return false;
                                    }
                                    else
                                    {
                                        return true;
                                    }
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                                    if(position == 0){
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    }
                                    else {
                                        tv.setTextColor(Color.BLACK);
                                    }
                                    return view;
                                }
                            };
                            int spinnerPosition = adapterRaport.getPosition(semester_nama);
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_semester.setAdapter(adapterRaport);
                            sp_semester.setOnItemSelectedListener((parent, view, position, id) ->
                                    semester_id = dataSemesters.get(position-1).getSemester_id());
                            sp_semester.setSelection(spinnerPosition);
                        }
                    }
                    final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                            RaporAnak.this,R.layout.spinner_full,listSpinner){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                            {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position == 0){
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    int spinnerPosition = adapterRaport.getPosition(semester_nama);
                    adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                    sp_semester.setAdapter(adapterRaport);
                    sp_semester.setOnItemSelectedListener((parent, view, position, id) ->
                            semester_id = dataSemesters.get(position-1).getSemester_id());
                    sp_semester.setSelection(spinnerPosition);
                    tv_semester.setText("Semester "+semester_nama+" "+converTahun(start_year)+"/"+converTahun(start_end));
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
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
        dialog = new ProgressDialog(RaporAnak.this);
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

    private void RaportAnak(){
        progressBar();
        showDialog();
        Call<JSONResponse.Raport> call = mApiInterface.kes_rapor_score_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString(),semester_id.toString());
        call.enqueue(new Callback<JSONResponse.Raport>() {
            @Override
            public void onResponse(Call<JSONResponse.Raport> call, final Response<JSONResponse.Raport> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.Raport resource = response.body();

                status = resource.status;
                code    = resource.code;
                RaportModel raportModel = null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    raportModelList = new ArrayList<RaportModel>();
                    if(response.body().getData().getDetailScore() != null) {
                        statusrapor = response.body().getData().getClassroom().getPromoteText();
                        peringkat   = response.body().getData().getClassroom().getPromoteRanking();
                        kritik      = response.body().getData().getClassroom().getDescriptionText();
                        status_rapor.setText(statusrapor);
                        tv_peringkat.setText(peringkat);
                        tv_kritik.setText(kritik);
                        if (peringkat.equals("1")){
                            star.setVisibility(View.VISIBLE);
                        }else {
                            star.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < response.body().getData().getDetailScore().size(); i++) {
                            mapel           = response.body().getData().getDetailScore().get(i).getCourcesName();

                            raportModel = new RaportModel();
                            raportModel.setMapel(mapel);
                            raportModelList.add(raportModel);

                        }

                        no_rapor.setVisibility(View.GONE);
                        snappyRecycleView.setVisibility(View.VISIBLE);
                        cardView.setVisibility(View.VISIBLE);
                        raporAdapter = new RaporAdapter(raportModelList);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(RaporAnak.this);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        LinearSnapHelper snapHelper  = new LinearSnapHelper();
                        snappyRecycleView.setOnFlingListener(null);
                        snapHelper.attachToRecyclerView(snappyRecycleView);
//                        snappyRecycleView.setLayoutManager(new SnappyLinearLayoutManager(RaporAnak.this));
//                        snappyRecycleView.addItemDecoration(new ExampleDatePaddingItemDecoration(snappyRecycleView.getOrientation()));
                        snappyRecycleView.setLayoutManager(layoutManager);
                        snappyRecycleView.setAdapter(raporAdapter);
                        snappyRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);

                                int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                                int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                                int currentItem = 0;
                                float itemWidth = horizontalScrollRange * 1.0f / raportModelList.size();
                                itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                                if (scrollOffset != 0) {
                                    currentItem = Math.round(scrollOffset / itemWidth);
                                }
                                currentItem = (currentItem < 0) ? 0 : currentItem;
                                currentItem = (currentItem >= raportModelList.size()) ? raportModelList.size() - 1 : currentItem;
                                teori           = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getLatihanTeori().getScoreExam());
                                ulangan_harian  = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUlanganHarian().getScoreExam());
                                praktikum       = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getPraktikum().getScoreExam());
                                eskul           = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getEkstrakulikuler().getScoreExam());
                                ujian_sekolah   = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUjianSekolah().getScoreExam());
                                ujian_negara    = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUjianNegara().getScoreExam());
                                nilai_akhir     = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getFinalScore());
                                rata_rata       = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getClassAverageScore());
                                tv_teori.setText(convertZero(teori));
                                tv_ulangan_harian.setText(convertZero(ulangan_harian));
                                tv_praktikum.setText(convertZero(praktikum));
                                tv_eskul.setText(convertZero(eskul));
                                tv_ujian_negara.setText(convertZero(ujian_negara));
                                tv_ujian_sekolah.setText(convertZero(ujian_sekolah));
                                tv_nilai_akhir.setText(convertZero(nilai_akhir));
                                tv_rata_rata.setText(convertZero(rata_rata));
                            }
                        });
                    }
                    else {
                        no_rapor.setVisibility(View.VISIBLE);
                        snappyRecycleView.setVisibility(View.GONE);
                        star.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                        status_rapor.setText("-");
                        tv_peringkat.setText("-");
                        tv_kritik.setText("-");
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Raport> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
                no_rapor.setText(t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }

    String convertZero(String data) {
        if (data.equals("0.0")){
            data = "-";
        }
        return data;
    }

    private void Classroom_detail(){

        Call<JSONResponse.ClassroomDetail> call = mApiInterface.kes_classroom_detail_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ClassroomDetail>() {

            @Override
            public void onResponse(Call<JSONResponse.ClassroomDetail> call, final Response<JSONResponse.ClassroomDetail> response) {
                Log.i("KES", response.code() + "");


                JSONResponse.ClassroomDetail resource = response.body();

                status = resource.status;
                code    = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    walikelas    = response.body().getData().getHomeroom_teacher();
                    namakelas    = response.body().getData().getClassroom_name();
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.ClassroomDetail> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(RaporAnak.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
