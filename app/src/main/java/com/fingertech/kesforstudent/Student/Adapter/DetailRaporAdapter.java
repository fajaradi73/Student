package com.fingertech.kesforstudent.Student.Adapter;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Model.DetailRaporModel;

import java.util.List;

public class DetailRaporAdapter extends RecyclerView.Adapter<DetailRaporAdapter.MyHolder>{

    private List<DetailRaporModel> viewItemList;

    public int row_index = 0;
    public DetailRaporAdapter(List<DetailRaporModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detail_raport, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        DetailRaporModel viewItem = viewItemList.get(position);
        holder.kkm.setText(viewItem.getKkm());
        holder.nilai.setText(viewItem.getNilai());
        holder.rr_kelas.setText(viewItem.getRatarata());
        if ((position % 2) == 0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F4F8F4"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // Set car item title.
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView kkm, nilai,rr_kelas;
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            kkm             = itemView.findViewById(R.id.kkm);
            nilai           = itemView.findViewById(R.id.nilai);
            rr_kelas        = itemView.findViewById(R.id.rr_kelas);
            linearLayout    = itemView.findViewById(R.id.ll_raport);
//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
        }

    }
}