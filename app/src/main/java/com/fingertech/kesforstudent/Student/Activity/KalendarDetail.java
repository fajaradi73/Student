package com.fingertech.kesforstudent.Student.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalendarDetail extends AppCompatActivity {

    Toolbar toolbar;
    String authorization,school_code,calendar_id;
    TextView tv_tanggal,tv_guru,tv_deskripsi,tv_judul;
    Auth mApiInterface;
    int status;
    String code;
    ImageView iv_background,iv_calendar,iv_people,iv_close;
    String tanggal,jam,deskripsi,judul,guru,warna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_detail);
        tv_tanggal         = findViewById(R.id.tanggal);
        tv_deskripsi       = findViewById(R.id.deskripsi);
        tv_judul           = findViewById(R.id.judul);
        tv_guru            = findViewById(R.id.dibuat);
        iv_background   = findViewById(R.id.color_calendar);
        iv_calendar     = findViewById(R.id.color_calender);
        iv_people       = findViewById(R.id.color_people);
        iv_close        = findViewById(R.id.iv_close);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        calendar_id     = getIntent().getStringExtra("calendar_id");
        warna           = getIntent().getStringExtra("warna");
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detail_calendar();
    }

    public void detail_calendar(){
        Call<JSONResponse.CalendarDetail> call = mApiInterface.kes_calendar_detail_get(authorization,school_code.toLowerCase(),calendar_id);
        call.enqueue(new Callback<JSONResponse.CalendarDetail>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.CalendarDetail> call, Response<JSONResponse.CalendarDetail> response) {
                Log.i("onRespone",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.CalendarDetail resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tanggal     = response.body().getData().getCalendar_date();
                        jam         = response.body().getData().getTimez();
                        deskripsi   = response.body().getData().getCalendar_desc();
                        judul       = response.body().getData().getCalendar_title();
                        guru        = response.body().getData().getCreated_by();
                        iv_background.setColorFilter(Color.parseColor(warna));
                        tv_tanggal.setText(converttanggal(tanggal)+" . "+jam);
                        tv_deskripsi.setText(deskripsi);
                        tv_judul.setText(judul);
                        tv_guru.setText(guru);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CalendarDetail> call, Throwable t) {
                Log.e("calendarEror",t.toString());
            }
        });
    }

    String converttanggal(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));

        SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("in", "ID"));
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
