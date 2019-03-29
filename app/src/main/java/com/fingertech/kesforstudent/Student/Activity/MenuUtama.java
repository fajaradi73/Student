package com.fingertech.kesforstudent.Student.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.JumatAdapter;
import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.KamisAdapter;
import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.RabuAdapter;
import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.SabtuAdapter;
import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.SelasaAdapter;
import com.fingertech.kesforstudent.Student.Adapter.HariAdapter.SeninAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Student.Fragment.MenuDuaFragment;
import com.fingertech.kesforstudent.Student.Fragment.MenuSatuFragment;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalJumat;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalKamis;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalRabu;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalSabtu;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalSelasa;
import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalSenin;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.CustomView.SnappyRecycleView;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ViewPager ParentPager;
    private FragmentAdapter fragmentAdapter;
    public static int PAGE_COUNT = 2;
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    InkPageIndicator inkPageIndicator;
    View header;
    TextView tv_profile,title_jadwal;
    CircleImageView image_profile;
    ProgressDialog dialog;

    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";

    private Date dates,date_now;
    private SimpleDateFormat jamformat  = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    SharedPreferences sharedpreferences,sharedViewpager;
    String picture, Base_anak;
    String authorization, memberid, username, member_type, fullname, school_code;
    Auth mApiInterface;
    int status;
    String code;

    private List<JadwalSenin> itemlist;
    private List<JadwalSelasa> itemselasa;
    private List<JadwalRabu> itemRabu;
    private List<JadwalKamis> itemKamis;
    private List<JadwalJumat> itemJumat;
    private List<JadwalSabtu> itemSabtu;
    SeninAdapter seninAdapter;
    SelasaAdapter selasaAdapter;
    RabuAdapter rabuAdapter;
    KamisAdapter kamisAdapter;
    JumatAdapter jumatAdapter;
    SabtuAdapter sabtuAdapter;
    private Boolean clicked = false;

    String classroom_id;
    String days_name;
    String mapel;
    int lamber;
    String jamber;
    String jam_mulai;
    String jam_selesai;
    String guru, daysid, day_type, day_status;
    String date, day,scyear_id,time;
    SnappyRecycleView rv_senin, rv_selasa, rv_rabu, rv_kamis, rv_jumat, rv_sabtu;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        toolbar             = findViewById(R.id.toolbar);
        navigationView      = findViewById(R.id.nav_view);
//        fab                 = findViewById(R.id.fab);
        drawer              = findViewById(R.id.drawer_layout);
        ParentPager         = findViewById(R.id.PagerUtama);
        fragmentAdapter     = new FragmentAdapter(getSupportFragmentManager());
        inkPageIndicator    = findViewById(R.id.indicators);
        header              = navigationView.getHeaderView(0);
        tv_profile          = header.findViewById(R.id.tv_profil);
        image_profile       = header.findViewById(R.id.image_profile);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        rv_senin            = findViewById(R.id.rv_senin);
        rv_selasa           = findViewById(R.id.rv_selasa);
        rv_rabu             = findViewById(R.id.rv_rabu);
        rv_kamis            = findViewById(R.id.rv_kamis);
        rv_jumat            = findViewById(R.id.rv_jumat);
        rv_sabtu            = findViewById(R.id.rv_sabtu);
        coordinatorLayout   = findViewById(R.id.menu_utama);
        title_jadwal        = findViewById(R.id.title_jadwal);
        Base_anak           = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");
        sharedViewpager = getSharedPreferences(my_viewpager_preferences,Context.MODE_PRIVATE);

        ParentPager.setAdapter(fragmentAdapter);
        inkPageIndicator.setViewPager(ParentPager);

//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).show());

//        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fabseed);
//        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
//            @Override
//            public boolean onMenuItemSelected(MenuItem menuItem) {
//                //TODO: Start some activity
//                return false;
//            }
//        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        image_profile.setOnClickListener(v -> {
            Intent intent = new Intent(MenuUtama.this, ProfileAnak.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(this);
        get_profile();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy  HH:mm:ss", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());


        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date dater = null;
        try {
            dater = inFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("in","ID"));
        df.setTimeZone(TimeZone.getDefault());
        day = outFormat.format(dater);

        itemlist = new ArrayList<JadwalSenin>();
        itemselasa = new ArrayList<JadwalSelasa>();
        itemRabu = new ArrayList<JadwalRabu>();
        itemKamis = new ArrayList<JadwalKamis>();
        itemJumat = new ArrayList<JadwalJumat>();
        itemSabtu = new ArrayList<JadwalSabtu>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_beranda) {
        } else if (id == R.id.nav_user) {
            Intent intent = new Intent(MenuUtama.this, ProfileAnak.class);
            startActivity(intent);
        } else if (id == R.id.nav_tentang) {

        }
