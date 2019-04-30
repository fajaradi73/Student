package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelPesanGuru;
import com.fingertech.kesforstudent.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fingertech.kesforstudent.Service.App.getContext;

public class AdapterPesanGuru extends RecyclerView.Adapter<AdapterPesanGuru.MyHolder> {

    private List<ModelPesanGuru> viewItemList;
    Date date_now,date_pesan;

    private OnItemClickListener onItemClickListener;
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public int row_index = 0;
    public AdapterPesanGuru(List<ModelPesanGuru> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pesan, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        ModelPesanGuru viewItem = viewItemList.get(position);
        // Set car item title.

        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        // Set car item title.
        try {
            date_now    = times_format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_now = date_now.getTime();

        try {
            date_pesan = times_format.parse(viewItem.getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_pesan = date_pesan.getTime();

        if (viewItem.getRead_status().equals("0")){
            holder.imageView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_hide));
        }else if (viewItem.getRead_status().equals("1")){
            holder.imageView.setBackground(getContext().getResources().getDrawable(R.drawable.ic_view));
        }

        if (times_pesan.equals(times_now)){
            holder.jam.setText(convertjam(viewItem.getJam()));
        }else {
            holder.jam.setText(convertTanggal(viewItem.getTanggal()));
        }

        holder.pengirim.setText(viewItem.getDari());
        if (viewItem.getTitle().equals("")){
            holder.subject.setText("( Tidak ada subject )");
        }else {
            holder.subject.setText(viewItem.getTitle());
        }
        holder.isi_pesan.setText(viewItem.getPesan());
        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + viewItem.getDari()+"&background=1de9b6&color=fff&font-size=0.40&length=1").into(holder.image_pesan);
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView jam,pengirim,subject,isi_pesan;
        CircleImageView image_pesan;
        OnItemClickListener onItemClickListener;
        ImageView imageView;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            jam           = itemView.findViewById(R.id.jam);
            pengirim      = itemView.findViewById(R.id.pengirim_pesan);
            subject       = itemView.findViewById(R.id.subject);
            isi_pesan     = itemView.findViewById(R.id.isi_pesan);
            image_pesan   = itemView.findViewById(R.id.image_pesan);
            imageView     = itemView.findViewById(R.id.image_status);
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
    String convertDate(String date) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(date));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String convertjam(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}