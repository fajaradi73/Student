package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterSilabus;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelSilabus;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Silabus extends AppCompatActivity {

    Auth mApiInterface;
    RecyclerView rv_silabus;
    String authorization,school_code,member_id,scyear_id,edulevel_id,edulevel_name,cources_id,cources_name,code;
    String mapel,datez,kelas,files,base_silabus;
    int status;
    Toolbar toolbar;
    LinearLayout tv_hint;
    List<ModelSilabus> modelSilabusList = new ArrayList<>();
    ModelSilabus modelSilabus;
    AdapterSilabus adapterSilabus;
    ProgressDialog dialog;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    String kelas_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.silabus);
        toolbar         = findViewById(R.id.toolbarSilabus);
        rv_silabus      = findViewById(R.id.rv_silabus);
        tv_hint         = findViewById(R.id.hint_silabus);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        base_silabus    = ApiClient.BASE_SILABUS;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        edulevel_id         = sharedpreferences.getString("classroom_id","");
        cources_id          = sharedpreferences.getString("cources_id","");

        dapat_silabus();
    }

    private void dapat_silabus(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListSilabus> call = mApiInterface.kes_silabus_get(authorization,school_code.toLowerCase(),member_id,edulevel_id,cources_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListSilabus>() {
            @Override
            public void onResponse(Call<JSONResponse.ListSilabus> call, Response<JSONResponse.ListSilabus> response) {
                Log.v("Sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.ListSilabus resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        if (modelSilabusList != null) {
                            modelSilabusList.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                mapel = response.body().getData().get(i).getCources_name();
                                kelas = response.body().getData().get(i).getEdulevel_name();
                                datez = response.body().getData().get(i).getDatez();
                                files = response.body().getData().get(i).getSilabus_file();
                                modelSilabus = new ModelSilabus();
                                modelSilabus.setFile(files);
                                modelSilabus.setKelas(kelas);
                                modelSilabus.setMapel(mapel);
                                modelSilabus.setTanggal(datez);
                                modelSilabusList.add(modelSilabus);
                            }

                            tv_hint.setVisibility(View.GONE);
                            rv_silabus.setVisibility(View.VISIBLE);
                            adapterSilabus = new AdapterSilabus(Silabus.this, modelSilabusList);
                            rv_silabus.setOnFlingListener(null);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(Silabus.this);
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            rv_silabus.setLayoutManager(layoutManager);
                            rv_silabus.setAdapter(adapterSilabus);
                            adapterSilabus.notifyDataSetChanged();
                        }

                    } else if (status == 0 && code.equals("DTS_ERR_0001")) {
                        tv_hint.setVisibility(View.VISIBLE);
                        rv_silabus.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSilabus> call, Throwable t) {
            hideDialog();
            Log.e("Eror",t.toString());
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
        dialog = new ProgressDialog(Silabus.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
