package com.fingertech.kesforstudent.Student.Adapter;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Model.AgendaModel;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyHolder> {

    private List<AgendaModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AgendaAdapter(List<AgendaModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_agenda, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        AgendaModel viewItem = viewItemList.get(position);
        holder.tvjudul.setText(viewItem.getType());
        holder.tv_title.setText(viewItem.getDesc());
        holder.tvdeskripsi.setText(viewItem.getContent());
        if (viewItem.getColour().equals("")){
            holder.pitaku.setColorFilter(Color.parseColor("#ff9800"));
        }else {
            holder.pitaku.setColorFilter(Color.parseColor(viewItem.getColour()));
        }

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title,tvdeskripsi,tvjudul;
        ImageView pitaku;
        LinearLayout ll_agenda;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_title           = itemView.findViewById(R.id.titleagenda);
            tvdeskripsi        = itemView.findViewById(R.id.deskripsiagenda);
            tvjudul            = itemView.findViewById(R.id.judulagenda);
            pitaku             = itemView.findViewById(R.id.pita);


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