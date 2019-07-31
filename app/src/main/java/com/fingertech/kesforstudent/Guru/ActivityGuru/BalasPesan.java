package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalasPesan extends AppCompatActivity {

    Auth mApiInterface;
    TextView sekolahpesan,kelaspesan,pesan;

    int status;

    String authorization,school_code,parent_id,message_id,parent_message_id,fullname,code;
    EditText pesanbaru;
    CardView balas;
    boolean clicked = false;
    String pengirim,title,tanggal,isipesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balas_pesan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        sekolahpesan    = findViewById(R.id.sekolah_pesan);
        kelaspesan      = findViewById(R.id.kelas_pesan);
        pesan           = findViewById(R.id.tv_pesan_guru);
        pesanbaru       = findViewById(R.id.etpesan);
        balas           = findViewById(R.id.btn_balas);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        parent_id           = getIntent().getStringExtra("member_id");
        message_id          = getIntent().getStringExtra("message_id");
        parent_message_id   = getIntent().getStringExtra("reply_message_id");
        fullname            = getIntent().getStringExtra("fullname");

        mApiInterface   = ApiClient.getClient().create(Auth.class);
        dapat_pesan();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        pesanbaru.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        balas.setOnClickListener(v -> {
            balas_pesan();
        });

    }
    public void dapat_pesan(){
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_inbox_detail_get(authorization.toString(),school_code.toLowerCase().toString(),parent_id.toString(),message_id.toString(),parent_message_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.i("onResponse",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.PesanDetail resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tanggal     = response.body().getData().getDataMessage().getDatez();
                        isipesan    = response.body().getData().getDataMessage().getMessage_cont();
                        pengirim    = response.body().getData().getDataMessage().getSender_name();
                        title       = response.body().getData().getDataMessage().getMessage_title();
                        sekolahpesan.setText(pengirim);
                        if (title.equals("")){
                            pesan.setText("( Tidak ada subjek )");
                        }else {
                            pesan.setText(title);
                        }
                        if (fullname != null) {
                            kelaspesan.setText(fullname);
                        }else {
                            kelaspesan.setText("Anda");
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
            }
        });
    }

    private void balas_pesan(){
        Call<JSONResponse>call = mApiInterface.kes_reply_post(authorization,school_code.toLowerCase(),parent_id,message_id,parent_message_id,pesanbaru.getText().toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.d("sukses",response.code()+"");
                if (response.isSuccessful()){
                    if (response.body() != null){
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            finish();
                            FancyToast.makeText(getApplicationContext(),"Pesan Terkirim",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

    }

}
