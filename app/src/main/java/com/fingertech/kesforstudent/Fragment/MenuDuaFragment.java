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

import com.fingertech.kesforstudent.Activity.MenuUtama;
import com.fingertech.kesforstudent.Activity.PesanAnak;
import com.fingertech.kesforstudent.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDuaFragment extends Fragment {


    public MenuDuaFragment() {
        // Required empty public constructor
    }

    CardView btn_pesan;
    SharedPreferences sharedPreferences;
    String authorization,school_code,member_id,classroom_id,nama_anak;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dua, container, false);
        btn_pesan   = view.findViewById(R.id.btn_pesan);

        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        nama_anak           = sharedPreferences.getString("fullname",null);

        btn_pesan.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && member_id != null) {
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
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
