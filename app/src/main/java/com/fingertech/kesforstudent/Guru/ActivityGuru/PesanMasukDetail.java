package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Sqlite.NotifikasiGuruTable;
import com.fingertech.kesforstudent.Sqlite.NotifikasiTable;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kesforstudent.Guru.ActivityGuru.KegiatanGuru.TAG_TOKEN;
import static com.fingertech.kesforstudent.Guru.ActivityGuru.MenuUtamaGuru.TAG_MEMBER_ID;
import static com.fingertech.kesforstudent.Guru.ActivityGuru.MenuUtamaGuru.TAG_SCHOOL_CODE;

public class PesanMasukDetail extends AppCompatActivity {

    CardView btn_balas;
    Toolbar toolbar;
    Auth mApiInterface;
    TextView tv_pengirim,tv_kepada,tv_title,tv_tanggal,tv_isipesan;
    ImageView iv_pesan;
    String authorization,school_code,member_id,scyear_id, reply_message_id,message_id,fullname;
    ProgressDialog dialog;
    int status;
    String code;
    String pengirim,title,tanggal,isipesan;
    SharedPreferences sharedpreferences,sharedPreferences;
    String tanggals,click;
    int id_notif;
    private NotifikasiGuruTable notifikasiTable = new NotifikasiGuruTable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appbar_pesan_masuk);
        btn_balas       = findViewById(R.id.btn_balas);
        toolbar         = findViewById(R.id.toolbarPesanMasukGuru);
        tv_isipesan     = findViewById(R.id.isi_pesan);
        tv_kepada       = findViewById(R.id.kepada);
        tv_title        = findViewById(R.id.title_pesan);
        tv_pengirim     = findViewById(R.id.pengirim);
        tv_tanggal      = findViewById(R.id.tanggal);
        iv_pesan        = findViewById(R.id.image_pesan);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        message_id          = getIntent().getStringExtra("message_id");
        reply_message_id    = getIntent().getStringExtra("reply_message_id");
        fullname            = getIntent().getStringExtra("fullname");

        click               = getIntent().getStringExtra("clicked");
        id_notif            = sharedPreferences.getInt("id_notif",0);

        if (click != null){
            notifikasiTable.updateStatus(id_notif,"0","1");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        dapat_pesan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PesanMasukDetail.this,PesanMasukGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id", member_id);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(PesanMasukDetail.this,PesanMasukGuru.class);
        intent.putExtra("authorization",authorization);
        intent.putExtra("school_code",school_code);
        intent.putExtra("member_id", member_id);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_inbox_detail_get(authorization.toString(),school_code.toLowerCase().toString(),member_id.toString(),message_id.toString(),reply_message_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.i("onResponse",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.PesanDetail resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tanggal     = response.body().getData().getDataMessage().getDatez();
                        isipesan    = response.body().getData().getDataMessage().getMessage_cont();
                        pengirim    = response.body().getData().getDataMessage().getSender_name();
                        title       = response.body().getData().getDataMessage().getMessage_title();
                        tv_isipesan.setText(isipesan);
                        tv_tanggal.setText(tanggal);
                        tv_pengirim.setText(pengirim);
                        if (title.equals("")){
                            tv_title.setText("( Tidak ada subjek )");
                        }else {
                            tv_title.setText(title);
                        }
                        if (fullname != null) {
                            tv_kepada.setText(fullname);
                        }else {
                            tv_kepada.setText("Anda");
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Eror database",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {
                Log.i("onFailure",t.toString());
                FancyToast.makeText(getApplicationContext(),"Pesan Tidak ditemukan", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
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
        dialog = new ProgressDialog(PesanMasukDetail.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
