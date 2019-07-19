package com.fingertech.kesforstudent.Student.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.CustomView.viewpageradapter;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Student.Fragment.LessonFragment.KegiatanFragment;
import com.fingertech.kesforstudent.Student.Fragment.LessonFragment.RiwayatFragment;
import com.google.android.material.tabs.TabLayout;

import static com.fingertech.kesforstudent.Student.Activity.JadwalPelajaran.my_lesson_preferences;

public class LessonReview extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String authorization,school_code,classroom_id,student_id,cources_id;
    Auth mApiInterface;
    Toolbar toolbar;
    int status;
    String code;
    private TabLayout mTablayout;
    private ViewPager mViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_review_murid);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        toolbar             = findViewById(R.id.toolbar);
        mTablayout          = findViewById(R.id.tablayout);
        mViewpager          = findViewById(R.id.viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences   = getSharedPreferences(my_lesson_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        cources_id          = sharedPreferences.getString("cources_id",null);
        setupviewpager(mViewpager);
        mTablayout.setupWithViewPager(mViewpager);
    }

    private void setupviewpager(ViewPager viewPager){

        viewpageradapter adapter = new viewpageradapter(getSupportFragmentManager());
        adapter.addFragment(new RiwayatFragment(),"Riwayat Pelajaran");
        adapter.addFragment(new KegiatanFragment(),"Kegiatan Terakhir");
        viewPager.setAdapter(adapter);
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
}
