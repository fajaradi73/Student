package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.ModelGuru.AgendaModelTanggal;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterDataTanggal extends RecyclerView.Adapter<AdapterDataTanggal.MyHolder> {

    private List<AgendaModelTanggal> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AdapterDataTanggal(List<AgendaModelTanggal> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_agenda_tanggal, parent, false);
        return new MyHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        AgendaModelTanggal viewItem = viewItemList.get(position);
        holder.tanggalagenda.setText(viewItem.getDate());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggalagenda;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggalagenda = itemView.findViewById(R.id.tanggalagendalayout);
//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
//            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}
