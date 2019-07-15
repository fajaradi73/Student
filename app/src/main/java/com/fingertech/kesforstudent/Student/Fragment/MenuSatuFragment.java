package com.fingertech.kesforstudent.Student.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingertech.kesforstudent.Student.Activity.AbsenAnak;
import com.fingertech.kesforstudent.Student.Activity.AgendaAnak;
import com.fingertech.kesforstudent.Student.Activity.JadwalPelajaran;
import com.fingertech.kesforstudent.Student.Activity.KalendarKelas;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.fingertech.kesforstudent.Student.Activity.PesanAnak;
import com.fingertech.kesforstudent.Student.Activity.RaporAnak;
import com.fingertech.kesforstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSatuFragment extends Fragment {


    public MenuSatuFragment() {
        // Required empty public constructor
    }


    CardView btn_jadwal, btn_pesan,btn_absen, btn_agenda,btn_raport,btn_kalendar;
    SharedPreferences sharedPreferences;
    String authorization,school_code,member_id,classroom_id,nama_anak;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        nama_anak           = sharedPreferences.getString("fullname",null);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_satu, container, false);
        btn_jadwal      = view.findViewById(R.id.btn_jadwal);
        btn_pesan       = view.findViewById(R.id.btn_pesan_anak);
        btn_absen       = view.findViewById(R.id.btn_absen);
        btn_agenda      = view.findViewById(R.id.btn_agenda);
        btn_raport      = view.findViewById(R.id.btn_raport);
        btn_kalendar    = view.findViewById(R.id.btn_kalendar);

        btn_jadwal.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.apply();
            Intent intent = new Intent(getContext(), JadwalPelajaran.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            startActivity(intent);
        });

        btn_pesan.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("member_id",member_id);
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("fullname",nama_anak);
            editor.apply();
            Intent intent = new Intent(getContext(), PesanAnak.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code);
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id",classroom_id);
            intent.putExtra("fullname",nama_anak);
            startActivity(intent);
        });

        btn_agenda.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.apply();
            Intent intent = new Intent(getContext(), AgendaAnak.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            startActivity(intent);
        });

        btn_raport.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.putString("student_name",nama_anak);
            editor.apply();
            Intent intent = new Intent(getContext(), RaporAnak.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            intent.putExtra("student_name",nama_anak);
            startActivity(intent);
        });
        btn_kalendar.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.apply();
            Intent intent = new Intent(getContext(), KalendarKelas.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            startActivity(intent);
        });
        btn_absen.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.apply();
            Intent intent = new Intent(getContext(), AbsenAnak.class);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            startActivity(intent);
        });
        return view;
    }

}
