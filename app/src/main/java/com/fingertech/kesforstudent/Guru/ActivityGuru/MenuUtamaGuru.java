package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterKegiatan;
import com.fingertech.kesforstudent.Guru.FragmentGuru.FragMenuGuruDua;
import com.fingertech.kesforstudent.Guru.FragmentGuru.FragMenuGuruSatu;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelKegiatan;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
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
    Toolbar toolbar;
    ImageView image_guru;
    String nama,nis,email,alamat,gender,tanggal,tempat,agama,no_hp,last_login,texttodolist,exam_type;
    ProgressDialog dialog;
    TextView tv_nama_guru;
    ViewPager viewPager;
    InkPageIndicator inkPageIndicator;
    public static int PAGE_COUNT = 2;
    FragmentAdapter fragmentAdapter;
    TextView tv_hint;
    RecyclerView rv_kegiatan;
    JSONArray absenlist,nilailist;
    JsonElement jsonElement;
    JSONObject statusobject,dataObject;
    ModelKegiatan modelKegiatan;
    List<ModelKegiatan> modelKegiatanList = new ArrayList<>();
    AdapterKegiatan adapterKegiatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu__utama__guru);
        toolbar     = findViewById(R.id.toolbarJadwalGuru);

        inkPageIndicator    = findViewById(R.id.indicators);
        viewPager           = findViewById(R.id.PagerUtama);
        image_guru          = findViewById(R.id.image_guru);
        fragmentAdapter     = new FragmentAdapter(getSupportFragmentManager());
        tv_nama_guru        = findViewById(R.id.tv_nama_profil_guru);
        tv_hint             = findViewById(R.id.hint_kegiatan);
        rv_kegiatan         = findViewById(R.id.rv_kegiatan);
        mApiInterface       = ApiClient.getClient().create(Auth.class);


        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN, "");
        memberid            = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username            = sharedpreferences.getString(TAG_EMAIL, "");
        fullname            = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type         = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        Base_anak           = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

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
                        last_login = response.body().getData().getLast_login();
                        tv_nama_guru.setText(nama);
                        if (picture.equals("")) {
                            Glide.with(MenuUtamaGuru.this).load(R.drawable.ic_logo).into(image_guru);
                        } else {
                            Glide.with(MenuUtamaGuru.this).load(Base_anak + picture).into(image_guru);
                        }
                        dapat_kegiatan();
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
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("authorization",authorization);
        bundle.putString("member_id",memberid);
        bundle.putString("school_code",school_code);
        bundle.putString("scyear_id",scyear_id);
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
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("authorization",authorization);
        bundle.putString("member_id",memberid);
        bundle.putString("school_code",school_code);
        bundle.putString("scyear_id",scyear_id);
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

    private void dapat_kegiatan(){
        progressBar();
        showDialog();
        Call<JsonElement> call = mApiInterface.kes_whattodolist_get(authorization,school_code.toLowerCase(),memberid,scyear_id);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.d("sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    jsonElement = response.body();
                    try {
                        statusobject    = new JSONObject(String.valueOf(jsonElement.getAsJsonObject()));
                        code            = statusobject.getString("code");
                        status          = statusobject.getInt("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        tv_hint.setVisibility(View.GONE);
                        rv_kegiatan.setVisibility(View.VISIBLE);
                        try {
                            dataObject  = statusobject.getJSONObject("data");
                            absenlist   = dataObject.getJSONArray("absents");
                            nilailist   = dataObject.getJSONArray("exam_scores");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (absenlist!=null) {
                            for (int i = 0; i < 1; i++) {
                                try {
                                    texttodolist = absenlist.getJSONObject(i).getString("absent_todo_text");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                modelKegiatan = new ModelKegiatan();
                                modelKegiatan.setExam_id(null);
                                modelKegiatan.setText(texttodolist);
                                modelKegiatanList.add(modelKegiatan);
                            }
                        }
                        if (nilailist!=null) {
                            for (int o = 0; o < 1; o++) {
                                try {
                                    texttodolist = nilailist.getJSONObject(o).getString("exam_todo_text");
                                    exam_type = nilailist.getJSONObject(o).getString("exam_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                modelKegiatan = new ModelKegiatan();
                                modelKegiatan.setText(texttodolist);
                                modelKegiatan.setExam_id(exam_type);
                                modelKegiatanList.add(modelKegiatan);
                            }
                        }
                        adapterKegiatan = new AdapterKegiatan(modelKegiatanList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtamaGuru.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_kegiatan.setLayoutManager(layoutManager);
                        rv_kegiatan.setAdapter(adapterKegiatan);
                        adapterKegiatan.setOnItemClickListener(new AdapterKegiatan.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (modelKegiatanList.get(position).getExam_id() == null){
                                    Intent intent = new Intent(MenuUtamaGuru.this,AbsenMurid.class);
                                    startActivity(intent);
                                }else{
                                    FancyToast.makeText(getApplicationContext(),"Harap untuk menambahkan nilai di website", Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                                }
                            }
                        });
                    }else if (status == 0 && code.equals("DTS_ERR_0001")){
                        tv_hint.setVisibility(View.VISIBLE);
                        rv_kegiatan.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("gagal",t.toString());
                hideDialog();
            }
        });
    }


}
