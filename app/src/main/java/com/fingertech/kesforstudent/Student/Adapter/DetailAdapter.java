package com.fingertech.kesforstudent.Student.Adapter;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Model.DetailModel;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder>{

    private List<DetailModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public DetailAdapter(List<DetailModel> viewItemList) {
        this.viewItemList = viewItemList;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nilai_rapor, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        DetailModel viewItem = viewItemList.get(position);
        holder.detail.setText(viewItem.getType_name());
        holder.nilai.setText(viewItem.getScore_exam());
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

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView detail, nilai;
        LinearLayout linearLayout;
        OnItemClickListener onItemClickListener;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            detail            = itemView.findViewById(R.id.detail_nilai);
            nilai             = itemView.findViewById(R.id.nilai_detail);
            linearLayout      = itemView.findViewById(R.id.ll_raport);
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