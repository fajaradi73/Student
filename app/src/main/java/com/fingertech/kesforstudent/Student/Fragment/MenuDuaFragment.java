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

import com.fingertech.kesforstudent.Student.Activity.JadwalUjian;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.fingertech.kesforstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDuaFragment extends Fragment {


    public MenuDuaFragment() {
        // Required empty public constructor
    }

    CardView btn_jadwalujian;
    SharedPreferences sharedPreferences;
    String authorization,school_code,member_id,classroom_id,nama_anak,edulevelid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        nama_anak           = sharedPreferences.getString("fullname",null);
        edulevelid          = sharedPreferences.getString("edulevelid",null);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dua, container, false);
        btn_jadwalujian = view.findViewById(R.id.btn_jadwalujian);


        btn_jadwalujian.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("school_code",school_code);
            editor.putString("authorization",authorization);
            editor.putString("classroom_id",classroom_id);
            editor.putString("member_id",member_id);
            editor.putString("edulevelid",edulevelid);
            editor.apply();
            Intent intent = new Intent(getContext(), JadwalUjian.class);
            intent.putExtra("edulevelid",edulevelid);
            intent.putExtra("authorization", authorization);
            intent.putExtra("school_code", school_code.toLowerCase());
            intent.putExtra("member_id", member_id);
            intent.putExtra("classroom_id", classroom_id);
            intent.putExtra("edulevelid",edulevelid);
            startActivity(intent);
        });
        return view;
    }

}
