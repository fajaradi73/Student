package com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAtitude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAttendance;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterCodeAbsen extends RecyclerView.Adapter<AdapterCodeAbsen.MyHolder>  {
    private List<ModelAttendance> modelCodeAttidudes;
    Context context;
    int row_index;


    private OnItemClickListener onItemClickListener;

    public AdapterCodeAbsen(Context context,List<ModelAttendance> viewItemList) {
        this.context = context;
        this.modelCodeAttidudes = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public  MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_code_absen, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelAttendance viewItem = modelCodeAttidudes.get(position);

        holder.tv_code.setText(viewItem.getCodeabsen());
        holder.bgcolor.setBackgroundColor(Color.parseColor(viewItem.getWarna()));
    }

    @Override
    public int getItemCount() {
        if (modelCodeAttidudes.size() != 0)
            return modelCodeAttidudes.size();
        else return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_code;
        LinearLayout bgcolor;
        View view;

        OnItemClickListener onItemClickListener;

        MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_code       = itemView.findViewById(R.id.tv_code);
            bgcolor       = itemView.findViewById(R.id.ll_color);
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

    public void selectRow(int index){
        row_index=index;
        notifyDataSetChanged();
    }

}