package com.fingertech.kesforstudent.Student.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kesforstudent.Student.Model.RaporModel;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class RaportAdapter extends RecyclerView.Adapter<RaportAdapter.MyHolder> {

    private List<RaporModel> viewItemList;

    public int row_index = 0;
    public RaportAdapter(List<RaporModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_raport, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        RaporModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.teori.setText(viewItem.getTeori());;
        holder.mapel.setText(viewItem.getMapel());
        holder.ulangan_harian.setText(viewItem.getUlangan_harian());
        holder.ujian_negara.setText(viewItem.getUjian_negara());
        holder.ujian_sekolah.setText(viewItem.getUjian_sekolah());
        holder.eskul.setText(viewItem.getEskul());
        holder.praktikum.setText(viewItem.getPraktikum());
        holder.nilai_akhir.setText(viewItem.getNilai_akhir());
        holder.rata_rata.setText(viewItem.getRata_rata());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView teori,ulangan_harian,praktikum,eskul,ujian_sekolah,ujian_negara,mapel,nilai_akhir,rata_rata;

        public MyHolder(View itemView) {
            super(itemView);
            teori           = (TextView) itemView.findViewById(R.id.nilai_teori);
            ulangan_harian  = (TextView) itemView.findViewById(R.id.ulangan_harian);
            praktikum       = (TextView) itemView.findViewById(R.id.latihan_praktikum);
            eskul           = (TextView) itemView.findViewById(R.id.eskul);
            ujian_sekolah   = (TextView) itemView.findViewById(R.id.ujian_sekolah);
            ujian_negara    = (TextView) itemView.findViewById(R.id.ujian_negara);
            mapel           = (TextView) itemView.findViewById(R.id.mapel_raport);
            rata_rata       = (TextView) itemView.findViewById(R.id.rata_rata);
            nilai_akhir     = (TextView) itemView.findViewById(R.id.nilai_akhir);

        }

    }

}