//        else if (id == R.id.nav_pengaturan){
//            Intent intent = new Intent(MenuUtama.this, Setting_Activity.class);
//            startActivity(intent);
//        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    send_data();
                    return new MenuSatuFragment();
                case 1:
                    send_data2();
                    return new MenuDuaFragment();
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

    public void get_profile() {
        progressBar();
        showDialog();
        Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization, school_code.toLowerCase(), memberid);
        call.enqueue(new Callback<JSONResponse.GetProfile>() {
            @Override
            public void onResponse(Call<JSONResponse.GetProfile> call, Response<JSONResponse.GetProfile> response) {
                Log.d("onResponse", response.code() + "");
                hideDialog();
                JSONResponse.GetProfile resource = response.body();

                status = resource.status;
                if (status == 1) {
                    fullname = response.body().getData().getFullname();
                    picture = response.body().getData().getPicture();
                    classroom_id = response.body().getData().getClassroom_id();
                    tv_profile.setText(fullname);
                    String imageFile = Base_anak + picture;
                    Picasso.with(MenuUtama.this).load(imageFile).into(image_profile);
                    Jadwal_pelajaran();
                    send_data();
                    send_data2();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.GetProfile> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
            }
        });
    }

    public void send_data(){
        Bundle bundle = new Bundle();
        if (bundle != null) {
            SharedPreferences.Editor editor = sharedViewpager.edit();
            editor.putString("member_id",memberid);
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("fullname",fullname);
            editor.apply();
            bundle.putString("member_id", memberid);
            bundle.putString("school_code", school_code);
            bundle.putString("authorization", authorization);
            bundle.putString("classroom_id", classroom_id);
            bundle.putString("fullname",fullname);
            MenuSatuFragment menuSatuFragment = new MenuSatuFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragMenuSatu, menuSatuFragment);
            fragmentTransaction.commitAllowingStateLoss();
            menuSatuFragment.setArguments(bundle);
        }else {
            Toast.makeText(MenuUtama.this,"harap refresh kembali",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public void send_data2(){
        Bundle bundle = new Bundle();
        if (bundle != null) {
            SharedPreferences.Editor editor = sharedViewpager.edit();
            editor.putString("member_id",memberid);
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("fullname",fullname);
            editor.apply();
            bundle.putString("member_id", memberid);
            bundle.putString("school_code", school_code);
            bundle.putString("authorization", authorization);
            bundle.putString("classroom_id", classroom_id);
            bundle.putString("fullname",fullname);
            MenuDuaFragment menuSatuFragment = new MenuDuaFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragMenuDua, menuSatuFragment);
            fragmentTransaction.commitAllowingStateLoss();
            menuSatuFragment.setArguments(bundle);
        }else {
            Toast.makeText(MenuUtama.this,"harap refresh kembali",Toast.LENGTH_LONG).show();
        }
    }
    private void Jadwal_pelajaran() {

            Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization, school_code.toLowerCase(), memberid, classroom_id);

            call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {

                @Override
                public void onResponse(Call<JSONResponse.JadwalPelajaran> call, final Response<JSONResponse.JadwalPelajaran> response) {
                    Log.i("KES", response.code() + "");

                    JSONResponse.JadwalPelajaran resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    JadwalSenin jadwalSenin = null;
                    JadwalSelasa jadwalSelasa = null;
                    JadwalRabu jadwalRabu = null;
                    JadwalKamis jadwalKamis = null;
                    JadwalJumat jadwalJumat = null;
                    JadwalSabtu jadwalSabtu = null;
                    if (status == 1 && code.equals("CSCH_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().getClass_schedule().size(); i++) {
                            JSONResponse.JadwalData jadwalData = resource.data.getClass_schedule().get(i);
                            days_name = jadwalData.getDayName();
                            day_status = jadwalData.getDayStatus();
                            daysid = jadwalData.getDayid();
                            day_type = jadwalData.getDayType();
                            switch (days_name) {
                                case "Senin": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();

                                        jadwalSenin = new JadwalSenin();
                                        jadwalSenin.setDay_name(days_name);
                                        jadwalSenin.setFullname(guru);
                                        jadwalSenin.setCources_name(mapel);
                                        jadwalSenin.setDuration(String.valueOf(lamber));
                                        jadwalSenin.setJam_mulai(jam_mulai);
                                        jadwalSenin.setJam_selesai(jam_selesai);
                                        itemlist.add(jadwalSenin);
                                    }
                                    seninAdapter = new SeninAdapter(itemlist);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_senin.setLayoutManager(layoutManager);
                                    rv_senin.setAdapter(seninAdapter);

                                    break;
                                }
                                case "Selasa": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalSelasa = new JadwalSelasa();
                                        jadwalSelasa.setFullname(guru);
                                        jadwalSelasa.setDay_name(days_name);
                                        jadwalSelasa.setCources_name(mapel);
                                        jadwalSelasa.setDuration(String.valueOf(lamber));
                                        jadwalSelasa.setJam_mulai(jam_mulai);
                                        jadwalSelasa.setJam_selesai(jam_selesai);
                                        itemselasa.add(jadwalSelasa);
                                    }
                                    selasaAdapter = new SelasaAdapter(itemselasa);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_selasa.setLayoutManager(layoutManager);
                                    rv_selasa.setAdapter(selasaAdapter);
                                    break;
                                }
                                case "Rabu": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalRabu = new JadwalRabu();
                                        jadwalRabu.setFullname(guru);
                                        jadwalRabu.setDay_name(days_name);
                                        jadwalRabu.setCources_name(mapel);
                                        jadwalRabu.setDuration(String.valueOf(lamber));
                                        jadwalRabu.setJam_mulai(jam_mulai);
                                        jadwalRabu.setJam_selesai(jam_selesai);
                                        itemRabu.add(jadwalRabu);
                                    }
                                    rabuAdapter = new RabuAdapter(itemRabu);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_rabu.setLayoutManager(layoutManager);
                                    rv_rabu.setAdapter(rabuAdapter);
                                    break;
                                }
                                case "Kamis": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalKamis = new JadwalKamis();
                                        jadwalKamis.setFullname(guru);
                                        jadwalKamis.setDay_name(days_name);
                                        jadwalKamis.setCources_name(mapel);
                                        jadwalKamis.setDuration(String.valueOf(lamber));
                                        jadwalKamis.setJam_mulai(jam_mulai);
                                        jadwalKamis.setJam_selesai(jam_selesai);
                                        itemKamis.add(jadwalKamis);
                                    }
                                    kamisAdapter = new KamisAdapter(itemKamis);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_kamis.setLayoutManager(layoutManager);
                                    rv_kamis.setAdapter(kamisAdapter);

                                    break;
                                }
                                case "Jumat": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalJumat = new JadwalJumat();
                                        jadwalJumat.setFullname(guru);
                                        jadwalJumat.setDay_name(days_name);
                                        jadwalJumat.setCources_name(mapel);
                                        jadwalJumat.setDuration(String.valueOf(lamber));
                                        jadwalJumat.setJam_mulai(jam_mulai);
                                        jadwalJumat.setJam_selesai(jam_selesai);
                                        itemJumat.add(jadwalJumat);
                                    }
                                    jumatAdapter = new JumatAdapter(itemJumat);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_jumat.setLayoutManager(layoutManager);
                                    rv_jumat.setAdapter(jumatAdapter);

                                    break;
                                }
                                case "Sabtu": {
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalSabtu = new JadwalSabtu();
                                        jadwalSabtu.setFullname(guru);
                                        jadwalSabtu.setDay_name(days_name);
                                        jadwalSabtu.setCources_name(mapel);
                                        jadwalSabtu.setDuration(String.valueOf(lamber));
                                        jadwalSabtu.setJam_mulai(jam_mulai);
                                        jadwalSabtu.setJam_selesai(jam_selesai);
                                        itemSabtu.add(jadwalSabtu);
                                    }
                                    sabtuAdapter = new SabtuAdapter(itemSabtu);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    rv_sabtu.setLayoutManager(layoutManager);
                                    rv_sabtu.setAdapter(sabtuAdapter);
                                    break;
                                }
                            }
                            if (days_name.equals(day)){
                                jam_mulai   = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()-1).getTimezFinish();
                            }
                        }
                        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
                        // Set car item title.
                        String jam_sekarang = jamformat.format(Calendar.getInstance().getTime());
                        try {
                            date_now    = times_format.parse(tanggal+" "+ jam_sekarang +":00");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long times_now = date_now.getTime();
                        try {
                            dates = times_format.parse(tanggal+" "+jam_mulai+":00");

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long times_start = dates.getTime();

                        switch (day) {
                            case "Senin":
                                if (itemlist.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_selasa.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_selasa.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_senin.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Selasa":
                                if (itemselasa.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_rabu.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_rabu.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_selasa.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Rabu":
                                if (itemRabu.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_kamis.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_kamis.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_rabu.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Kamis":
                                if (itemKamis.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_jumat.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_jumat.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_kamis.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Jumat":
                                if (itemJumat.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_sabtu.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_sabtu.setVisibility(View.VISIBLE);

                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_jumat.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Sabtu":
                                if (itemSabtu.size() == 0) {
                                    title_jadwal.setText("Jadwal besok");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_senin.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal hari Senin");
                                        rv_senin.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal hari ini");
                                        rv_sabtu.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                            case "Minggu":
                                if (itemlist.size() == 0) {
                                    title_jadwal.setText("Jadwal selasa");
                                    Snackbar.make(coordinatorLayout, "Tidak ada jadwal hari ini", Snackbar.LENGTH_LONG).show();
                                    rv_selasa.setVisibility(View.VISIBLE);
                                } else {
                                    if (times_now > times_start) {
                                        title_jadwal.setText("Jadwal selasa");
                                        rv_selasa.setVisibility(View.VISIBLE);
                                    } else {
                                        title_jadwal.setText("Jadwal besok");
                                        rv_senin.setVisibility(View.VISIBLE);
                                    }
                                }
                                break;
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONResponse.JadwalPelajaran> call, Throwable t) {
                    Log.d("onFailure", t.toString());
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
        dialog = new ProgressDialog(MenuUtama.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}