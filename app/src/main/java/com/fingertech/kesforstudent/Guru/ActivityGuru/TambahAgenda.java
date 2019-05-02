package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahAgenda extends AppCompatActivity {

    EditText et_tanggal,et_keterangan;
    Toolbar toolbar;
    Auth mApiInterface;
    Spinner sp_mapel;
    Button btn_simpan;
    String authorization,school_code,member_id,scyear_id,edulevel_id,semester_id,cources_id,exam_name,code;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    ProgressDialog dialog;
    int status;
    String cources_name;
    List<String> listMapel              = new ArrayList<String>();
    private List<JSONResponse.DataMapelEdu> dataMapelEduList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_agenda);
        toolbar         = findViewById(R.id.toolbar_add_agenda);
        et_keterangan   = findViewById(R.id.et_keterangan);
        et_tanggal      = findViewById(R.id.et_tanggal);
        sp_mapel        = findViewById(R.id.sp_mapel);
        btn_simpan      = findViewById(R.id.btn_simpan);
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

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker;

        mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                et_tanggal.setText(convertDate(selectedyear, selectedmonth, selectedday));
            }
        }, mYear, mMonth, mDay);


        et_tanggal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mDatePicker.show();
            }
        });

        et_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mDatePicker.show();
                }
            }
        });
        dapat_mapel();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_tanggal.getText().toString().trim().isEmpty()){
                    FancyToast.makeText(getApplicationContext(),"Harap untuk menambah tanggal terlebih dahulu",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }else if (cources_id == null){
                    FancyToast.makeText(getApplicationContext(),"Harap untuk memilih pelajaran terlebih dahulu",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }else if (et_keterangan.getText().toString().isEmpty()){
                    FancyToast.makeText(getApplicationContext(),"Harap untuk menambah deskripsi terlebih dahulu terlebih dahulu",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }else {
                    add_agenda();
                }
            }
        });
    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
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


    private void dapat_mapel(){
        Call<JSONResponse.ListMapelEdu> call = mApiInterface.kes_get_edulevel_cources_get(authorization,school_code.toLowerCase(),member_id,edulevel_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListMapelEdu>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapelEdu> call, Response<JSONResponse.ListMapelEdu> response) {
                Log.d("MataPelajaran",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListMapelEdu resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataMapelEduList = response.body().getData();
                        if (listMapel != null) {
                            listMapel.clear();
                            listMapel.add("Pilih Mata Pelajaran");
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                cources_name = response.body().getData().get(i).getCources_name();
                                listMapel.add(cources_name);
                                final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(TambahAgenda.this, R.layout.spinner_full, listMapel);
                                adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                sp_mapel.setAdapter(adapterMapel);
                                sp_mapel.setOnItemSelectedListener((parent, view, position, id) -> {
                                    if (position == 0) {
                                        cources_id = null;
                                    } else {
                                        cources_id = dataMapelEduList.get(position - 1).getCourcesid();
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapelEdu> call, Throwable t) {
                Log.d("GagalMapel",t.toString());
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
        dialog = new ProgressDialog(TambahAgenda.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    private void add_agenda(){
        progressBar();
        showDialog();
        Call<JSONResponse.AddAgenda> call = mApiInterface.kes_add_agenda_post(authorization,school_code.toLowerCase(),member_id,edulevel_id,convertTanggal(et_tanggal.getText().toString()),cources_id,et_keterangan.getText().toString(),scyear_id);
        call.enqueue(new Callback<JSONResponse.AddAgenda>() {
            @Override
            public void onResponse(Call<JSONResponse.AddAgenda> call, Response<JSONResponse.AddAgenda> response) {
                Log.d("tambahSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.AddAgenda resource = response.body();
                    status = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        Intent intent = new Intent(TambahAgenda.this,AgendaDetail.class);
                        setResult(RESULT_OK,intent);
                        FancyToast.makeText(getApplicationContext(),"Sukses Menyimpan", Toast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        finish();
                    }else {
                        FancyToast.makeText(getApplicationContext(),"Gagal Menyimpan", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.AddAgenda> call, Throwable t) {
                hideDialog();
                Log.e("eror",t.toString());
            }
        });
    }
}
