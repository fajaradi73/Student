package com.fingertech.kesforstudent.Student.Adapter;

import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.Student.Model.AbsenModel;
import com.fingertech.kesforstudent.Student.Model.AbsensiModel;
import com.fingertech.kesforstudent.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kesforstudent.Service.App.getContext;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.MyHolder> {

    private List<AbsensiModel> viewItemList;
    private List<AbsenModel> absenModels;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private Date date,date_now,date_mulai,date_selesai;
    private SimpleDateFormat jamformat      = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private DateFormat times_format         = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public AbsensiAdapter(List<AbsensiModel> viewItemList,List<AbsenModel> absenModels) {
        this.viewItemList = viewItemList;
        this.absenModels = absenModels;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removedata(){
        this.viewItemList.clear();
        notifyDataSetChanged();
    }
    public void updateData(List<AbsensiModel> viewModels) {
        this.viewItemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_absensi, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        AbsensiModel viewItem = viewItemList.get(position);
        AbsenModel absenModel = absenModels.get(position);
        String times_ok = absenModel.getTimez_star();

        holder.mapel.setText(viewItem.getMapel());
        holder.guru.setText("Guru "+viewItem.getGuru());
        holder.times.setText("Jam "+viewItem.getTimez_star() + " - "+viewItem.getTimez_finish());
        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        // Set car item title.
        String jam_sekarang = jamformat.format(Calendar.getInstance().getTime());
        try {
            date_now    = times_format.parse(tanggal+" "+ jam_sekarang +":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_now = date_now.getTime();
        try {
            date = times_format.parse(absenModel.getTanggal()+" "+viewItem.getTimez_star()+":00");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_start = date.getTime();

        try {
            date_mulai = times_format.parse(absenModel.getTanggal()+" "+absenModel.getTimez_star()+":00");
        }catch (ParseException e){
            e.printStackTrace();
        }
        Long times_mulai = date_mulai.getTime();

        switch (absenModel.getDay_id()) {
            case "0":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=!&background=FFDE17&color=000&length=1").into(holder.image_absen);
                break;
            case "1":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Dikeluarkan&background=317FA1&color=fff&length=1").into(holder.image_absen);
                break;
            case "2":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Telat&background=2C3039&color=fff&length=1").into(holder.image_absen);
                break;
            case "3":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Izin&background=EFE138&color=000&length=1").into(holder.image_absen);
                break;
            case "4":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Sakit&background=1FA3DE&color=fff&length=1").into(holder.image_absen);
                break;
            case "5":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Alpa&background=CF1D35&color=fff&length=1").into(holder.image_absen);
                break;
            case "6":
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Hadir&background=B6F883&color=000&length=1").into(holder.image_absen);
                break;
            default:
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=!&background=FFDE17&color=000&length=1").into(holder.image_absen);
                break;
        }

//        if (times_start.equals(times_mulai)){
//            switch (absenModel.getDay_id()) {
//                case "0":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=!&background=FFDE17&color=000&length=1").into(holder.image_absen);
//                    break;
//                case "1":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Dikeluarkan&background=317FA1&color=fff&length=1").into(holder.image_absen);
//                    break;
//                case "2":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Telat&background=2C3039&color=fff&length=1").into(holder.image_absen);
//                    break;
//                case "3":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Izin&background=EFE138&color=000&length=1").into(holder.image_absen);
//                    break;
//                case "4":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Sakit&background=1FA3DE&color=fff&length=1").into(holder.image_absen);
//                    break;
//                case "5":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Alpa&background=CF1D35&color=fff&length=1").into(holder.image_absen);
//                    break;
//                case "6":
//                    Glide.with(getContext()).load("https://ui-avatars.com/api/?name=Hadir&background=B6F883&color=000&length=1").into(holder.image_absen);
//                    break;
//            }
//        }else {
//            Glide.with(getContext()).load("https://ui-avatars.com/api/?name=!&background=FFDE17&color=000&length=1").into(holder.image_absen);
//        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView times,mapel,guru;
        OnItemClickListener onItemClickListener;
        ImageView image_absen;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            times       = itemView.findViewById(R.id.jam_absen);
            image_absen = itemView.findViewById(R.id.image_absen);
            guru        = itemView.findViewById(R.id.guru_absen);
            mapel       = itemView.findViewById(R.id.mapel_absen);
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