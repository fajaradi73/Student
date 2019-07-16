package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

public class TambahTugas extends AppCompatActivity {

    EditText et_tanggal,et_keterangan;
    Toolbar toolbar;
    Auth mApiInterface;
    Spinner sp_exam;
    Button btn_simpan;
    String authorization,school_code,member_id,scyear_id,edulevel_id,semester_id,type_id,exam_name,code;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    ProgressDialog dialog;
    int status;
    List<String> listExam              = new ArrayList<String>();
    private List<JSONResponse.DataExam> dataExamList;
    Long times_awal,times_akhir;
    String cources_id,cources_name;
    TextView tv_mapel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tambah_tugas);
        toolbar         = findViewById(R.id.toolbar_add_agenda);
        et_keterangan   = findViewById(R.id.et_keterangan);
        et_tanggal      = findViewById(R.id.et_tanggal);
        tv_mapel        = findViewById(R.id.tv_mapel);
        sp_exam         = findViewById(R.id.sp_mapel);
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
        times_awal          = sharedpreferences.getLong("times_awal",0);
        times_akhir         = sharedpreferences.getLong("times_akhir",0);
        cources_id          = sharedpreferences.getString("cources_id",null);
        cources_name        = sharedpreferences.getString("cources_name",null);

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        tv_mapel.setText(cources_name);
        et_keterangan.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        final DatePickerDialog mDatePicker;

        mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                et_tanggal.setText(convertDate(selectedyear, selectedmonth, selectedday));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMinDate(times_awal);
        mDatePicker.getDatePicker().setMaxDate(times_akhir);


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
        dapat_tugas();

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_tanggal.getText().toString().trim().isEmpty()){
                    FancyToast.makeText(getApplicationContext(),"Harap untuk menambah tanggal terlebih dahulu",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }else if (type_id == null){
                    FancyToast.makeText(getApplicationContext(),"Harap untuk memilih tipe ujian terlebih dahulu",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
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

    private void dapat_tugas(){
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
                        listExam.add("Pilih Tipe");
                        for (JSONResponse.DataExam dataExam : dataExamList){
                            exam_name = dataExam.getType_name();
                            listExam.add(exam_name);
                        }
                        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(
                                TambahTugas.this, R.layout.spinner_full, listExam) {
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
                        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_exam.setAdapter(adapterMapel);
                        sp_exam.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                if (position == 0){
                                    type_id = null;
                                }
                                else if (position > 0) {
                                    type_id = dataExamList.get(position - 1).getTypeid();
                                }
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
        dialog = new ProgressDialog(TambahTugas.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    private void add_agenda(){
        progressBar();
        showDialog();

    }
}

