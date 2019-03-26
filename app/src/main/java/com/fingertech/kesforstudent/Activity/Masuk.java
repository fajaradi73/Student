package com.fingertech.kesforstudent.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.kesforstudent.Activity.Menu_guru.Menu_Utama_Guru;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Util.JWTUtils;

import org.json.JSONObject;

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
    String username, memberid, fullname, member_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk);
        et_username     = findViewById(R.id.et_username);
        et_password     = findViewById(R.id.et_kata_sandi);
        btn_masuk       = findViewById(R.id.btn_Masuk);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        school_code     = getIntent().getStringExtra("school_code");
        school_name     = getIntent().getStringExtra("school_name");

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
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
            Toast.makeText(getApplicationContext(),"Email Tidak sesuai",Toast.LENGTH_LONG).show();
            requestFocus(et_username);
            return false;
        } else {
        }
        return true;
    }
    private boolean validatePassword() {
        if (et_password.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Masukan kata sandi anda",Toast.LENGTH_LONG).show();
            requestFocus(et_password);
            return false;
        }else if(et_password.length()<6) {
            Toast.makeText(getApplicationContext(),"Minimal 6 karakter",Toast.LENGTH_LONG).show();
            requestFocus(et_password);
            return false;
        } else {
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
        Call<JSONResponse> call = mApiInterface.login_post(school_code.toString(), et_username.getText().toString(), et_password.getText().toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("LP_SCS_0001")){
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

                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_EMAIL, username);
                        editor.putString(TAG_MEMBER_ID, memberid);
                        editor.putString(TAG_FULLNAME, fullname);
                        editor.putString(TAG_MEMBER_TYPE, member_type);
                        editor.putString(TAG_SCHOOL_CODE,school_code);
                        editor.putString(TAG_SCHOLL_NAME,school_name);
                        editor.putString(TAG_TOKEN, token);
                        editor.commit();
                        /// call session
                        if(member_type.toString().equals("4")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Masuk.this,R.style.DialogTheme);
                            builder.setTitle("Change Password");
                            builder.setMessage("Apakah anda ingin mengubah kata sandi anda?");
                            builder.setIcon(R.drawable.ic_alarm);
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Masuk.this, ChangePassword.class);
                                    intent.putExtra(TAG_EMAIL, username);
                                    intent.putExtra(TAG_MEMBER_ID, memberid);
                                    intent.putExtra(TAG_FULLNAME, fullname);
                                    intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                    intent.putExtra(TAG_SCHOOL_CODE,school_code);
                                    intent.putExtra(TAG_SCHOLL_NAME,school_name);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Masuk.this, MenuUtama.class);
                                    intent.putExtra(TAG_EMAIL, username);
                                    intent.putExtra(TAG_MEMBER_ID, memberid);
                                    intent.putExtra(TAG_FULLNAME, fullname);
                                    intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                    intent.putExtra(TAG_SCHOOL_CODE,school_code);
                                    intent.putExtra(TAG_SCHOLL_NAME,school_name);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        }else if (member_type.equals("3")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Masuk.this,R.style.DialogTheme);
                            builder.setTitle("Change Password");
                            builder.setMessage("Apakah anda ingin mengubah kata sandi anda?");
                            builder.setIcon(R.drawable.ic_alarm);
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Masuk.this, ChangePassword.class);
                                    intent.putExtra(TAG_EMAIL, username);
                                    intent.putExtra(TAG_MEMBER_ID, memberid);
                                    intent.putExtra(TAG_FULLNAME, fullname);
                                    intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                    intent.putExtra(TAG_SCHOOL_CODE,school_code);
                                    intent.putExtra(TAG_SCHOLL_NAME,school_name);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Masuk.this, Menu_Utama_Guru.class);
                                    intent.putExtra(TAG_EMAIL, username);
                                    intent.putExtra(TAG_MEMBER_ID, memberid);
                                    intent.putExtra(TAG_FULLNAME, fullname);
                                    intent.putExtra(TAG_MEMBER_TYPE, member_type);
                                    intent.putExtra(TAG_SCHOOL_CODE,school_code);
                                    intent.putExtra(TAG_SCHOLL_NAME,school_name);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

