package com.fingertech.kesforstudent.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fingertech.kesforstudent.Activity.JadwalPelajaran;
import com.fingertech.kesforstudent.Activity.JadwalUjian;
import com.fingertech.kesforstudent.Activity.MenuUtama;
import com.fingertech.kesforstudent.Activity.RaporAnak;
import com.fingertech.kesforstudent.Activity.TugasAnak;
import com.fingertech.kesforstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSatuFragment extends Fragment {


    public MenuSatuFragment() {
        // Required empty public constructor
    }


    CardView btn_jadwal,btn_ujian,btn_absen,btn_tugas,btn_raport,btn_kalendar;
    SharedPreferences sharedPreferences;
    String authorization,school_code,member_id,classroom_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_satu, container, false);
        btn_jadwal      = view.findViewById(R.id.btn_jadwal);
        btn_ujian       = view.findViewById(R.id.btn_jadwal_ujian);
        btn_absen       = view.findViewById(R.id.btn_absen);
        btn_tugas       = view.findViewById(R.id.btn_tugas);
        btn_raport      = view.findViewById(R.id.btn_raport);
        btn_kalendar    = view.findViewById(R.id.btn_kalender);

        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && member_id != null && classroom_id != null) {
                    Intent intent = new Intent(getContext(), JadwalPelajaran.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && member_id != null && classroom_id != null) {
                    Intent intent = new Intent(getContext(), JadwalUjian.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_tugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && member_id != null && classroom_id != null) {
                    Intent intent = new Intent(getContext(), TugasAnak.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_raport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && member_id != null && classroom_id != null) {
                    Intent intent = new Intent(getContext(), RaporAnak.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("member_id", member_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}
