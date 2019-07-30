package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterKegiatan;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelKegiatan;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Masuk;
import com.google.gson.JsonElement;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KegiatanGuru extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_kegiatan;
    TextView tv_hint;
    Auth mApiInterface;
    JSONArray absenlist,nilailist;
    JsonElement jsonElement;
    String authorization,school_code,member_id,scyear_id, texttodolist,exam_type,cources_id,id_kelas,code;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    JSONObject statusobject,dataObject;
    int status;
    ModelKegiatan modelKegiatan;
    List<ModelKegiatan> modelKegiatanList = new ArrayList<>();
    AdapterKegiatan adapterKegiatan;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kegiatan_guru);
        toolbar         = findViewById(R.id.toolbar_kegiatan);
        rv_kegiatan     = findViewById(R.id.rv_kegiatan);
        tv_hint         = findViewById(R.id.hint_kegiatan);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dapat_kegiatan();
    }

    private void dapat_kegiatan(){
        progressBar();
        showDialog();
        Call<JsonElement> call = mApiInterface.kes_whattodolist_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
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
                        if (absenlist.length() > 0) {
                            for (int i = 0; i < absenlist.length(); i++) {
                                try {
                                    texttodolist = absenlist.getJSONObject(i).getString("absent_todo_text");
                                    id_kelas     = absenlist.getJSONObject(i).getString("class");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                modelKegiatan = new ModelKegiatan();
                                modelKegiatan.setExam_id(null);
                                modelKegiatan.setText(texttodolist);
                                modelKegiatan.setIdkelas(id_kelas);
                                modelKegiatanList.add(modelKegiatan);
                            }
                        }
                        if (nilailist.length() > 0) {
                            for (int o = 0; o < nilailist.length(); o++) {
                                try {
                                    texttodolist = nilailist.getJSONObject(o).getString("exam_todo_text");
                                    exam_type = nilailist.getJSONObject(o).getString("exam_id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                modelKegiatan = new ModelKegiatan();
                                modelKegiatan.setText(texttodolist);
                                modelKegiatan.setExam_id(exam_type);
                                modelKegiatan.setIdkelas(null);
                                modelKegiatanList.add(modelKegiatan);
                            }
                        }
                        adapterKegiatan = new AdapterKegiatan(modelKegiatanList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(KegiatanGuru.this);
                        layoutManager.setOrientation(RecyclerView.VERTICAL);
                        rv_kegiatan.setLayoutManager(layoutManager);
                        rv_kegiatan.setAdapter(adapterKegiatan);
                        adapterKegiatan.setOnItemClickListener(new AdapterKegiatan.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (modelKegiatanList.get(position).getExam_id() == null){
                                    if (modelKegiatanList.get(position).getIdkelas() != null) {
                                        id_kelas = modelKegiatanList.get(position).getIdkelas();
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("classroom_id", id_kelas);
                                        editor.apply();
                                        Intent intent = new Intent(KegiatanGuru.this, AbsenMurid.class);
                                        intent.putExtra("classroom_id", id_kelas);
                                        startActivity(intent);
                                    }
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
        dialog = new ProgressDialog(KegiatanGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
