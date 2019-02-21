package com.fingertech.kesforstudent.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanDetail extends AppCompatActivity {


    Toolbar toolbar;
    Auth mApiInterface;
    TextView tanggal,dibuat,mapel,kelas,pesan;
    String authorization,school_code,member_id,message_id,classroom_id;
    String code;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_detail);
        toolbar             = findViewById(R.id.toolbar_pesan_detail);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        tanggal             = findViewById(R.id.tanggal);
        dibuat              = findViewById(R.id.dibuat_oleh);
        mapel               = findViewById(R.id.mapel_pesan);
        kelas               = findViewById(R.id.kelas_pesan);
        pesan               = findViewById(R.id.isi_pesan);

        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        member_id           = getIntent().getStringExtra("member_id");
        message_id          = getIntent().getStringExtra("message_id");
        classroom_id        = getIntent().getStringExtra("classroom_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        dapat_pesan();

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


    private void dapat_pesan(){
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_message_detail_get(authorization.toString(),school_code.toLowerCase(),member_id.toString(),classroom_id.toString(),message_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.d("onResponse",response.code()+"");
                JSONResponse.PesanDetail resource = response.body();

                status = resource.status;
                code   = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    tanggal.setText(response.body().getData().getDataMessage().getDatez());
                    dibuat.setText(response.body().getData().getDataMessage().getCreated_by());
                    mapel.setText(response.body().getData().getDataMessage().getCources_name());
                    kelas.setText(response.body().getData().getDataMessage().getClassroom_name());
                    pesan.setText(Html.fromHtml(response.body().getData().getDataMessage().getMessage_cont()));
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }
        });
    }
}
