package com.fingertech.kesforstudent.Student.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.CustomView.CustomLayoutManager;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Adapter.DetailAdapter;
import com.fingertech.kesforstudent.Student.Adapter.DetailRaporAdapter;
import com.fingertech.kesforstudent.Student.Adapter.RaporAdapter;
import com.fingertech.kesforstudent.Student.Model.DetailModel;
import com.fingertech.kesforstudent.Student.Model.DetailRaporModel;
import com.fingertech.kesforstudent.Student.Model.RaportModel;
import com.google.gson.JsonElement;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.rey.material.widget.Spinner;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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

public class DetailRaport extends AppCompatActivity {

    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    String authorization,school_code,classroom_id,student_id,semester_id,exam_type,cources_id;
    String date;
    RecyclerView rv_mapel,rv_nilai;
    JSONObject jsonObject,type_exam,innerJsonObject,statusobject;
    JSONArray detailScore;
    RaporAdapter raporAdapter;
    RaportModel raportModel;
    List<RaportModel> raportModelList = new ArrayList<>();
    IndefinitePagerIndicator indefinitePagerIndicator;
    String typename,mapel,nilai_akhir;
    LinearSnapHelper snapHelper;
    DetailAdapter detailAdapter;
    DetailModel detailModel;
    List<DetailModel>detailModelList = new ArrayList<>();
    int posisi;
    SlidingUpPanelLayout slidingUpPanelLayout;
    Spinner sp_semester;
    ImageView arrow;
    LinearLayout drag,ll_raport;
    String semester_nama;
    private List<JSONResponse.DataSemester> dataSemesters;
    TextView tv_norapor;
    String detail;
    Dialog myDialog;
    List<JSONResponse.DataDetailRapor> detailRaporList;
    CardView iv_close;
    RecyclerView rv_detail;
    DetailRaporAdapter detailRaporAdapter;
    List<DetailRaporModel> detailRaporModelList = new ArrayList<>();
    DetailRaporModel detailRaporModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raport_view);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        toolbar         = findViewById(R.id.toolbar_rapor);
        rv_mapel        = findViewById(R.id.rv_rapor);
        rv_nilai        = findViewById(R.id.rv_nilai);
        sp_semester     = findViewById(R.id.sp_semester);
        slidingUpPanelLayout    = findViewById(R.id.sliding_layout);
        arrow              = findViewById(R.id.arrow);
        drag               = findViewById(R.id.dragView);
        tv_norapor         = findViewById(R.id.tv_no_rapor);
        ll_raport          = findViewById(R.id.ll_raport);
        indefinitePagerIndicator    = findViewById(R.id.recyclerview_pager_indicator);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        student_id          = getIntent().getStringExtra("student_id");
        classroom_id        = getIntent().getStringExtra("classroom_id");
        posisi              = getIntent().getIntExtra("posisi",0);
        semester_id         = getIntent().getStringExtra("semester_id");
        DateFormat df       = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date                = df.format(Calendar.getInstance().getTime());

        if (semester_id != null){
            RaportAnak();
        }

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

        drag.setOnClickListener(v -> {
            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);
            }else if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            }
        });
        dapat_semester();
        myDialog = new Dialog(this);
    }
    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.CheckSemester resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    semester_id = response.body().getData();
                    RaportAnak();
                }
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

                if (response.isSuccessful()) {
                    JSONResponse.ListSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataSemesters = response.body().getData();
                        List<String> listSpinner = new ArrayList<String>();
                        listSpinner.add("Pilih Semester...");
                        for (int i = 0; i < dataSemesters.size(); i++) {
                            listSpinner.add(dataSemesters.get(i).getSemester_name());
                            if (dataSemesters.get(i).getSemester_id().equals(semester_id)) {
                                semester_nama = response.body().getData().get(i).getSemester_name();
                                final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                        DetailRaport.this, R.layout.spinner_full, listSpinner) {
                                    @Override
                                    public boolean isEnabled(int position) {
                                        if (position == 0) {
                                            // Disable the first item from Spinner
                                            // First item will be use for hint
                                            return false;
                                        } else {
                                            return true;
                                        }
                                    }

                                    @Override
                                    public View getDropDownView(int position, View convertView,
                                                                ViewGroup parent) {
                                        View view = super.getDropDownView(position, convertView, parent);
                                        TextView tv = (TextView) view;
                                        if (position == 0) {
                                            // Set the hint text color gray
                                            tv.setTextColor(Color.GRAY);
                                        } else {
                                            tv.setTextColor(Color.BLACK);
                                        }
                                        return view;
                                    }
                                };
                                int spinnerPosition = adapterRaport.getPosition(semester_nama);
                                adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                sp_semester.setAdapter(adapterRaport);
                                sp_semester.setOnItemSelectedListener((parent, view, position, id) -> {
                                    if (position > 0) {
                                        semester_id = dataSemesters.get(position - 1).getSemester_id();
                                    }
                                });

                                sp_semester.setSelection(spinnerPosition);
                            }
                        }
                        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                DetailRaport.this, R.layout.spinner_full, listSpinner) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        int spinnerPosition = adapterRaport.getPosition(semester_nama);
                        adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_semester.setAdapter(adapterRaport);
                        sp_semester.setOnItemSelectedListener((parent, view, position, id) -> {
                            if (position > 0) {
                                semester_id = dataSemesters.get(position - 1).getSemester_id();
                                dapat_semester();
                                RaportAnak();
                                posisi  = 1;
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                arrow.setImageResource(R.drawable.ic_up_arrow);
                            }
                        });
                        sp_semester.setSelection(spinnerPosition);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void RaportAnak(){
        progressBar();
        showDialog();
        Call<JsonElement>call = mApiInterface.kes_rapor_get(authorization,school_code.toLowerCase(),student_id,classroom_id,semester_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("RaporSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JsonElement resource = response.body();
                    try {
                        statusobject    = new JSONObject(String.valueOf(resource.getAsJsonObject()));
                        code            = statusobject.getString("code");
                        status          = statusobject.getInt("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        try {
                            jsonObject = new JSONObject(String.valueOf(resource.getAsJsonObject().get("data")));
                            detail     = jsonObject.getString("detail_score");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (detail.equals("null")){
                            tv_norapor.setVisibility(View.VISIBLE);
                            rv_mapel.setVisibility(View.GONE);
                            rv_nilai.setVisibility(View.GONE);
                            ll_raport.setVisibility(View.GONE);
                            indefinitePagerIndicator.setVisibility(View.GONE);
                        }else {
                            try {
                                detailScore = jsonObject.getJSONArray("detail_score");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            tv_norapor.setVisibility(View.GONE);
                            rv_mapel.setVisibility(View.VISIBLE);
                            rv_nilai.setVisibility(View.VISIBLE);
                            ll_raport.setVisibility(View.VISIBLE);
                            indefinitePagerIndicator.setVisibility(View.VISIBLE);
                            for (int i = 0; i < detailScore.length(); i++) {
                                snapHelper = new LinearSnapHelper();
                                try {
                                    mapel = detailScore.getJSONObject(i).getString("cources_name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                raportModel = new RaportModel();
                                raportModel.setMapel(mapel);
                                raportModelList.add(raportModel);
                            }
                            final CustomLayoutManager layoutManager = new CustomLayoutManager(DetailRaport.this);
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            raporAdapter = new RaporAdapter(raportModelList);
                            rv_mapel.setOnFlingListener(null);
                            indefinitePagerIndicator.attachToRecyclerView(rv_mapel);
                            snapHelper.attachToRecyclerView(rv_mapel);
                            rv_mapel.setLayoutManager(layoutManager);
                            rv_mapel.setAdapter(raporAdapter);
                            rv_mapel.scrollToPosition(posisi);
                            rv_mapel.smoothScrollToPosition(posisi);
                            rv_mapel.smoothScrollBy(1, 0);
                            rv_mapel.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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
                                    try {
                                        cources_id  = detailScore.getJSONObject(currentItem).getString("courcesid");
                                        type_exam   = detailScore.getJSONObject(currentItem).getJSONObject("type_exam");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    Iterator<String> iterator = type_exam.keys();
                                    if (detailModelList != null) {
                                        detailModelList.clear();
                                        while (iterator.hasNext()) {
                                            Object obj = iterator.next();
                                            innerJsonObject = null;
                                            try {
                                                innerJsonObject = type_exam.getJSONObject(obj.toString());
                                                typename    = innerJsonObject.getString("type_name");
                                                nilai_akhir = innerJsonObject.getString("score_exam");
                                                exam_type   = innerJsonObject.getString("type_id");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            detailModel = new DetailModel();
                                            detailModel.setScore_exam(convertZero(nilai_akhir));
                                            detailModel.setExam_type(exam_type);
                                            detailModel.setType_name(typename);
                                            detailModelList.add(detailModel);
                                        }
                                        detailAdapter = new DetailAdapter(detailModelList);
                                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(DetailRaport.this);
                                        layoutManager1.setOrientation(RecyclerView.VERTICAL);
                                        rv_nilai.setLayoutManager(layoutManager1);
                                        rv_nilai.setAdapter(detailAdapter);
                                        detailAdapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                exam_type   = detailModelList.get(position).getExam_type();
                                                detail_raport();
                                            }
                                        });
                                        detailAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("RaporEror",t.toString());
                hideDialog();
            }
        });
    }

    String convertZero(String data) {
        if (data.equals("0.0")){
            data = "-";
        }else if (data.equals("0")){
            data = "-";
        }
        return data;
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
        dialog = new ProgressDialog(DetailRaport.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DetailRaport.this,RaporAnak.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                intent.putExtra("semester_id", semester_id);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(DetailRaport.this,RaporAnak.class);
        intent.putExtra("authorization",authorization);
        intent.putExtra("school_code",school_code);
        intent.putExtra("student_id", student_id);
        intent.putExtra("classroom_id", classroom_id);
        intent.putExtra("semester_id", semester_id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    private void detail_raport(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListDetailRapor> call = mApiInterface.kes_cources_score_detail_get(authorization,school_code.toLowerCase(),student_id,classroom_id,cources_id,semester_id,exam_type);
        call.enqueue(new Callback<JSONResponse.ListDetailRapor>() {
            @Override
            public void onResponse(Call<JSONResponse.ListDetailRapor> call, Response<JSONResponse.ListDetailRapor> response) {
                Log.d("suksesrapor",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.ListDetailRapor listDetailRapor = response.body();
                    status  = listDetailRapor.status;
                    code    = listDetailRapor.code;
                    if (status == 1 && code.equals("CSD_SCS_0001") || status == 1 && code.equals("DTS_SCS_0001")){
                        detailRaporList = response.body().getData();
                        myDialog.setContentView(R.layout.custom_popup);
                        iv_close    = myDialog.findViewById(R.id.btn_close);
                        rv_detail   = myDialog.findViewById(R.id.rv_detail_raport);
                        if (detailRaporModelList!=null) {
                            detailRaporModelList.clear();
                            for (JSONResponse.DataDetailRapor dataDetailRapor : detailRaporList) {
                                String nilai = dataDetailRapor.getScore_value();
                                detailRaporModel = new DetailRaporModel();
                                detailRaporModel.setNilai(nilai);
                                detailRaporModel.setKkm("-");
                                detailRaporModel.setRatarata("-");
                                detailRaporModelList.add(detailRaporModel);
                            }
                            detailRaporAdapter = new DetailRaporAdapter(detailRaporModelList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailRaport.this);
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            rv_detail.setLayoutManager(layoutManager);
                            rv_detail.setAdapter(detailRaporAdapter);
                            iv_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    myDialog.dismiss();
                                }
                            });
                            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            myDialog.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListDetailRapor> call, Throwable t) {
                Log.e("eror",t.toString());
                hideDialog();
            }
        });
    }
}
