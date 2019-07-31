package com.fingertech.kesforstudent.Student.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    String nama,nis,email,alamat,gender,tanggal,tempat,agama,nohp,picture;
    Toolbar toolbar;
    TextInputLayout til_nama,til_email,til_tanggal_lahir,til_tempat_lahir,til_no_hp;
    EditText et_nama,et_email,et_tanggal_lahir,et_tempat_lahir,et_no_hp;
    RadioButton rb_pria,rb_wanita;
    Spinner sp_religion;
    private String[] religion = {
            "Pilih Agama...",
            "Islam",
            "Kristen",
            "Katolik",
            "Budhaa",
            "Hindu"
    };
    Button btn_update;
    ProgressDialog dialog;
    int status;
    String code;
    Auth mApiInterface;
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    SharedPreferences sharedpreferences;
    String authorization,memberid,last_update,member_type,fullname,school_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        toolbar             = findViewById(R.id.toolbarprofil);
        til_nama            = findViewById(R.id.til_nama_profile);
        til_email           = findViewById(R.id.til_email_profile);
        til_tanggal_lahir   = findViewById(R.id.til_tanggal);
        til_no_hp           = findViewById(R.id.til_mobile_phone);
        et_nama             = findViewById(R.id.et_nama_profile);
        et_email            = findViewById(R.id.et_email_profile);
        et_tanggal_lahir    = findViewById(R.id.et_tanggal);
        et_no_hp            = findViewById(R.id.et_mobile_phone);
        rb_pria             = findViewById(R.id.rb_pria);
        rb_wanita           = findViewById(R.id.rb_wanita);
        sp_religion         = findViewById(R.id.sp_religion);
        btn_update          = findViewById(R.id.btn_update);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization     = sharedpreferences.getString(TAG_TOKEN,"");
        memberid          = sharedpreferences.getString(TAG_MEMBER_ID,"");
        school_code       = sharedpreferences.getString(TAG_SCHOOL_CODE,"");

        nama    = getIntent().getStringExtra("nama");
        nis     = getIntent().getStringExtra("nis");
        email   = getIntent().getStringExtra("email");
        gender  = getIntent().getStringExtra("gender");
        tanggal = getIntent().getStringExtra("tanggal_lahir");
        agama   = getIntent().getStringExtra("agama");
        nohp    = getIntent().getStringExtra("nohp");

        et_nama.setText(nama);
        et_email.setText(email);
        et_tanggal_lahir.setText(tanggal);
        et_no_hp.setText(nohp);
        if (gender.equals("Pria")){
            rb_pria.setChecked(true);
            rb_wanita.setChecked(false);
        }else if (gender.equals("Wanita")){
            rb_wanita.setChecked(true);
            rb_pria.setChecked(false);
        }

        rb_pria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Pria";
            }
        });
        rb_wanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "Wanita";
            }
        });

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker;

        mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                et_tanggal_lahir.setText(convertDate(selectedyear, selectedmonth, selectedday));
            }
        }, mYear, mMonth, mDay);
        mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
        mDatePicker.updateDate(Integer.parseInt(convertTahun(tanggal)),Integer.parseInt(convertBulan(tanggal))-1,Integer.parseInt(convertDate(tanggal)));


        et_tanggal_lahir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mDatePicker.show();
            }
        });

        et_tanggal_lahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mDatePicker.show();
                }
            }
        });


        final List<String> penghasil = new ArrayList<>(Arrays.asList(religion));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_full,penghasil){
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
                                        @NotNull ViewGroup parent) {
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
        int position    = ArrayAdapter.getPosition(agama);
        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_religion.setAdapter(ArrayAdapter);
        sp_religion.setSelection(position);
        sp_religion.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (position > 0) {
                    agama = penghasil.get(position);
                }
            }
        });
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        last_update = df.format(Calendar.getInstance().getTime());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
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
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void submitForm() {
        if (!validateNamaLengkap()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateTanggalLahir()){
            return;
        }
        if (!validateNumberPhone()) {
            return;
        }
        else {
            update_profile();
        }
    }

    private boolean validateNamaLengkap() {
        if (et_nama.getText().toString().trim().isEmpty()) {
            til_nama.setError(getResources().getString(R.string.validate_fullname));
            requestFocus(et_nama);
            return false;
        } else {
            til_nama.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateTanggalLahir() {
        if (et_tanggal_lahir.getText().toString().trim().isEmpty()) {
            til_tanggal_lahir.setError(getResources().getString(R.string.validate_tanggal_lahir));
            requestFocus(et_tanggal_lahir);
            return false;
        } else {
            til_tanggal_lahir.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            til_email.setError(getResources().getString(R.string.validate_email));
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateNumberPhone() {
        if (et_no_hp.getText().toString().trim().isEmpty()) {
            til_no_hp.setError(getResources().getString(R.string.validate_mobile_phone));
            requestFocus(et_no_hp);
            return false;
        }else if(et_no_hp.length()<10) {
            til_no_hp.setError(getResources().getString(R.string.validate_number_lengh));
            requestFocus(et_no_hp);
            return false;
        } else {
            til_no_hp.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateAlamat(){
        if (et_tanggal_lahir.getText().toString().trim().isEmpty()) {
            til_tanggal_lahir.setError(getResources().getString(R.string.validate_alamat));
            requestFocus(et_tanggal_lahir);
            return false;
        } else {
            til_tanggal_lahir.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void update_profile(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_update_put(authorization.toString(),memberid.toString(),school_code.toLowerCase(),et_nama.getText().toString(),et_no_hp.getText().toString(),last_update.toString(),gender.toString(),sp_religion.getSelectedItem().toString(),converttanggal(et_tanggal_lahir.getText().toString()));

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                Log.i("KES", response.code() + "");
                hideDialog();
                JSONResponse resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("UP_SCS_0001")) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("status","1");
                    editor.apply();
                    Intent intent = new Intent(EditProfile.this, ProfileAnak.class);
                    intent.putExtra("authorization",authorization);
                    intent.putExtra("school_code",school_code);
                    intent.putExtra("member_id",memberid);
                    intent.putExtra("status","1");
                    setResult(RESULT_OK, intent);
                    finish();
                    FancyToast.makeText(getApplicationContext(),"Berhasil Update",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                } else{
                    if (status == 0 && code.equals("UP_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }else if (status == 0 & code.equals("UP_ERR_0002")){
                        Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }else if (status == 0 & code.equals("UP_ERR_0003")){
                        Toast.makeText(getApplicationContext(), "Nomor HP tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse> call, Throwable t) {
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
        dialog = new ProgressDialog(EditProfile.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }


    String convertDate(String date) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(date));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertBulan(String date) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(date));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTahun(String date) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(date));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converttanggal(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat .parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
