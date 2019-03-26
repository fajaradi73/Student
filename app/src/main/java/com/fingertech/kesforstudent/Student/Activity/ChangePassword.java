package com.fingertech.kesforstudent.Student.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    Auth mApiInterface;
    String code;
    int status;
    EditText et_password_lama,et_password_baru,et_konfirmasi;
    Button btn_ganti_password;
    TextView tv_lupa_password;
    ProgressDialog dialog;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    public static final String session_status = "session_status";
    public static final String my_change_shared = "my_change_shared";

    SharedPreferences sharedpreferences,changeshared;
    String authorization,memberid,username,member_type,fullname,school_code;
    TextInputLayout til_password_lama,til_password_baru,til_konfirmasi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        et_password_baru    = findViewById(R.id.et_kata_sandi_baru);
        et_password_lama    = findViewById(R.id.et_kata_sandi_lama);
        et_konfirmasi       = findViewById(R.id.et_konfirmasi_kata_sandi);
        btn_ganti_password  = findViewById(R.id.btn_ganti_sandi);
        til_password_lama   = findViewById(R.id.til_kata_sandi_lama);
        til_password_baru   = findViewById(R.id.til_kata_sandi_baru);
        til_konfirmasi      = findViewById(R.id.til_konfirmasi);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_change);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization     = sharedpreferences.getString(TAG_TOKEN,"");
        memberid          = sharedpreferences.getString(TAG_MEMBER_ID,"");
        username          = sharedpreferences.getString(TAG_EMAIL,"");
        fullname          = sharedpreferences.getString(TAG_FULLNAME,"");
        member_type       = sharedpreferences.getString(TAG_MEMBER_TYPE,"");
        school_code       = sharedpreferences.getString(TAG_SCHOOL_CODE,"");

        changeshared    = getSharedPreferences(my_change_shared,Context.MODE_PRIVATE);
        btn_ganti_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {

        if (!validateKataSandi()) {
            return;
        }
        if (!validateKataSandiBaru()) {
            return;
        }
        if (!validateUlangiKataSandi()) {
            return;
        }
        else {
            change_password();
        }
    }
    private boolean validateKataSandi() {
        if (et_password_lama.getText().toString().trim().isEmpty()) {
            til_password_lama.setError(getResources().getString(R.string.validate_pass));
            requestFocus(et_password_lama);
            return false;
        }else if(et_password_lama.length()<6) {
            til_password_lama.setError(getResources().getString(R.string.validate_pass_lengh));
            requestFocus(et_password_lama);
            return false;
        } else {
            til_password_lama.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateKataSandiBaru() {
        if (et_password_baru.getText().toString().trim().isEmpty()) {
            til_password_baru.setError(getResources().getString(R.string.validate_pass));
            requestFocus(et_password_baru);
            return false;
        }else if(et_password_baru.length()<6) {
            til_password_baru.setError(getResources().getString(R.string.validate_pass_lengh));
            requestFocus(et_password_baru);
            return false;
        } else {
            til_password_baru.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateUlangiKataSandi() {
        String pass = et_password_baru.getText().toString();
        String cpass = et_konfirmasi.getText().toString();
        if (!pass.equals(cpass)) {
            til_konfirmasi.setError(getResources().getString(R.string.validate_cpass));
            requestFocus(et_konfirmasi);
            return false;
        } else {
            til_konfirmasi.setErrorEnabled(false);
        }

        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void change_password(){
        progressBar();
        showDialog();
        Call<JSONResponse.ChangePassword> call = mApiInterface.kes_change_password_post(authorization.toString(),memberid.toString(),et_password_baru.getText().toString(),et_password_lama.getText().toString(),school_code.toLowerCase());

        call.enqueue(new Callback<JSONResponse.ChangePassword>() {

            @Override
            public void onResponse(Call<JSONResponse.ChangePassword> call, final Response<JSONResponse.ChangePassword> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.ChangePassword resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("CP_SCS_0001")) {
                    if (member_type.equals("4")){
                        Intent intent = new Intent(ChangePassword.this,MenuUtama.class);
                        startActivity(intent);
                    }else if (member_type.equals("3")){
                        Intent intent = new Intent(ChangePassword.this,MenuUtama.class);
                        startActivity(intent);
                    }
                } else{
                    if (status == 0 && code.equals("CP_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), "Kata sandi lama anda salah", Toast.LENGTH_LONG).show();
                        requestFocus(et_password_lama);
                    }else if (status == 0 && code.equals("CP_ERR_0004")){
                        requestFocus(et_password_baru);
                        Toast.makeText(getApplicationContext(), "Kata sandi tidak boleh sama dengan yang lain", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.ChangePassword> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
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

    //////// Progressbar - Loading Animation
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
        dialog = new ProgressDialog(ChangePassword.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
