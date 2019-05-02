package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterJumat;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterKamis;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterRabu;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterSabtu;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterSelasa;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterHari.AdapterSenin;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelJumat;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelKamis;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelRabu;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelSabtu;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelSelasa;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelHari.ModelSenin;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalGuru extends AppCompatActivity {

    Auth mApiInterface;
    Toolbar toolbar;
    CardView btn_senin,btn_selasa,btn_rabu,btn_kamis,btn_jumat,btn_sabtu;
    ImageView arrow_senin,arrow_selasa,arrow_rabu,arrow_kamis,arrow_jumat,arrow_sabtu;
    TextView tv_senin,tv_selasa,tv_rabu,tv_kamis,tv_jumat,tv_sabtu;
    String authorization,school_code,teacher_id,scyear_id;
    RecyclerView rv_senin, rv_selasa, rv_rabu, rv_kamis, rv_jumat, rv_sabtu;
    TextView hint_senin,hint_selasa,hint_rabu,hint_kamis,hint_jumat,hint_sabtu;
    int status;
    String code;
    ProgressDialog dialog;
    private List<ModelSenin> modelSeninList     = new ArrayList<>();
    private List<ModelSelasa> modelSelasaList   = new ArrayList<>();
    private List<ModelRabu> modelRabuList       = new ArrayList<>();
    private List<ModelKamis> modelKamisList     = new ArrayList<>();
    private List<ModelJumat> modelJumatList     = new ArrayList<>();
    private List<ModelSabtu> modelSabtuList     = new ArrayList<>();
    ModelSenin modelSenin;
    ModelSelasa modelSelasa;
    ModelRabu modelRabu;
    ModelKamis modelKamis;
    ModelJumat modelJumat;
    ModelSabtu modelSabtu;
    AdapterSenin adapterSenin;
    AdapterSelasa adapterSelasa;
    AdapterRabu adapterRabu;
    AdapterKamis adapterKamis;
    AdapterJumat adapterJumat;
    AdapterSabtu adapterSabtu;
    String day,days_name;
    SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("in","ID"));
    SharedPreferences sharedpreferences;

    String jam_mulai,jam_selesai,mapel,kelas,lamber,warna_mapel;
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    private boolean isExpanded = false;
    private boolean isExpandedSelasa    = false;
    private boolean isExpandedRabu      = false;
    private boolean isExpandedKamis     = false;
    private boolean isExpandedJumat     = false;
    private boolean isExpandedSabtu     = false;
    private boolean isExpandedSenin     = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_guru);
        toolbar         = findViewById(R.id.toolbarJadwalGuru);
        btn_senin       = findViewById(R.id.klik_senin);
        btn_selasa      = findViewById(R.id.klik_selasa);
        btn_rabu        = findViewById(R.id.klik_rabu);
        btn_kamis       = findViewById(R.id.klik_kamis);
        btn_jumat       = findViewById(R.id.klik_jumat);
        btn_sabtu       = findViewById(R.id.klik_sabtu);
        arrow_senin     = findViewById(R.id.arrow_senin);
        arrow_selasa    = findViewById(R.id.arrow_selasa);
        arrow_rabu      = findViewById(R.id.arrow_rabu);
        arrow_kamis     = findViewById(R.id.arrow_kamis);
        arrow_jumat     = findViewById(R.id.arrow_jumat);
        arrow_sabtu     = findViewById(R.id.arrow_sabtu);
        tv_senin        = findViewById(R.id.jumlah_senin);
        tv_selasa       = findViewById(R.id.jumlah_selasa);
        tv_rabu         = findViewById(R.id.jumlah_rabu);
        tv_kamis        = findViewById(R.id.jumlah_kamis);
        tv_jumat        = findViewById(R.id.jumlah_jumat);
        tv_sabtu        = findViewById(R.id.jumlah_sabtu);
        hint_senin      = findViewById(R.id.hint_senin);
        hint_selasa     = findViewById(R.id.hint_selasa);
        hint_rabu       = findViewById(R.id.hint_rabu);
        hint_kamis      = findViewById(R.id.hint_kamis);
        hint_jumat      = findViewById(R.id.hint_jumat);
        hint_sabtu      = findViewById(R.id.hint_sabtu);
        rv_senin        = findViewById(R.id.senin);
        rv_selasa       = findViewById(R.id.selasa);
        rv_rabu         = findViewById(R.id.rabu);
        rv_kamis        = findViewById(R.id.kamis);
        rv_jumat        = findViewById(R.id.jumat);
        rv_sabtu        = findViewById(R.id.sabtu);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN, "");
        teacher_id          = sharedpreferences.getString(TAG_MEMBER_ID, "");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id           = sharedpreferences.getString("scyear_id","");

        day = outFormat.format(Calendar.getInstance().getTime());

        JadwalNgajar();
        switch (day) {
            case "Senin":
                btn_senin.callOnClick();
                break;
            case "Selasa":
                btn_selasa.callOnClick();
                break;
            case "Rabu":
                btn_rabu.callOnClick();
                break;
            case "Kamis":
                btn_kamis.callOnClick();
                break;
            case "Jumat":
                btn_jumat.callOnClick();
                break;
            case "Sabtu":
                btn_sabtu.callOnClick();
                break;
            case "Minggu":
                btn_senin.callOnClick();
                break;
        }
        btn_senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSenin ? 0 : 90;
                ViewCompat.animate(arrow_senin).rotation(rotation).start();
                isExpandedSenin = !isExpandedSenin;
                if (modelSeninList.size() == 0) {
                    if (isExpandedSenin){
                        hint_senin.setVisibility(View.VISIBLE);
                        rv_senin.setVisibility(View.GONE);
                    }else {
                        rv_senin.setVisibility(View.GONE);
                        hint_senin.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSenin){
                        hint_senin.setVisibility(View.GONE);
                        rv_senin.setVisibility(View.VISIBLE);
                    }else {
                        rv_senin.setVisibility(View.GONE);
                        hint_senin.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSelasa ? 0 : 90;
                ViewCompat.animate(arrow_selasa).rotation(rotation).start();
                isExpandedSelasa = !isExpandedSelasa;
                if (modelSelasaList.size() == 0) {
                    if (isExpandedSelasa){
                        hint_selasa.setVisibility(View.VISIBLE);
                        rv_selasa.setVisibility(View.GONE);
                    }else {
                        rv_selasa.setVisibility(View.GONE);
                        hint_selasa.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSelasa){
                        hint_selasa.setVisibility(View.GONE);
                        rv_selasa.setVisibility(View.VISIBLE);
                    }else {
                        rv_selasa.setVisibility(View.GONE);
                        hint_selasa.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedRabu ? 0 : 90;
                ViewCompat.animate(arrow_rabu).rotation(rotation).start();
                isExpandedRabu = !isExpandedRabu;
                if (modelRabuList.size() == 0) {
                    if (isExpandedRabu){
                        hint_rabu.setVisibility(View.VISIBLE);
                        rv_rabu.setVisibility(View.GONE);
                    }else {
                        rv_rabu.setVisibility(View.GONE);
                        hint_rabu.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedRabu){
                        hint_rabu.setVisibility(View.GONE);
                        rv_rabu.setVisibility(View.VISIBLE);
                    }else {
                        rv_rabu.setVisibility(View.GONE);
                        hint_rabu.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedKamis ? 0 : 90;
                ViewCompat.animate(arrow_kamis).rotation(rotation).start();
                isExpandedKamis = !isExpandedKamis;
                if (modelKamisList.size() == 0) {
                    if (isExpandedKamis){
                        hint_kamis.setVisibility(View.VISIBLE);
                        rv_kamis.setVisibility(View.GONE);
                    }else {
                        rv_kamis.setVisibility(View.GONE);
                        hint_kamis.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedKamis){
                        hint_kamis.setVisibility(View.GONE);
                        rv_kamis.setVisibility(View.VISIBLE);
                    }else {
                        rv_kamis.setVisibility(View.GONE);
                        hint_kamis.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_jumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedJumat ? 0 : 90;
                ViewCompat.animate(arrow_jumat).rotation(rotation).start();
                isExpandedJumat = !isExpandedJumat;
                if (modelJumatList.size() == 0) {
                    if (isExpandedJumat){
                        hint_jumat.setVisibility(View.VISIBLE);
                        rv_jumat.setVisibility(View.GONE);
                    }else {
                        rv_jumat.setVisibility(View.GONE);
                        hint_jumat.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedJumat){
                        hint_jumat.setVisibility(View.GONE);
                        rv_jumat.setVisibility(View.VISIBLE);
                    }else {
                        rv_jumat.setVisibility(View.GONE);
                        hint_jumat.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_sabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSabtu ? 0 : 90;
                ViewCompat.animate(arrow_sabtu).rotation(rotation).start();
                isExpandedSabtu = !isExpandedSabtu;
                if (modelSabtuList.size() == 0) {
                    if (isExpandedSabtu){
                        hint_sabtu.setVisibility(View.VISIBLE);
                        rv_sabtu.setVisibility(View.GONE);
                    }else {
                        rv_sabtu.setVisibility(View.GONE);
                        hint_sabtu.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSabtu){
                        hint_sabtu.setVisibility(View.GONE);
                        rv_sabtu.setVisibility(View.VISIBLE);
                    }else {
                        rv_sabtu.setVisibility(View.GONE);
                        hint_sabtu.setVisibility(View.GONE);
                    }
                }
            }
        });
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            tv_senin.setText(days[0]);
            tv_selasa.setText(days[1]);
            tv_rabu.setText(days[2]);
            tv_kamis.setText(days[3]);
            tv_jumat.setText(days[4]);
            tv_sabtu.setText(days[5]);
        }
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
        dialog = new ProgressDialog(JadwalGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @SuppressLint("SetTextI18n")
    private void JadwalNgajar() {
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalGuru> call = mApiInterface.kes_class_schedule_teacher_get(authorization, school_code.toLowerCase(), teacher_id, scyear_id);
        call.enqueue(new Callback<JSONResponse.JadwalGuru>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalGuru> call, Response<JSONResponse.JadwalGuru> response) {
                Log.d("Sukses", response.code() + "");
                hideDialog();
                JSONResponse.JadwalGuru resource = response.body();
                status = resource.status;
                code = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    for (int i = 0 ; i < response.body().getData().getSchedule_class().size(); i++){
                        days_name = response.body().getData().getSchedule_class().get(i).getDay_name();
                        switch (days_name) {
                            case "Senin": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelSenin = new ModelSenin();
                                    modelSenin.setJam_mulai(jam_mulai);
                                    modelSenin.setJam_selesai(jam_selesai);
                                    modelSenin.setKelas(kelas);
                                    modelSenin.setWarna_mapel(warna_mapel);
                                    modelSenin.setLama_ngajar(lamber);
                                    modelSenin.setMapel(mapel);
                                    modelSeninList.add(modelSenin);
                                }
                                adapterSenin = new AdapterSenin(modelSeninList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_senin.setLayoutManager(layoutManager);
                                rv_senin.setAdapter(adapterSenin);
                                break;
                            }
                            case "Selasa": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelSelasa = new ModelSelasa();
                                    modelSelasa.setJam_mulai(jam_mulai);
                                    modelSelasa.setJam_selesai(jam_selesai);
                                    modelSelasa.setKelas(kelas);
                                    modelSelasa.setWarna_mapel(warna_mapel);
                                    modelSelasa.setLama_ngajar(lamber);
                                    modelSelasa.setMapel(mapel);
                                    modelSelasaList.add(modelSelasa);
                                }
                                adapterSelasa = new AdapterSelasa(modelSelasaList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_selasa.setLayoutManager(layoutManager);
                                rv_selasa.setAdapter(adapterSelasa);
                                break;
                            }
                            case "Rabu": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelRabu = new ModelRabu();
                                    modelRabu.setJam_mulai(jam_mulai);
                                    modelRabu.setJam_selesai(jam_selesai);
                                    modelRabu.setKelas(kelas);
                                    modelRabu.setWarna_mapel(warna_mapel);
                                    modelRabu.setLama_ngajar(lamber);
                                    modelRabu.setMapel(mapel);
                                    modelRabuList.add(modelRabu);
                                }
                                adapterRabu = new AdapterRabu(modelRabuList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_rabu.setLayoutManager(layoutManager);
                                rv_rabu.setAdapter(adapterRabu);
                                break;
                            }
                            case "Kamis": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelKamis = new ModelKamis();
                                    modelKamis.setJam_mulai(jam_mulai);
                                    modelKamis.setJam_selesai(jam_selesai);
                                    modelKamis.setKelas(kelas);
                                    modelKamis.setWarna_mapel(warna_mapel);
                                    modelKamis.setLama_ngajar(lamber);
                                    modelKamis.setMapel(mapel);
                                    modelKamisList.add(modelKamis);
                                }
                                adapterKamis = new AdapterKamis(modelKamisList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_kamis.setLayoutManager(layoutManager);
                                rv_kamis.setAdapter(adapterKamis);
                                break;
                            }
                            case "Jumat": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelJumat = new ModelJumat();
                                    modelJumat.setJam_mulai(jam_mulai);
                                    modelJumat.setJam_selesai(jam_selesai);
                                    modelJumat.setKelas(kelas);
                                    modelJumat.setWarna_mapel(warna_mapel);
                                    modelJumat.setLama_ngajar(lamber);
                                    modelJumat.setMapel(mapel);
                                    modelJumatList.add(modelJumat);
                                }
                                adapterJumat = new AdapterJumat(modelJumatList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_jumat.setLayoutManager(layoutManager);
                                rv_jumat.setAdapter(adapterJumat);
                                break;
                            }
                            case "Sabtu": {
                                for (int o = 0; o < response.body().getData().getSchedule_class().get(i).getSchedule_class().size(); o++) {
                                    jam_mulai   = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_start();
                                    jam_selesai = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getTimez_finish();
                                    lamber      = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getLesson_duration();
                                    mapel       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_name();
                                    kelas       = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getClassroom_name();
                                    warna_mapel = response.body().getData().getSchedule_class().get(i).getSchedule_class().get(o).getCources_colour();
                                    modelSabtu = new ModelSabtu();
                                    modelSabtu.setJam_mulai(jam_mulai);
                                    modelSabtu.setJam_selesai(jam_selesai);
                                    modelSabtu.setKelas(kelas);
                                    modelSabtu.setWarna_mapel(warna_mapel);
                                    modelSabtu.setLama_ngajar(lamber);
                                    modelSabtu.setMapel(mapel);
                                    modelSabtuList.add(modelSabtu);
                                }
                                adapterSabtu = new AdapterSabtu(modelSabtuList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalGuru.this);
                                rv_sabtu.setLayoutManager(layoutManager);
                                rv_sabtu.setAdapter(adapterSabtu);
                                break;
                            }
                        }
                    }
                    switch (day) {
                        case "Senin":
                            btn_senin.performClick();
                            break;
                        case "Selasa":
                            btn_selasa.performClick();
                            break;
                        case "Rabu":
                            btn_rabu.performClick();
                            break;
                        case "Kamis":
                            btn_kamis.performClick();
                            break;
                        case "Jumat":
                            btn_jumat.performClick();
                            break;
                        case "Sabtu":
                            btn_sabtu.performClick();
                            break;
                        case "Minggu":
                            btn_senin.performClick();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalGuru> call, Throwable t) {

            }
        });
    }
}
