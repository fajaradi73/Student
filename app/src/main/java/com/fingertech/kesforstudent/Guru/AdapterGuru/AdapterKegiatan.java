package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Guru.ModelGuru.ModelKegiatan;
import com.fingertech.kesforstudent.R;

import java.util.List;

public class AdapterKegiatan extends RecyclerView.Adapter<AdapterKegiatan.MyHolder> {

    private List<ModelKegiatan> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AdapterKegiatan(List<ModelKegiatan> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_kegiatan, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ModelKegiatan viewItem = viewItemList.get(position);
        holder.textView.setText(viewItem.getText());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            textView    = itemView.findViewById(R.id.text);
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