package com.fingertech.kesforstudent.Guru.FragmentGuru;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.ActivityGuru.AbsenMurid;
import com.fingertech.kesforstudent.Guru.ActivityGuru.AgendaGuru;
import com.fingertech.kesforstudent.Guru.ActivityGuru.JadwalGuru;
import com.fingertech.kesforstudent.Guru.ActivityGuru.PenilaianGuru;
import com.fingertech.kesforstudent.Guru.ActivityGuru.Silabus;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Activity.Masuk;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragMenuGuruSatu extends Fragment {

    public FragMenuGuruSatu() {
        // Required empty public constructor
    }

    CardView btn_absensi,btn_penilaian,btn_silabus,btn_pesan,btn_jadwal, btn_agenda;
    SharedPreferences sharedpreferences,sharedViewpager;
    String picture, Base_anak;
    String authorization, memberid, username, member_type, fullname, school_code,scyear_id;
    Auth mApiInterface;
    int status;
    String code;
    public static final String TAG_EMAIL = "email";
    public static final String TAG_MEMBER_ID = "member_id";
    public static final String TAG_FULLNAME = "fullname";
    public static final String TAG_MEMBER_TYPE = "member_type";
    public static final String TAG_TOKEN = "token";
    public static final String TAG_SCHOOL_CODE = "school_code";
    public static final String my_viewpager_preferences = "my_viewpager_preferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization   = sharedpreferences.getString(TAG_TOKEN, "");
        memberid        = sharedpreferences.getString(TAG_MEMBER_ID, "");
        username        = sharedpreferences.getString(TAG_EMAIL, "");
        fullname        = sharedpreferences.getString(TAG_FULLNAME, "");
        member_type     = sharedpreferences.getString(TAG_MEMBER_TYPE, "");
        school_code     = sharedpreferences.getString(TAG_SCHOOL_CODE, "");
        scyear_id       = sharedpreferences.getString("scyear_id","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_menu_guru_satu, container, false);
        btn_absensi     = view.findViewById(R.id.btn_absensi);
        btn_penilaian   = view.findViewById(R.id.btn_penilaian);
        btn_jadwal      = view.findViewById(R.id.btn_jadwal_mengajar);
        btn_silabus     = view.findViewById(R.id.btn_silabus);
        btn_pesan       = view.findViewById(R.id.btn_pesan);
        btn_agenda = view.findViewById(R.id.btn_agenda);
        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(getContext(), JadwalGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });

        btn_absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(getContext(), AbsenMurid.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });


        btn_silabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(getContext(), Silabus.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });

        btn_penilaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(getContext(), PenilaianGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });

        btn_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("authorization",authorization);
                editor.putString("member_id",memberid);
                editor.putString("school_code",school_code);
                editor.putString("scyear_id",scyear_id);
                editor.apply();
                Intent intent = new Intent(getContext(), AgendaGuru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                intent.putExtra("scyear_id",scyear_id);
                startActivity(intent);
            }
        });


        return view;
    }

}
