package com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.ActivityGuru.AbsenMurid;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAtitude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDetailAbsen;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AdapterDetailAbsen extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ModelDetailAbsen> modelDetailAbsenList;
    private List<ModelAtitude> modelAtitudeList;
    private AbsenMurid absenMurid;
    private CardView btn_next,btn_back,btn_simpan;
    private ViewPager viewPager;
    private RecyclerView rv_attidude;
    private TextView namaanak,nis;

    private View views;

    public AdapterDetailAbsen(Context context, List<ModelDetailAbsen> viewItemlist,ViewPager viewPager,List<ModelAtitude> modelAtitudeList,View views) {
        this.context            = context;
        this.modelDetailAbsenList = viewItemlist;
        this.viewPager          = viewPager;
        this.modelAtitudeList   = modelAtitudeList;
        this.views              = views;
    }
    @Override
    public int getCount() {
        return modelDetailAbsenList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, Object object) {
        return (view==object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater            = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view           = inflater.inflate(R.layout.fragments_absen,container,false);
        namaanak            = view.findViewById(R.id.tv_nama);
        nis                 = view.findViewById(R.id.tv_nis);
        rv_attidude         = view.findViewById(R.id.rv_absen);
        btn_back            = views.findViewById(R.id.btnback);
        btn_next            = views.findViewById(R.id.btnnext);
        btn_simpan          = views.findViewById(R.id.btn_simpan);

        ModelDetailAbsen viewitem = modelDetailAbsenList.get(position);
        namaanak.setText(viewitem.getNama());
        nis.setText(viewitem.getNis());
        AdapterAttidudes adapterAttidudes = new AdapterAttidudes(context,viewitem.getModelDataAttidudeList(),modelAtitudeList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_attidude.setLayoutManager(layoutManager);
        rv_attidude.setAdapter(adapterAttidudes);

        container.addView(view);
        if (viewPager.getCurrentItem() == 0){
            btn_back.setVisibility(View.INVISIBLE);
        }else if (position == modelDetailAbsenList.size()-1){
            btn_back.setVisibility(View.GONE);
            btn_next.setVisibility(View.GONE);
            btn_simpan.setVisibility(View.VISIBLE);
        }else {
            btn_simpan.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_back.setVisibility(View.VISIBLE);
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
            }
        });


        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


}