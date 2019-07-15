package com.fingertech.kesforstudent.Student.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kesforstudent.Service.App.getContext;

public class PesanDetail extends AppCompatActivity {

    Auth mApiInterface;
    String authorization,school_code,student_id,classroom_id,message_id,nama_anak,read_status;
    int status;
    String code;
    TextView tv_subject,tv_pengirim,tv_kelas,tv_pesan,tv_mapel,tv_jam,tv_anak;
    String subject,pengirim,kelas,pesan,mapel,jam;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    CircleImageView imageView;
    Date date_now,date_message;

    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String tanggals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_detail);
        toolbar         = findViewById(R.id.toolbar_pesan_detail);
        tv_subject      = findViewById(R.id.subject);
        tv_pengirim     = findViewById(R.id.pengirim_pesan);
        tv_kelas        = findViewById(R.id.kelas_pesan);
        tv_anak         = findViewById(R.id.anak_pesan);
        tv_jam          = findViewById(R.id.jam);
        tv_pesan        = findViewById(R.id.pesan);
        tv_mapel        = findViewById(R.id.mapel_pesan);
        imageView       = findViewById(R.id.image_pesan);

        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        message_id          = sharedPreferences.getString("message_id",null);
        nama_anak           = sharedPreferences.getString("student_name",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        pesan_detail();
    }

    private void pesan_detail(){
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_message_detail_get(authorization,school_code.toLowerCase(),student_id,classroom_id,message_id);
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.d("response",response.code()+"");
                JSONResponse.PesanDetail resource = response.body();
                status = resource.status;
                code   = resource.code;
                if (status==1&&code.equals("DTS_SCS_0001")){
                    subject     = response.body().getData().getDataMessage().getMessage_title();
                    pengirim    = response.body().getData().getDataMessage().getSender_name();
                    jam         = response.body().getData().getDataMessage().getDatez();
                    kelas       = response.body().getData().getDataMessage().getClassroom_name();
                    pesan       = response.body().getData().getDataMessage().getMessage_cont();
                    mapel       = response.body().getData().getDataMessage().getCources_name();
                    tanggals    = response.body().getData().getDataMessage().getMessage_date();

                    if (subject.equals("")){
                        tv_subject.setText("( Tidak ada Subject )");
                    }else {
                        tv_subject.setText(subject);
                    }

                    String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
                    // Set car item title.
                    try {
                        date_now    = times_format.parse(tanggal);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long times_now = date_now.getTime();

                    try {
                        date_message = times_format.parse(tanggals);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Long times_pesan = date_message.getTime();
                    if (times_pesan.equals(times_now)){
                        tv_jam.setText(convertjam(jam));
                    }else {
                        tv_jam.setText(convertTanggal(tanggals));
                    }

                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + pengirim+"&background=1de9b6&color=fff&font-size=0.40&length=1").into(imageView);

                    tv_pengirim.setText(pengirim);
                    tv_mapel.setText(mapel);
                    tv_kelas.setText(kelas);
                    tv_pesan.setText(pesan);
                    tv_anak.setText(nama_anak);

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {

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
        dialog = new ProgressDialog(PesanDetail.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PesanDetail.this,PesanAnak.class);
        intent.putExtra("authorization",authorization);
        intent.putExtra("school_code",school_code);
        intent.putExtra("member_id", student_id);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(PesanDetail.this,PesanAnak.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id", student_id);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    String convertjam(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}