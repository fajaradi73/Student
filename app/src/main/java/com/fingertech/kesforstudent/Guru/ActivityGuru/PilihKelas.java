package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.google.android.material.snackbar.Snackbar;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihKelas extends AppCompatActivity {

    Auth mApiInterface;
    int status;
    String authorization,school_code,member_id,scyear_id,code,nama_kelas,id_kelas;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    SharedPreferences sharedpreferences;
    List<String> listEdulevel           = new ArrayList<String>();
    private List<JSONResponse.DataKelas> dataEdulevelList;
    Spinner sp_edulevel;
    CardView cv_close,cv_absen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_kelas);
        sp_edulevel     = findViewById(R.id.sp_kelas);
        cv_absen        = findViewById(R.id.cv_absen);
        cv_close        = findViewById(R.id.iv_close);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization       = sharedpreferences.getString(TAG_TOKEN,"");
        member_id           = sharedpreferences.getString(TAG_MEMBER_ID,"");
        scyear_id           = sharedpreferences.getString("scyear_id","");
        school_code         = sharedpreferences.getString(TAG_SCHOOL_CODE,"");

        dapat_edulevel();

        cv_close.setOnClickListener(v -> {
            finish();
        });
        cv_absen.setOnClickListener(v -> {
            if (id_kelas != null){
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",member_id);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.putString("classroom_id",id_kelas);
                editor.apply();
                Intent intent = new Intent(this, AbsenMurid.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",member_id);
                intent.putExtra("scyear_id",scyear_id);
                intent.putExtra("classroom_id",id_kelas);
                startActivity(intent);
            }else {
                Snackbar.make(v,"Silahkan pilih kelas terlebih dahulu",Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void dapat_edulevel(){
        Call<JSONResponse.ListKelas> call = mApiInterface.kes_get_classroom_get(authorization,school_code.toLowerCase(),member_id,scyear_id);
        call.enqueue(new Callback<JSONResponse.ListKelas>() {
            @Override
            public void onResponse(Call<JSONResponse.ListKelas> call, Response<JSONResponse.ListKelas> response) {
                Log.d("SuksesKelas",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListKelas resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataEdulevelList = response.body().getData();
                        listEdulevel.add("Pilih Kelas");
                        for (int i = 0; i < dataEdulevelList.size(); i++) {
                            nama_kelas = dataEdulevelList.get(i).getClassroom_name();
                            listEdulevel.add(nama_kelas);
                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                    PilihKelas.this, R.layout.spinner_full, listEdulevel) {
                                @Override
                                public boolean isEnabled(int position) {
                                    if (position == 0) {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return false;
                                    } else {
                                        return true;
                                    }
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                                    if (position == 0) {
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    } else {
                                        tv.setTextColor(Color.BLACK);
                                    }
                                    return view;
                                }
                            };
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_edulevel.setAdapter(adapterRaport);
                            sp_edulevel.setOnItemSelectedListener((parent, view, position, id) -> {
                                if (position == 0) {
                                    id_kelas = null;
                                } else {
                                    id_kelas = dataEdulevelList.get(position - 1).getClassroomid();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListKelas> call, Throwable t) {
                Log.e("GagalClass",t.toString());
            }
        });
    }
}
