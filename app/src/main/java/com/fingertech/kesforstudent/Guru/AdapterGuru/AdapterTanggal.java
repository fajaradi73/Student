package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.ModelGuru.ModelTanggal;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterTanggal extends RecyclerView.Adapter<AdapterTanggal.MyHolder> {

    private List<ModelTanggal> viewItemList;

    Context context;
    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AdapterTanggal(Context context,List<ModelTanggal> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tanggal_penilaian, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelTanggal viewItem = viewItemList.get(position);
        holder.nilai.setText(viewItem.getTanggal());
        if (row_index == position){
            holder.linearLayout.setBackgroundResource(R.drawable.rectang_line_yellow);
        }else {
            holder.linearLayout.setBackgroundResource(R.drawable.rectang_line_grey);
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nilai;
        LinearLayout linearLayout;
        OnItemClickListener onItemClickListener;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            nilai       = itemView.findViewById(R.id.tanggal);
            linearLayout    = itemView.findViewById(R.id.ll_tanggal);
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

    public void select_row(int index){
        row_index = index;
        notifyDataSetChanged();
    }
}