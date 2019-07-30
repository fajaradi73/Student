package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterKegiatan;
import com.fingertech.kesforstudent.Guru.FragmentGuru.FragMenuGuruDua;
import com.fingertech.kesforstudent.Guru.FragmentGuru.FragMenuGuruSatu;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelKegiatan;
import com.fingertech.kesforstudent.MainActivity;
import com.fingertech.kesforstudent.NotifikasiActivity;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Service.FirebaseMessaging;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonElement;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUtamaGuru extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    String picture, Base_anak;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id,classroom;
    Auth mApiInterface;
    int status;
    String code;
    public static final String TAG_EMAIL                = "email";
    public static final String TAG_MEMBER_ID            = "member_id";
    public static final String TAG_FULLNAME             = "fullname";
    public static final String TAG_MEMBER_TYPE          = "member_type";
    public static final String TAG_TOKEN                = "token";
    public static final String TAG_SCHOOL_CODE          = "school_code";
    public static final String TAG_CLASS                = "classroom_id";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";
    ImageView image_guru;
    String nama,nis,email,alamat,gender,tanggal,tempat,agama,no_hp,last_login,texttodolist,exam_type;
    ProgressDialog dialog;
    TextView tv_nama_guru;
    ViewPager viewPager;
    InkPageIndicator inkPageIndicator;
    public static int PAGE_COUNT = 2;
    FragmentAdapter fragmentAdapter;
    String status_profile;
    Toolbar toolbar;


    int mNotifCount = 0;
    View actionView;
    TextView countmenu;
    private BroadcastReceiver statusReceiver;
    private IntentFilter mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu__utama__guru);
        inkPageIndicator    = findViewById(R.id.indicators);
        toolbar             = findViewById(R.id.toolbar_guru);
        viewPager           = findViewById(R.id.PagerUtama);
        image_guru          = findViewById(R.id.image_guru);
        fragmentAdapter     = new FragmentAdapter(getSupportFragmentManager());
        tv_nama_guru        = findViewById(R.id.tv_nama_profil_guru);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN, "");
        memberid            = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username            = sharedpreferences.getString(TAG_EMAIL, "");
        fullname            = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type         = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        classroom           = sharedpreferences.getString(TAG_CLASS,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        mNotifCount         = sharedpreferences.getInt("counting_guru",0);
        Base_anak           = ApiClient.BASE_IMAGE;

        viewPager.setAdapter(fragmentAdapter);
        inkPageIndicator.setViewPager(viewPager);
        get_profile();
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        send_data();
                        break;
                    case 1:
                        send_data2();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("coba", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d("firebase_Token", msg);
                });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1){
                status_profile = data.getStringExtra("status");
                if (status_profile != null){
                    get_profile();
                }
            }else if (requestCode == 1234){
                mNotifCount = 0;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("counting_guru",0);
                editor.apply();
                invalidateOptionsMenu();
                refresh();
            }
        }
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
                if (response.isSuccessful()) {
                    JSONResponse.GetProfile resource = response.body();
                    status = resource.status;
                    if (status == 1) {
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
                        classroom   = response.body().getData().getClassroom_id();
                        Log.d("class",classroom.toString());
                        tv_nama_guru.setText(nama);
                        if (picture.equals("")) {
                            Glide.with(MenuUtamaGuru.this).load(R.drawable.ic_logo).into(image_guru);
                        } else {
                            Glide.with(MenuUtamaGuru.this).load(Base_anak + picture).into(image_guru);
                        }
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

    @Override
    public void onBackPressed() {
        new FancyGifDialog.Builder(this)
                .setTitle("Keluar")
                .setMessage("Apakah anda ingin keluar dari aplikasi.")
                .setNegativeBtnText("Tidak")
                .setNegativeBtnBackground("#40bfe8")
                .setPositiveBtnBackground("#ff0000")
                .setPositiveBtnText("Ya")
                .setGifResource(R.drawable.home)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        }
                })
                .build();
    }

    private void send_data(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("authorization",authorization);
        editor.putString("member_id",memberid);
        editor.putString("school_code",school_code);
        editor.putString("scyear_id",scyear_id);
        editor.putString("classroom_id",classroom);
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("authorization",authorization);
        bundle.putString("member_id",memberid);
        bundle.putString("school_code",school_code);
        bundle.putString("scyear_id",scyear_id);
        bundle.putString("classroom_id",classroom);
        Fragment fragment = new FragMenuGuruSatu();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment1, fragment);
        fragmentTransaction.commitAllowingStateLoss();
        fragmentTransaction.addToBackStack(null);
        fragment.setArguments(bundle);
    }

    private void send_data2(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("authorization",authorization);
        editor.putString("member_id",memberid);
        editor.putString("school_code",school_code);
        editor.putString("scyear_id",scyear_id);
        editor.putString("classroom_id",classroom);
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("authorization",authorization);
        bundle.putString("member_id",memberid);
        bundle.putString("school_code",school_code);
        bundle.putString("scyear_id",scyear_id);
        bundle.putString("classroom_id",classroom);
        FragMenuGuruDua fragments = new FragMenuGuruDua();
        FragmentManager fragmentManagers = getSupportFragmentManager();
        FragmentTransaction fragmentTransactions = fragmentManagers.beginTransaction();
        fragmentTransactions.add(R.id.fragment6, fragments);
        fragmentTransactions.commitAllowingStateLoss();
        fragmentTransactions.addToBackStack(null);
        fragments.setArguments(bundle);
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    send_data();
                    return new FragMenuGuruSatu();
                case 1:
                    send_data2();
                    return new FragMenuGuruDua();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_keluar:
                pilihan();
                return true;
            case R.id.action_cart:
                mNotifCount = 0;
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("counting_guru",0);
                editor.apply();
                Intent intent = new Intent(MenuUtamaGuru.this, NotifikasiGuruActivity.class);
                intent.putExtra("counting_guru", mNotifCount);
                startActivityForResult(intent,1234);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guru, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        actionView  = menu.findItem(R.id.action_cart).getActionView();
        countmenu   = actionView.findViewById(R.id.cart_badge);
        if (mNotifCount == 0){
            countmenu.setVisibility(View.GONE);
        }else {
            countmenu.setVisibility(View.VISIBLE);
            countmenu.setText(String.valueOf(mNotifCount));
        }
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    private void pilihan() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuUtamaGuru.this,R.style.DialogTheme);
        builder.setTitle("Log out");
        builder.setMessage("Apakah anda ingin keluar?");
        builder.setIcon(R.drawable.ic_alarm);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void logout(){
        Call<JSONResponse.Logout> call = mApiInterface.kes_logout_post(authorization,school_code.toLowerCase(),memberid);
        call.enqueue(new Callback<JSONResponse.Logout>() {
            @Override
            public void onResponse(Call<JSONResponse.Logout> call, Response<JSONResponse.Logout> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")) {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(Masuk.session_status, false);
                            editor.putString(TAG_EMAIL, null);
                            editor.putString(TAG_MEMBER_ID, null);
                            editor.putString(TAG_FULLNAME, null);
                            editor.putString(TAG_MEMBER_TYPE, null);
                            editor.putString(TAG_TOKEN, null);
                            editor.apply();
                            Intent intent = new Intent(MenuUtamaGuru.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Logout> call, Throwable t) {
                Log.e("gagal",t.toString());
            }
        });
    }

    public void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String counting = intent.getStringExtra("counting_guru");
            if (counting != null){
                if (counting.equals("true")){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("counting_guru",mNotifCount);
                    editor.apply();
                    mNotifCount++;
                    invalidateOptionsMenu();
                }
            }
        }
    };

    @Override
    protected void onStart(){
        super.onStart();
        startService(new Intent(getBaseContext(), FirebaseMessaging.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(MenuUtamaGuru.this).registerReceiver(broadcastReceiver, new IntentFilter("NOW"));
    }

    @Override
    protected void onPause() {
        if(mIntent != null) {
            unregisterReceiver(statusReceiver);
            mIntent = null;
        }
        super.onPause();
    }

}
