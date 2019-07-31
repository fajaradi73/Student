package com.fingertech.kesforstudent.Student.Adapter.HariAdapter;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Student.Model.HariModel.JadwalRabu;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class RabuAdapter extends RecyclerView.Adapter<RabuAdapter.MyHolder> {

    private List<JadwalRabu> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public RabuAdapter(List<JadwalRabu> viewItemList,String type) {
        this.viewItemList = viewItemList;
        this.type   = type;
    }

    private String type;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

    }
    private View itemView;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (type.equals("menu")) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jadwal_harian, parent, false);
            return new MyHolder(itemView,onItemClickListener);
        }else{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jadwal, parent, false);
            return new MyHolder(itemView,onItemClickListener);
        }
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        JadwalRabu viewItem = viewItemList.get(position);
        // Set car item title.
        holder.mapel.setText(viewItem.getCources_name());
        holder.jambel.setText(viewItem.getJam_mulai() +" - "+ viewItem.getJam_selesai());
        holder.guru.setText(viewItem.getFullname());
        holder.ll_jadwal.setBackgroundColor(Color.parseColor(viewItem.getCources_color()));
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mapel,jambel,guru;
        LinearLayout ll_jadwal;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            mapel       = itemView.findViewById(R.id.mapel);
            jambel      = itemView.findViewById(R.id.jam);
            guru        = itemView.findViewById(R.id.guru);
            ll_jadwal   = itemView.findViewById(R.id.ll_jadwal);
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