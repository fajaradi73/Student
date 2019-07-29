package com.fingertech.kesforstudent.Student.Adapter;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Student.Model.PesanModel;
import com.fingertech.kesforstudent.R;
import com.github.florent37.shapeofview.shapes.CircleView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fingertech.kesforstudent.Service.App.getContext;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.MyHolder> {

    private List<PesanModel> viewItemList;
    Date date_now,date_pesan;

    private OnItemClickListener onItemClickListener;
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public int row_index = 0;
    public PesanAdapter(List<PesanModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pesan_anak, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        PesanModel viewItem = viewItemList.get(position);
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
        if (times_pesan.equals(times_now)){
            holder.tanggal.setText(convertjam(viewItem.getJam()));
        }else {
            holder.tanggal.setText(convertTanggal(viewItem.getTanggal()));
        }

        if (viewItem.getRead_status().equals("0")){
            holder.title.setTextColor(Color.BLACK);
            holder.title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.pengirim.setTextColor(Color.BLACK);
            holder.pengirim.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.tanggal.setTextColor(Color.BLACK);
            holder.status.setBackground(getContext().getResources().getDrawable(R.drawable.ic_hide));
        }else if (viewItem.getRead_status().equals("1")){
            holder.status.setBackground(getContext().getResources().getDrawable(R.drawable.ic_view));
        }

        holder.pengirim.setText(viewItem.getDari());
        if (viewItem.getTitle().equals("")){
            holder.title.setText("( Tidak ada subject )");
        }else {
            holder.title.setText(viewItem.getTitle());
        }
        holder.pengirim.setText(viewItem.getDari());
        if (viewItem.getPicture().equals(ApiClient.BASE_IMAGE)) {
            Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + viewItem.getDari() + "&background=1de9b6&color=fff&font-size=0.40&length=1").into(holder.imageView);
        }else {
            Glide.with(getContext()).load(viewItem.getPicture()).into(holder.imageView);
        }
        holder.pesan.setText(viewItem.getPesan());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    public List<PesanModel> getItems() {
        return viewItemList;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pengirim,pesan,title,tanggal;
        CircleView circleView;
        ImageView imageView,status;
        LinearLayout linearLayout;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.Tv_tanggal);
            pengirim    = itemView.findViewById(R.id.Tvpengirim);
            pesan       = itemView.findViewById(R.id.Tvpesan);
            title       = itemView.findViewById(R.id.Tvsubject);
            circleView  = itemView.findViewById(R.id.profilanak);
            imageView   = itemView.findViewById(R.id.image_guru);
            status      = itemView.findViewById(R.id.image_pesan);
            linearLayout    = itemView.findViewById(R.id.ll_pesan);
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

    String convertjam(String tanggal){
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
    String convertTanggal(String tanggal){
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