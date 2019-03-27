package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Student.Activity.Masuk;
import com.fingertech.kesforstudent.Student.Activity.ProfileAnak;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileGuru extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    Toolbar toolbar;
    View actionEdit;
    Auth mApiInterface;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    String authorization,memberid,username,member_type,fullname,school_code;
    TextView tv_nis,tv_email,tv_no_hp,tv_alamat,tv_gender,tv_tanggal,tv_agama,tv_last_login;
    String nama,nis,email,alamat,gender,tanggal,tempat,agama,Base_anak,picture,no_hp,last_login;
    CardView btn_edit,btn_katasandi;
    Button btn_logout;
    FloatingActionButton fab_picture;
    ImageView imageView;
    ProgressDialog dialog;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_guru);
        toolbar                 = findViewById(R.id.toolbar_profile);
        collapsingToolbarLayout = findViewById(R.id.collapse_profile);
        appBarLayout            = findViewById(R.id.appbar_profile);
        tv_nis                  = findViewById(R.id.nig_guru);
        tv_email                = findViewById(R.id.email_guru);
        tv_no_hp                = findViewById(R.id.phone_guru);
        tv_alamat               = findViewById(R.id.alamat_guru);
        tv_gender               = findViewById(R.id.gender_guru);
        tv_tanggal              = findViewById(R.id.tanggal_lahir);
        tv_agama                = findViewById(R.id.agama_guru);
        tv_last_login           = findViewById(R.id.last_login);
        btn_edit                = findViewById(R.id.btn_edit);
        btn_katasandi           = findViewById(R.id.btn_katasandi);
        btn_logout              = findViewById(R.id.btn_logout);
        fab_picture             = findViewById(R.id.floatingActionButton);
        imageView               = findViewById(R.id.image_guru);
        mApiInterface           = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization     = sharedpreferences.getString(TAG_TOKEN,"");
        memberid          = sharedpreferences.getString(TAG_MEMBER_ID,"");
        username          = sharedpreferences.getString(TAG_EMAIL,"");
        fullname          = sharedpreferences.getString(TAG_FULLNAME,"");
        member_type       = sharedpreferences.getString(TAG_MEMBER_TYPE,"");
        school_code       = sharedpreferences.getString(TAG_SCHOOL_CODE,"");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        fab_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Tester",Snackbar.LENGTH_LONG).show();
            }
        });
        get_profile();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbarLayout.setTitle(NamaSekolah);
                    setTitle(nama);
                    isShow = true;
                } else if(isShow) {
//                    collapsingToolbarLayout.setTitle(NamaSekolah);//carefull there should a space between double quote otherwise it wont work
                    setTitle(nama);
                    isShow = false;
                }
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

                    setTitle(nama);
                    tv_nis.setText(nis);
                    tv_email.setText(email);
                    tv_alamat.setText(alamat);
                    tv_gender.setText(gender);
                    tv_tanggal.setText(converDate(tanggal));
                    tv_agama.setText(agama);
                    tv_no_hp.setText(no_hp);
                    tv_last_login.setText(last_login);
                    if (picture.equals("")){
                        Glide.with(ProfileGuru.this).load(R.drawable.ic_logo).into(imageView);
                    }else {
                        Glide.with(ProfileGuru.this).load(Base_anak + picture).into(imageView);
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
        dialog = new ProgressDialog(ProfileGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
