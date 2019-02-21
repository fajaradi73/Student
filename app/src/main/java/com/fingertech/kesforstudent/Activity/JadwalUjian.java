package com.fingertech.kesforstudent.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Adapter.UjianAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Model.ItemUjian;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
//import com.rey.material.widget.Spinner;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalUjian extends AppCompatActivity {

    List<ItemUjian> itemUjianList = new ArrayList<>();
    ItemUjian itemUjian;
    UjianAdapter ujianAdapter;
    Auth mApiInterface;
    String authorization,memberid,school_code,classroom_id;
    RecyclerView rv_ujian;
    Toolbar toolbar;
    ProgressDialog dialog;
    int status;
    String code;
    String jam,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
    TextView no_ujian;
    String date,semester_nama;
    TextView tv_semester,tv_start,tv_end,tv_filter,tv_semesters,tv_reset,tv_slide;
    EditText et_kata_kunci;
    LinearLayout ll_slide;
    com.rey.material.widget.Spinner sp_type;
    private List<JSONResponse.DataSemester> dataSemesters;
    private List<JSONResponse.DataMapel> dataMapelList;
    Spinner sp_mapel;
    private String[] tipe = {
            "FINAL",
            "MID",
            "UN"
    };
    Button btn_cari;
    ImageView btn_down;
    View view;
    String mata_pelajaran,type_pelajaran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ujian_sheet);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        rv_ujian        = findViewById(R.id.recycleview_ujian);
        toolbar         = findViewById(R.id.toolbar_ujian);
        no_ujian        = findViewById(R.id.tv_no_ujian);
        tv_semester     = findViewById(R.id.tv_semester);
        tv_start        = findViewById(R.id.tv_start);
        tv_end          = findViewById(R.id.tv_end);
        tv_filter       = findViewById(R.id.tv_filter);
        et_kata_kunci   = findViewById(R.id.et_kata_kunci);
        ll_slide        = findViewById(R.id.slide_down);

        authorization   = getIntent().getStringExtra("authorization");
        memberid        = getIntent().getStringExtra("member_id");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        Check_Semester();

        et_kata_kunci.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ujianAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });
    }

    public void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.filter_sheet, null);
        LinearLayout ll_slide   = view.findViewById(R.id.slide_down);
        tv_semesters     = view.findViewById(R.id.tv_semesters);
        sp_mapel        = view.findViewById(R.id.sp_mapel);
        sp_type         = view.findViewById(R.id.sp_tipe);
        btn_cari        = view.findViewById(R.id.btn_cari);
        tv_reset        = view.findViewById(R.id.reset);
        tv_slide        = view.findViewById(R.id.name);
        btn_down        = view.findViewById(R.id.arrow_down);

        final Dialog mBottomSheetDialog = new Dialog(JadwalUjian.this,
                R.style.MaterialDialogSheet);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        tv_semesters.setText(semester);

        List<String> listMapel = new ArrayList<>();
        for (int m = 0;m < dataMapelList.size();m++){
            listMapel.add(dataMapelList.get(m).getCources_name());
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_mapel);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
            Log.d("eror",e.getMessage());
        }
        final  ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(JadwalUjian.this,R.layout.spinner_full,listMapel);
        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_mapel.setAdapter(adapterMapel);
        sp_mapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mata_pelajaran = listMapel.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final List<String> listtype = new ArrayList<>(Arrays.asList(tipe));
        final  ArrayAdapter<String> adapterTipe = new ArrayAdapter<String>(JadwalUjian.this,R.layout.spinner_full,listtype);
        adapterTipe.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_type.setAdapter(adapterTipe);
        sp_type.setOnItemClickListener((parent, view1, position, id) -> {
            type_pelajaran = listtype.get(position).toString();
            return true;
        });
        type_pelajaran = sp_type.getSelectedItem().toString();

        btn_down.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        tv_slide.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        tv_reset.setOnClickListener(v -> {
            Jadwal_ujian();
            mBottomSheetDialog.dismiss();
        });
        btn_cari.setOnClickListener(v -> {
            ujianAdapter.getfilter(mata_pelajaran.toLowerCase()).filter(type_pelajaran.toLowerCase());
            mBottomSheetDialog.dismiss();
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
                Jadwal_ujian();
                dapat_mapel();
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.ListSemester resource = response.body();

                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    for (int i = 0;i < response.body().getData().size();i++){

                        if (response.body().getData().get(i).getSemester_id().equals(semester_id)){
                            semester    = response.body().getData().get(i).getSemester_name();
                            start_date  = response.body().getData().get(i).getStart_date();
                            end_date    = response.body().getData().get(i).getEnd_date();
                            start_year  = response.body().getData().get(0).getStart_date();
                            start_end   = response.body().getData().get(1).getEnd_date();
                            tv_semester.setText("Semester "+semester+" ("+converTahun(start_year)+"/"+converTahun(start_end)+")");
                            tv_start.setText(converDate(start_date));
                            tv_end.setText(converDate(end_date));

                        }
                    }

                    dataSemesters = response.body().getData();

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void dapat_mapel(){
        Call<JSONResponse.ListMapel> call = mApiInterface.kes_list_cources_get(authorization.toString(),school_code.toLowerCase(),classroom_id);
        call.enqueue(new Callback<JSONResponse.ListMapel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapel> call, Response<JSONResponse.ListMapel> response) {
                Log.d("onResponse",response.code()+"");
                JSONResponse.ListMapel resource = response.body();

                status  = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("KLC_SCS_0001")){
                    dataMapelList = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapel> call, Throwable t) {

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

    private void Jadwal_ujian(){
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(),school_code.toString().toLowerCase(),memberid.toString(),classroom_id.toString(),semester_id.toString());

        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {

            @Override
            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.JadwalUjian resource = response.body();

                status = resource.status;
                code    = resource.code;

                ItemUjian itemUjian= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            jam         = response.body().getData().get(i).getExam_time_ok();
                            tanggal     = response.body().getData().get(i).getExam_date_ok();
                            mapel       = response.body().getData().get(i).getCources_name();
                            type        = response.body().getData().get(i).getType_name();
                            deskripsi   = response.body().getData().get(i).getExam_desc();
                            nilai       = response.body().getData().get(i).getScore_value();
                            itemUjian = new ItemUjian();
                            itemUjian.setJam(jam);
                            itemUjian.setTanggal(tanggal);
                            itemUjian.setMapel(mapel);
                            itemUjian.setType_id(type);
                            itemUjian.setDeskripsi(deskripsi);
                            itemUjian.setNilai(nilai);
                            itemUjianList.add(itemUjian);
                        }
                        no_ujian.setVisibility(View.GONE);
                        ujianAdapter = new UjianAdapter(itemUjianList,JadwalUjian.this);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalUjian.this);
                        rv_ujian.setLayoutManager(layoutManager);
                        rv_ujian.setAdapter(ujianAdapter);
                    }else {
                        no_ujian.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
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
        dialog = new ProgressDialog(JadwalUjian.this);
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

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
