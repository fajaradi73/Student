package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;

public class PesanTerkirimDetail extends AppCompatActivity {

    Toolbar toolbar;
    Auth mApiInterface;
    TextView tv_pengirim,tv_kepada,tv_title,tv_tanggal,tv_isipesan;
    String authorization,school_code,member_id,scyear_id, reply_message_id,message_id,fullname;
    ProgressDialog dialog;
    int status;
    String code;
    String pengirim,title,tanggal,isipesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_terkirim_detail);

        toolbar         = findViewById(R.id.toolbarPesanMasukGuru);
        tv_isipesan     = findViewById(R.id.isi_pesan);
        tv_kepada       = findViewById(R.id.kepada);
        tv_title        = findViewById(R.id.title_pesan);
        tv_pengirim     = findViewById(R.id.pengirim);
        tv_tanggal      = findViewById(R.id.tanggal);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        member_id           = getIntent().getStringExtra("member_id");
        message_id          = getIntent().getStringExtra("message_id");
        reply_message_id    = getIntent().getStringExtra("reply_message_id");
        fullname            = getIntent().getStringExtra("fullname");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        kirim_pesan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PesanTerkirimDetail.this, Pesan_Terkirim.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
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
        Intent intent = new Intent(PesanTerkirimDetail.this,Pesan_Terkirim.class);
        intent.putExtra("authorization",authorization);
        intent.putExtra("school_code",school_code);
        intent.putExtra("member_id", member_id);
        setResult(RESULT_OK, intent);
        finish();
    }

//    public void kirim_pesan(){
//        progressBar();
//        showDialog();
//        Call<JSONResponse.ListPesanTerkirimGuru> call = mApiInterface.kes_message_sent_get(authorization.toString(),school_code.toLowerCase().toString(),member_id.toString(),message_id.toString(),reply_message_id.toString());
//        call.enqueue(new Callback<JSONResponse.ListPesanTerkirimGuru>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<JSONResponse.ListPesanTerkirimGuru> call, Response<JSONResponse.ListPesanTerkirimGuru> response) {
//                Log.i("onResponse",response.code()+"");
//                hideDialog();
//                if (response.isSuccessful()) {
//                    JSONResponse.ListPesanTerkirimGuru resource = response.body();
//                    status = resource.status;
//                    code = resource.code;
//                    if (status == 1 && code.equals("DTS_SCS_0001")) {
//                        tanggal     = response.body().getData().getPesanTerkirimDetail().getDatez();
//                        isipesan    = response.body().getData().getDataPesanTerkirim().getMessage_cont();
//                        pengirim    = response.body().getData().getDataPesanTerkirim().getSender_name();
//                        title       = response.body().getData().getDataPesanTerkirim().getMessage_title();
//                        tv_isipesan.setText(isipesan);
//                        tv_tanggal.setText(tanggal);
//                        tv_pengirim.setText(pengirim);
//                        if (title.equals("")){
//                            tv_title.setText("( Tidak ada subjek )");
//                        }else {
//                            tv_title.setText(title);
//                        }
//                        tv_kepada.setText(fullname);
//                    }
//                }else if (response.code() == 500){
//                    FancyToast.makeText(getApplicationContext(),"Eror database",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse.ListPesanTerkirimGuru> call, Throwable t) {
//                Log.i("onFailure",t.toString());
//                FancyToast.makeText(getApplicationContext(),"Pesan Rusak", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
//                hideDialog();
//
//            }
//        });
//    }

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
        dialog = new ProgressDialog(PesanTerkirimDetail.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}

