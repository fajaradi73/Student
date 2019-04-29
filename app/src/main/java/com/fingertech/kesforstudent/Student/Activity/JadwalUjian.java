package com.fingertech.kesforstudent.Student.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Student.Adapter.UjianAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Student.Fragment.UjianFragment.UasFragment;
import com.fingertech.kesforstudent.Student.Fragment.UjianFragment.UnFragment;
import com.fingertech.kesforstudent.Student.Fragment.UjianFragment.UtsFragment;
import com.fingertech.kesforstudent.Student.Model.ItemUjian;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
//import com.rey.material.widget.Spinner;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalUjian extends AppCompatActivity {

    ProgressDialog dialog;

    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewpager;
    SharedPreferences sharedPreferences;
    String authorization,school_code,memberid,classroom_id,edulvelid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_ujian);
        mToolbar= findViewById(R.id.toolbar);
        mTablayout=findViewById(R.id.tablayout);
        mViewpager=findViewById(R.id.viewpager);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization", null);
        school_code         = sharedPreferences.getString("school_code", null);
        memberid            = sharedPreferences.getString("member_id", null);
        classroom_id        = sharedPreferences.getString("classroom_id", null);
        edulvelid           = sharedPreferences.getString("edulevelid",null);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        setTitle("Jadwal Ujian");
        mToolbar.setTitleTextColor(Color.WHITE);
        setupviewpager(mViewpager);
        mTablayout.setupWithViewPager(mViewpager);
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


    private void setupviewpager(ViewPager viewPager){

        viewpageradapter adapter = new viewpageradapter(getSupportFragmentManager());

        if (edulvelid.equals("9") || edulvelid.equals("12") || edulvelid.equals("15")){
            adapter.addFragment(new UtsFragment(),"UTS");
            adapter.addFragment(new UasFragment(),"UAS");
            adapter.addFragment(new UnFragment(),"UN");
        }else {
            adapter.addFragment(new UtsFragment(),"UTS");
            adapter.addFragment(new UasFragment(),"UAS");
        }
        viewPager.setAdapter(adapter);
    }

    class viewpageradapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragment = new ArrayList<>();
        private final List<String> mFragmentTittlelist = new ArrayList<>();


        public viewpageradapter(FragmentManager fm){
            super(fm);

        }

        @Override
        public Fragment getItem(int i) {
            return mFragment.get(i);
        }


        @Override
        public int getCount() {
            return mFragmentTittlelist.size();
        }

        public void addFragment (Fragment fragment,String title){
            mFragment.add(fragment);
            mFragmentTittlelist.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTittlelist.get(position);
        }
    }
}
