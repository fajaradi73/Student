package com.fingertech.kesforstudent.Student.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Model.AgendaTanggalModel;

import java.util.List;

public class AgendaDataTanggal extends RecyclerView.Adapter<AgendaDataTanggal.MyHolder> {


    private List<AgendaTanggalModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AgendaDataTanggal(List<AgendaTanggalModel> viewItemList) {
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
    public void onBindViewHolder(@NonNull AgendaDataTanggal.MyHolder holder, int position) {

        AgendaTanggalModel viewItem = viewItemList.get(position);
        holder.tanggalagenda.setText(viewItem.getDate());
        holder.hariagenda.setText(viewItem.getHari());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggalagenda,hariagenda;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggalagenda   = itemView.findViewById(R.id.tanggalagendalayout);
            hariagenda      = itemView.findViewById(R.id.hari_agenda);
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