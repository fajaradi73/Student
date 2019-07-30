package com.fingertech.kesforstudent;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.MyHolder> {

    private List<NotifikasiModel> viewItemList;
    private Context context;
    public int row_index = 0;
    private OnItemClickListener onItemClickListener;

    public NotifikasiAdapter(List<NotifikasiModel> viewItemList,Context context) {
        this.viewItemList = viewItemList;
        this.context    = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notifikasi, parent, false);
        return new MyHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        NotifikasiModel viewItem = viewItemList.get(position);
        holder.jam_mulai.setText(viewItem.getTitle());
        holder.jam_selesai.setText(viewItem.getBody());
        holder.jam_selesai.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.jam_selesai.setSelected(true);
        switch (viewItem.getType()) {
            case "pesan_siswa":
                Glide.with(context).load(R.drawable.ic_pesan).into(holder.imageView);
                break;
            case "insert_new_agenda":
                Glide.with(context).load(R.drawable.ic_agenda).into(holder.imageView);
                break;
            case "absen_siswa":
                Glide.with(context).load(R.drawable.ic_absen).into(holder.imageView);
                break;
            case "update_data_siswa":
                Glide.with(context).load(R.drawable.ic_profil).into(holder.imageView);
                break;
            case "insert_new_exam":
                Glide.with(context).load(R.drawable.ic_jadwal_ujian).into(holder.imageView);
                break;
            case "pesan_guru":
                Glide.with(context).load(R.drawable.ic_pesan).into(holder.imageView);
                break;
        }
        if (viewItem.getStatus().equals("0")){
            holder.star.setVisibility(View.VISIBLE);
            holder.linearLayout.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }else {
            holder.star.setVisibility(View.GONE);
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    public List<NotifikasiModel> getItems() {
        return viewItemList;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView jam_mulai,jam_selesai;
        ImageView imageView,star;
        LinearLayout linearLayout;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            jam_mulai       = itemView.findViewById(R.id.tv_title);
            jam_selesai     = itemView.findViewById(R.id.tv_body);
            imageView       = itemView.findViewById(R.id.image_notifikasi);
            star            = itemView.findViewById(R.id.star);
            linearLayout    = itemView.findViewById(R.id.ll_notif);
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