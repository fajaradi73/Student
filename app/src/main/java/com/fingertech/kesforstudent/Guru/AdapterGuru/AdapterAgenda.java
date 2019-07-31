package com.fingertech.kesforstudent.Guru.AdapterGuru;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.ActivityGuru.TambahAgenda;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAgenda;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAgenda extends RecyclerView.Adapter<AdapterAgenda.MyHolder> {

    private List<ModelAgenda> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private Context context;
    private OnImageClickListener onImageClickListener;

    public AdapterAgenda(Context context,List<ModelAgenda> viewItemList, Auth mApiInterface,String authorization,String member_id, String school_code,OnImageClickListener onImageClickListener) {
        this.viewItemList           = viewItemList;
        this.mApiInterface          = mApiInterface;
        this.authorization          = authorization;
        this.school_code            = school_code;
        this.member_id              = member_id;
        this.context                = context;
        this.onImageClickListener   = onImageClickListener;
    }

    private String authorization,member_id,school_code,id,code,tanggal,description;
    private int status;
    private Auth mApiInterface;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_agenda_guru, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        ModelAgenda viewItem = viewItemList.get(position);
        holder.tvjudul.setText(viewItem.getType());
        holder.tv_title.setText(viewItem.getDesc());
        holder.tvdeskripsi.setText(viewItem.getContent());
        if (viewItem.getColour().equals("")){
            holder.pitaku.setColorFilter(Color.parseColor("#ff9800"));
        }else {
            holder.pitaku.setColorFilter(Color.parseColor(viewItem.getColour()));
        }
        if (viewItem.getType_data().equals("tugas") || viewItem.getType_data().equals("agenda")){
            holder.ll_agenda.setVisibility(View.VISIBLE);
        }else {
            holder.ll_agenda.setVisibility(View.GONE);
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
        CardView edit,hapus;

        MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_title            = itemView.findViewById(R.id.titleagenda);
            tvdeskripsi         = itemView.findViewById(R.id.deskripsiagenda);
            tvjudul             = itemView.findViewById(R.id.judulagenda);
            pitaku              = itemView.findViewById(R.id.pita);
            edit                = itemView.findViewById(R.id.btn_edit);
            hapus               = itemView.findViewById(R.id.btn_hapus);
            ll_agenda           = itemView.findViewById(R.id.ll_edit);
            itemView.setOnClickListener(this);
            hapus.setOnClickListener(this);
            edit.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (v.equals(hapus)){
                if (viewItemList.get(getAdapterPosition()).getType_data().equals("agenda")) {
                    id  = viewItemList.get(getAdapterPosition()).getId();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
                    builder.setTitle("Agenda");
                    builder.setMessage("Apakah anda ingin menghapus agenda ini?");
                    builder.setIcon(R.drawable.ic_info_kes);
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete_agenda(getAdapterPosition());
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else if (viewItemList.get(getAdapterPosition()).getType_data().equals("tugas")){
                    id  = viewItemList.get(getAdapterPosition()).getId();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
                    builder.setTitle("Agenda");
                    builder.setMessage("Apakah anda ingin menghapus tugas ini?");
                    builder.setIcon(R.drawable.ic_info_kes);
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete_tugas(getAdapterPosition());
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }if (v.equals(edit)){
                if (viewItemList.get(getAdapterPosition()).getType_data().equals("agenda")) {
                    id          = viewItemList.get(getAdapterPosition()).getId();
                    description = viewItemList.get(getAdapterPosition()).getContent();
                    tanggal     = viewItemList.get(getAdapterPosition()).getDate();
                    Intent intent   = new Intent(context, TambahAgenda.class);
                    intent.putExtra("agenda_id",id);
                    intent.putExtra("agenda_date",tanggal);
                    intent.putExtra("agenda_desc",description);
                    intent.putExtra("edit","edit");
                    ((Activity) context).startActivityForResult(intent,1);
                }else if (viewItemList.get(getAdapterPosition()).getType_data().equals("tugas")){
                    id  = viewItemList.get(getAdapterPosition()).getId();
                }
            }
            else {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private void delete_agenda(int position){
        Call<JSONResponse.DeleteAgenda> call = mApiInterface.kes_delete_agenda_post(authorization,school_code.toLowerCase(),member_id,id);
        call.enqueue(new Callback<JSONResponse.DeleteAgenda>() {
            @Override
            public void onResponse(Call<JSONResponse.DeleteAgenda> call, Response<JSONResponse.DeleteAgenda> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()) {
                    if (response.body() != null){
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            FancyToast.makeText(context,"Sukses menghapus agenda", Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                            removeAt(position);
                            onImageClickListener.onImageClick("change");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.DeleteAgenda> call, Throwable t) {
                Log.e("gagal",t.toString());
            }
        });
    }

    private void removeAt(int position) {
        viewItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, viewItemList.size());
    }

    public interface OnImageClickListener {
        void onImageClick(String change);
    }

    private void delete_tugas(int position){
        Call<JSONResponse.DeleteTugas> call = mApiInterface.kes_delete_exercises_post(authorization,school_code.toLowerCase(),member_id,id);
        call.enqueue(new Callback<JSONResponse.DeleteTugas>() {
            @Override
            public void onResponse(Call<JSONResponse.DeleteTugas> call, Response<JSONResponse.DeleteTugas> response) {
                Log.d("Sukses",response.code()+"");
                if (response.isSuccessful()) {
                    if (response.body() != null){
                        status  = response.body().status;
                        code    = response.body().code;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            FancyToast.makeText(context,"Sukses menghapus Tugas", Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                            removeAt(position);
                            onImageClickListener.onImageClick("change");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.DeleteTugas> call, Throwable t) {
                Log.e("gagal",t.toString());
            }
        });
    }
}