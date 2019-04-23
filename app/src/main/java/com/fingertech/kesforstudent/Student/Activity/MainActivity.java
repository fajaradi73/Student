package com.fingertech.kesforstudent.Student.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.speech.RecognizerIntent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fingertech.kesforstudent.Guru.ActivityGuru.MenuUtamaGuru;
import com.fingertech.kesforstudent.Student.Adapter.SearchAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Auth mApiInterface;
    SearchAdapter searchAdapter;
    String code;
    int status;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout linearLayout;
    private List<JSONResponse.SData> arraylist;
    String sekolah_kode,school_name,school_id;
    CardView btn_selanjutnya;
    ImageView logo;
    TextView footer,tv_sekolah;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    String email, memberid, fullname, member_type;
    FloatingSearchView floatingSearchView;
    LinearLayout layou_main;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView    = findViewById(R.id.rv_sekolah);
        btn_selanjutnya = findViewById(R.id.btn_selanjutnya);
        logo            = findViewById(R.id.logo);
        footer          = findViewById(R.id.footer);
        layou_main      = findViewById(R.id.layout_main);
        tv_sekolah      = findViewById(R.id.temukansekolah);
        floatingSearchView  = findViewById(R.id.floating_search_view);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        floatingSearchView.setSearchHint("Pilih Sekolah anda");

        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                Log.d("katakata",oldQuery+"/"+newQuery);
                if (newQuery.equals("")){
                    Log.d("query","sekolah harus diisi");
                }else {
                    search_school_post(newQuery);
                    recyclerView.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.GONE);
                    footer.setVisibility(View.GONE);
                    tv_sekolah.setVisibility(View.GONE);
                }
            }
        });

        floatingSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                search_school_post("");
                recyclerView.setVisibility(View.VISIBLE);
                logo.setVisibility(View.GONE);
                footer.setVisibility(View.GONE);
                tv_sekolah.setVisibility(View.GONE);
            }
        });

        btn_selanjutnya.setOnClickListener(v -> {
            if (sekolah_kode!=null) {
                Intent intent = new Intent(MainActivity.this, Masuk.class);
                intent.putExtra("school_code", sekolah_kode.toLowerCase());
                intent.putExtra("school_name", school_name);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(),"Harap Diisi terlebih dahulu sekolah anda",Toast.LENGTH_LONG).show();
                floatingSearchView.setSearchFocused(true);
            }
        });

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session     = sharedpreferences.getBoolean(session_status, false);
        email       = sharedpreferences.getString(TAG_EMAIL, null);
        memberid    = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname    = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type = sharedpreferences.getString(TAG_MEMBER_TYPE, null);

        if (session) {
            if (member_type.equals("3")){
                Intent intent = new Intent(MainActivity.this, MenuUtamaGuru.class);
                intent.putExtra(TAG_EMAIL, email);
                intent.putExtra(TAG_MEMBER_ID, memberid);
                intent.putExtra(TAG_FULLNAME, fullname);
                intent.putExtra(TAG_MEMBER_TYPE, member_type);
                finish();
                startActivity(intent);
            }else if (member_type.equals("4")){
                Intent intent = new Intent(MainActivity.this, MenuUtama.class);
                intent.putExtra(TAG_EMAIL, email);
                intent.putExtra(TAG_MEMBER_ID, memberid);
                intent.putExtra(TAG_FULLNAME, fullname);
                intent.putExtra(TAG_MEMBER_TYPE, member_type);
                finish();
                startActivity(intent);
            }
        }

        layou_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    floatingSearchView.clearSearchFocus();
                    recyclerView.setVisibility(View.GONE);
                    logo.setVisibility(View.VISIBLE);
                    footer.setVisibility(View.VISIBLE);
                    tv_sekolah.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard(this);
    }

    public void search_school_post(final String key){
        floatingSearchView.showProgress();
        Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key);
        postCall.enqueue(new Callback<JSONResponse.School>() {
            @Override
            public void onResponse(Call<JSONResponse.School> call, final Response<JSONResponse.School> response) {

                Log.d("TAG",response.code()+"");
                floatingSearchView.hideProgress();
                JSONResponse.School resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("SS_SCS_0001")) {
                    arraylist = response.body().getData();
                    searchAdapter = new SearchAdapter(arraylist, MainActivity.this);
                    recyclerView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();
                    searchAdapter.getFilter(key).filter(key);
                    searchAdapter.setOnItemClickListener((view, position) -> {
                        school_name         = arraylist.get(position).getSchool_name();
                        sekolah_kode        = arraylist.get(position).getSchool_code();
                        school_id           = arraylist.get(position).getSchool_id();
                        sekolah_kode        = sekolah_kode.toLowerCase();
                        floatingSearchView.setSearchText(school_name);
                        floatingSearchView.clearSearchFocus();
                        recyclerView.setVisibility(View.GONE);
                        logo.setVisibility(View.VISIBLE);
                        footer.setVisibility(View.VISIBLE);
                        tv_sekolah.setVisibility(View.VISIBLE);
                        hideKeyboard(MainActivity.this);
                    });

                } else {
                    if(status == 0 && code.equals("SS_ERR_0001")){

                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                floatingSearchView.hideProgress();
                Log.i("onFailure",t.toString());
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
