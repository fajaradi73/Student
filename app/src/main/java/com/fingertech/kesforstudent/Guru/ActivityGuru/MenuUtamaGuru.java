package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUtamaGuru extends AppCompatActivity {

    SharedPreferences sharedpreferences,sharedViewpager;
    String picture, Base_anak;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id;
    Auth mApiInterface;
    int status;
    String code;
    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";
    CardView btn_absensi,btn_penilaian,btn_silabus,btn_pesan,btn_jadwal,btn_kalendar;
    Toolbar toolbar;
    ImageView image_guru;
    String nama,nis,email,alamat,gender,tanggal,tempat,agama,no_hp,last_login;
    ProgressDialog dialog;
    TextView tv_nama_guru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu__utama__guru);
        toolbar     = findViewById(R.id.toolbarJadwalGuru);
        btn_absensi = findViewById(R.id.btn_absensi);
        btn_penilaian   = findViewById(R.id.btn_penilaian);
        btn_jadwal      = findViewById(R.id.btn_jadwal_mengajar);
        btn_silabus     = findViewById(R.id.btn_silabus);
        btn_pesan       = findViewById(R.id.btn_pesan);
        btn_kalendar    = findViewById(R.id.btn_kalender);
        image_guru      = findViewById(R.id.image_guru);
        tv_nama_guru    = findViewById(R.id.tv_nama_profil_guru);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");
        Base_anak               = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

        get_profile();
        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(MenuUtamaGuru.this,JadwalGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });
        image_guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(MenuUtamaGuru.this,ProfileGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });

        btn_silabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(MenuUtamaGuru.this,Silabus.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });
    }
    private void get_profile(){
        progressBar();
        showDialog();
        Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization.toString(),school_code.toLowerCase(),memberid.toString());
        call.enqueue(new Callback<JSONResponse.GetProfile>() {
            @Override
            public void onResponse(Call<JSONResponse.GetProfile> call, Response<JSONResponse.GetProfile> response) {
                Log.d("onRespone",response.code()+"");
                hideDialog();
                JSONResponse.GetProfile resource = response.body();
                status = resource.status;
                if (status == 1){
                    nama        = response.body().getData().getFullname();
                    nis         = response.body().getData().getMember_code();
                    email       = response.body().getData().getEmail();
                    alamat      = response.body().getData().getAddress();
                    gender      = response.body().getData().getGender();
                    tanggal     = response.body().getData().getBirth_date();
                    tempat      = response.body().getData().getBirth_place();
                    agama       = response.body().getData().getReligion();
                    picture     = response.body().getData().getPicture();
                    no_hp       = response.body().getData().getMobile_phone();
                    last_login  = response.body().getData().getLast_login();
                    tv_nama_guru.setText(nama);
                    if (picture.equals("")){
                        Glide.with(MenuUtamaGuru.this).load(R.drawable.ic_logo).into(image_guru);
                    }else {
                        Glide.with(MenuUtamaGuru.this).load(Base_anak + picture).into(image_guru);
                    }

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.GetProfile> call, Throwable t) {
                Log.d("onfailure",t.toString());
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
        dialog = new ProgressDialog(MenuUtamaGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}