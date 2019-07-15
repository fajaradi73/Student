package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.ActivityGuru.LihatFile;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelSilabus;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterSilabus extends RecyclerView.Adapter<AdapterSilabus.MyHolder> {

    private List<ModelSilabus> viewItemList;

    Context context;
    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AdapterSilabus(Context context,List<ModelSilabus> viewItemList) {
        this.context = context;
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_silabus, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelSilabus viewItem = viewItemList.get(position);
        holder.mapel.setText("Mata Pelajaran "+ viewItem.getMapel());
        holder.kelas.setText(viewItem.getKelas());
        holder.dates.setText(viewItem.getTanggal());
        holder.cv_unduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(context, LihatFile.class);
                intent.putExtra("file",viewItem.getFile());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mapel,kelas,dates;
        OnItemClickListener onItemClickListener;
        CardView cv_unduh;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            mapel       = itemView.findViewById(R.id.mapel);
            kelas       = itemView.findViewById(R.id.kelas_upload);
            dates       = itemView.findViewById(R.id.tanggal_upload);
            cv_unduh    = itemView.findViewById(R.id.btn_unduh);
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