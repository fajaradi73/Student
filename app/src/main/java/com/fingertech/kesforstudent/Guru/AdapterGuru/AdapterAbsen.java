package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAbsenGuru;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterAbsen extends RecyclerView.Adapter<AdapterAbsen.MyHolder> {

    private List<ModelAbsenGuru> modelAbsenGuruList;

    Context context;
    private OnItemClickListener onItemClickListener;

    public AdapterAbsen(Context context,List<ModelAbsenGuru> viewItemList) {
        this.context = context;
        this.modelAbsenGuruList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_murid, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelAbsenGuru viewItem = modelAbsenGuruList.get(position);
        holder.nama.setText(viewItem.getNama());

    }

    @Override
    public int getItemCount() {
        return modelAbsenGuruList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama,nis,nilai,btn_absen;
        OnItemClickListener onItemClickListener;
        LinearLayout linearLayout;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            nama       = itemView.findViewById(R.id.tv_nama_murid);
            btn_absen  = itemView.findViewById(R.id.arrow_absen);

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