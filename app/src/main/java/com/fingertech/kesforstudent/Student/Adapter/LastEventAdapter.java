package com.fingertech.kesforstudent.Student.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Model.LastEventModel;
import java.util.List;

public class LastEventAdapter extends RecyclerView.Adapter<LastEventAdapter.MyHolder> {

    private List<LastEventModel> viewItemList;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;

    public LastEventAdapter(List<LastEventModel> viewItemList,Context context) {
        this.viewItemList = viewItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_last_event, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        LastEventModel viewItem = viewItemList.get(position);
        holder.tanggal.setText(viewItem.getTanggal());
        holder.bulan.setText(viewItem.getBulan());
        holder.nama.setText(viewItem.getNama());
        holder.tahun.setText(viewItem.getTahun());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama,bulan,tanggal,tahun;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            nama        = itemView.findViewById(R.id.tv_event);
            bulan       = itemView.findViewById(R.id.tv_bulan);
            tanggal     = itemView.findViewById(R.id.tv_tanggal);
            tahun       = itemView.findViewById(R.id.tv_tahun);
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