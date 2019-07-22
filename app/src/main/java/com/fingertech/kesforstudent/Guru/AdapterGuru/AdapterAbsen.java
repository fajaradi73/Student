package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi.AdapterCodeAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAbsenGuru;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAttendance;
import com.fingertech.kesforstudent.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterAbsen extends RecyclerView.Adapter<AdapterAbsen.MyHolder> {

    private List<ModelAbsenGuru> modelAbsenGuruList;
    private List<ModelAttendance> modelArrayAbsenList = new ArrayList<>();
    ModelAttendance modelAttendance;

    Context context;
    private OnItemClickListener onItemClickListener;
    AdapterCodeAbsen adapterCodeAbsen;


    public AdapterAbsen(Context context,List<ModelAbsenGuru> viewItemList) {
        this.context                = context;
        this.modelAbsenGuruList     = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_murid, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelAbsenGuru viewItem = modelAbsenGuruList.get(position);
        holder.nama.setText(viewItem.getNama());
        if (modelArrayAbsenList != null){
            modelArrayAbsenList.clear();
        }
        for (int i = 0 ; i < viewItem.getModelArrayAbsenList().size();i++){
            if (viewItem.getModelArrayAbsenList().get(i).getNis().equals(viewItem.getNis())){
                modelAttendance = new ModelAttendance();
                modelAttendance.setCodeabsen(viewItem.getModelArrayAbsenList().get(i).getCodeabsen());
                modelAttendance.setWarna(viewItem.getModelArrayAbsenList().get(i).getWarna());
                modelArrayAbsenList.add(modelAttendance);
            }
        }
        adapterCodeAbsen = new AdapterCodeAbsen(context,modelArrayAbsenList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(adapterCodeAbsen);
    }

    @Override
    public int getItemCount() {
        return modelAbsenGuruList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama,nis,nilai;
        RecyclerView recyclerView;
        OnItemClickListener onItemClickListener;
        LinearLayout linearLayout;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            nama       = itemView.findViewById(R.id.tv_nama_murid);
            recyclerView   = itemView.findViewById(R.id.rv_hasil_absen);
            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}