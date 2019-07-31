package com.fingertech.kesforstudent;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Guru.ActivityGuru.MenuUtamaGuru;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Service.Position;
import com.fingertech.kesforstudent.Rest.PositionTable;
import com.fingertech.kesforstudent.Student.Activity.ForgotPassword;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.fingertech.kesforstudent.Util.JWTUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Masuk extends AppCompatActivity {

    String school_code,school_name;
    EditText et_username,et_password;
    Button btn_masuk;
    ProgressDialog dialog;
    Auth mApiInterface;
    int status;
    String code,token;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_SCHOLL_NAME  = "school_name";
    String username, memberid, fullname, member_type,scyear_id;
    TextInputLayout til_email,til_password;
    PositionTable positionTable = new PositionTable();
    Position position = new Position();
    ArrayList<HashMap<String, String>> row;
    TextView tv_lupa_sandi;
    String deviceid,firebase_token;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk);
        et_username     = findViewById(R.id.et_username);
        et_password     = findViewById(R.id.et_kata_sandi);
        btn_masuk       = findViewById(R.id.btn_Masuk);
        til_email       = findViewById(R.id.til_email);
        til_password    = findViewById(R.id.til_kata_sandi);
        tv_lupa_sandi   = findViewById(R.id.tvb_lupa_pass);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences   = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        school_code         = sharedpreferences.getString("school_code",null);
        school_name         = sharedpreferences.getString("school_name",null);
        row = positionTable.getAllData();

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Masuk.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(Masuk.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceID();
                submitForm();
            }
        });

        tv_lupa_sandi.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(intent);
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("coba", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    firebase_token = task.getResult().getToken();
                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, firebase_token);
                    Log.d("Token", msg);
                });

    }

    public void getDeviceID(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { return; }
        deviceid = tm.getDeviceId();
    }

    ///// check editext
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        login_post();
    }
    //////// validate Editext
    private boolean validateEmail() {
        String email = et_username.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            til_email.setError("* Username yang anda masukan salah");
            requestFocus(et_username);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            til_password.setError("* Masukkan kata sandi anda");
            requestFocus(et_password);
            return false;
        }else if(et_password.length()<6) {
            til_password.setError("* Minimal 6 karakter");
            requestFocus(et_password);
            return false;
        } else {
            til_password.setErrorEnabled(false);
        }
        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //////// Touchscreen disable focus keyobard on Editext
    @Override
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

    public void login_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> call = mApiInterface.login_post(school_code.toString(), et_username.getText().toString(), et_password.getText().toString(),firebase_token,device_id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("Login",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status  = resource.status;
                    code    = resource.code;

                    if (status == 1 && code.equals("LP_SCS_0001")) {
                        JSONResponse.Login_Data data = resource.login_data;
                        JSONObject jsonObject = null;
                        try {
                            token = data.token;
                            jsonObject = new JSONObject(JWTUtils.decoded(token));
                            /// save session
                            username    = String.valueOf(jsonObject.get("username"));
                            memberid    = String.valueOf(jsonObject.get("member_id"));
                            fullname    = String.valueOf(jsonObject.get("fullname"));
                            member_type = String.valueOf(jsonObject.get("member_type"));
                            scyear_id   = String.valueOf(jsonObject.get("scyear_id"));
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_EMAIL, username);
                            editor.putString(TAG_MEMBER_ID, memberid);
                            editor.putString(TAG_FULLNAME, fullname);
                            editor.putString(TAG_MEMBER_TYPE, member_type);
                            editor.putString(TAG_SCHOOL_CODE, school_code);
                            editor.putString(TAG_SCHOLL_NAME, school_name);
                            editor.putString(TAG_TOKEN, token);
                            editor.putString("scyear_id", scyear_id);
                            editor.apply();
                            /// call session
                            if (member_type.toString().equals("4")) {
                                Intent intent = new Intent(Masuk.this, MenuUtama.class);
                                intent.putExtra(TAG_EMAIL, username);
                                intent.putExtra(TAG_MEMBER_ID, memberid);
                                intent.putExtra(TAG_FULLNAME, fullname);
                                intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                intent.putExtra(TAG_SCHOOL_CODE, school_code);
                                intent.putExtra(TAG_SCHOLL_NAME, school_name);
                                intent.putExtra(TAG_TOKEN, token);
                                intent.putExtra("scyear_id", scyear_id);
                                startActivity(intent);
                                finish();


                            } else if (member_type.equals("3")) {
                                Intent intent = new Intent(Masuk.this, MenuUtamaGuru.class);
                                intent.putExtra(TAG_EMAIL, username);
                                intent.putExtra(TAG_MEMBER_ID, memberid);
                                intent.putExtra(TAG_FULLNAME, fullname);
                                intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                intent.putExtra(TAG_SCHOOL_CODE, school_code);
                                intent.putExtra(TAG_SCHOLL_NAME, school_name);
                                intent.putExtra(TAG_TOKEN, token);
                                intent.putExtra("scyear_id", scyear_id);
                                startActivity(intent);
                                finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else if (status == 0 && code.equals("LP_ERR_0004")){
                        FancyToast.makeText(Masuk.this,"Sekolah tidak ditemukan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                        finish();
                    }else if (status == 0 && code.equals("LP_ERR_0006")){
                        til_email.setError("* Username yang anda masukan salah");
                        requestFocus(et_username);
                    }else if (status == 0 && code.equals("LP_ERR_0005")){
                        til_password.setError("* Kata sandi yang ada masukkan salah");
                        requestFocus(et_password);
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Log.i("onFailure",t.toString());
            }
        });
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
        dialog = new ProgressDialog(Masuk.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}

