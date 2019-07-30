package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterPesanGuru;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelPesanGuru;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Masuk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanMasukGuru extends AppCompatActivity {

    Toolbar toolbar;

    SharedPreferences sharedpreferences,sharedViewpager;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id;

    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";

    RecyclerView rv_pesan;
    LinearLayout tv_hint;
    Auth mApiInterface;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String code,date_from,date_to,statusku;
    ProgressDialog dialog;
    AdapterPesanGuru adapterPesanGuru;
    ModelPesanGuru modelPesanGuru;
    List<ModelPesanGuru> modelPesanGuruList = new ArrayList<>();
    String pengirim,isipesan,title,tanggal,jam,statusread,message_id,reply_message_id,picture;
    int status;
    FloatingActionButton fab_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbar_pesan_guru);

        toolbar         = findViewById(R.id.toolbarPesanMasukGuru);
        rv_pesan        = findViewById(R.id.rv_pesan_guru);
        tv_hint         = findViewById(R.id.hint_pesan);
        fab_pesan       = findViewById(R.id.fab);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences = this.getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-2);
        date_from   =  dateFormatForMonth.format(calendar.getTime());
        date_to     =  dateFormatForMonth.format(Calendar.getInstance().getTime());
        fab_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(PesanMasukGuru.this,TulisPesan.class);
                startActivity(intent);
            }
        });

        dapat_pesan();
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

    private void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListPesanGuru> call = mApiInterface.kes_message_inbox_get(authorization,school_code.toLowerCase(),memberid,date_from,date_to);
        call.enqueue(new Callback<JSONResponse.ListPesanGuru>() {
            @Override
            public void onResponse(Call<JSONResponse.ListPesanGuru> call, Response<JSONResponse.ListPesanGuru> response) {
                Log.d("pesanSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.ListPesanGuru resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        tv_hint.setVisibility(View.GONE);
                        rv_pesan.setVisibility(View.VISIBLE);
                        if (modelPesanGuruList!=null) {
                            modelPesanGuruList.clear();
                            for (JSONResponse.DataPesanGuru dataPesanGuru : response.body().getData()) {
                                title               = dataPesanGuru.getMessage_title();
                                isipesan            = dataPesanGuru.getMessage_cont();
                                tanggal             = dataPesanGuru.getMessage_date();
                                jam                 = dataPesanGuru.getDatez();
                                statusread          = dataPesanGuru.getRead_status();
                                message_id          = dataPesanGuru.getMessageid();
                                pengirim            = dataPesanGuru.getSender_name();
                                reply_message_id    = dataPesanGuru.getReply_message_id();
                                picture             = dataPesanGuru.getPicture();
                                modelPesanGuru = new ModelPesanGuru();
                                modelPesanGuru.setDari(pengirim);
                                modelPesanGuru.setJam(jam);
                                modelPesanGuru.setMessage_id(message_id);
                                modelPesanGuru.setRead_status(statusread);
                                modelPesanGuru.setPesan(isipesan);
                                modelPesanGuru.setTitle(title);
                                modelPesanGuru.setReply_message_id(reply_message_id);
                                modelPesanGuru.setTanggal(tanggal);
                                modelPesanGuru.setPicture(ApiClient.BASE_IMAGE + picture);
                                modelPesanGuruList.add(modelPesanGuru);
                            }
                            adapterPesanGuru = new AdapterPesanGuru(modelPesanGuruList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(PesanMasukGuru.this);
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            rv_pesan.setLayoutManager(layoutManager);
                            rv_pesan.setAdapter(adapterPesanGuru);
                            adapterPesanGuru.setOnItemClickListener(new AdapterPesanGuru.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(PesanMasukGuru.this, PesanMasukDetail.class);
                                    intent.putExtra("fullname", fullname);
                                    intent.putExtra("authorization", authorization);
                                    intent.putExtra("school_code", school_code);
                                    intent.putExtra("member_id", memberid);
                                    intent.putExtra("message_id", modelPesanGuruList.get(position).getMessage_id());
                                    intent.putExtra("reply_message_id", modelPesanGuruList.get(position).getReply_message_id());
                                    startActivityForResult(intent, 1);
                                }
                            });
                        }
                    }else {
                        tv_hint.setVisibility(View.VISIBLE);
                        rv_pesan.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListPesanGuru> call, Throwable t) {
                Log.e("pesanGagal",t.toString());
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
        dialog = new ProgressDialog(PesanMasukGuru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                authorization   = data.getStringExtra("authorization");
                school_code     = data.getStringExtra("school_code");
                memberid        = data.getStringExtra("member_id");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH,-2);
                date_from   = dateFormatForMonth.format(calendar.getTime());
                date_to     = dateFormatForMonth.format(Calendar.getInstance().getTime());
                dapat_pesan();
            }
        }
    }
}
