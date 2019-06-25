package com.fingertech.kesforstudent.Guru.ActivityGuru.AdapterAbsen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCodeAbsen extends RecyclerView.Adapter<AdapterCodeAbsen.MyHolder>  {
    private List<ModelDataAttidude> modelDataAttidudes;
    Context context;
    int statusattidude;
    String authorization,school_code,member_id,codeattidude,attidudename,attidudegradecode,scyear_id;
    Auth mApiInterface;

    private OnItemClickListener onItemClickListener;

    public AdapterCodeAbsen(Context context,List<ModelDataAttidude> viewItemList) {
        this.context = context;
        this.modelDataAttidudes = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public  MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_code, parent, false);

        AdapterCodeAbsen.MyHolder myHolder = new AdapterCodeAbsen.MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelDataAttidude viewItem = modelDataAttidudes.get(position);
        holder.tv_code.setText(viewItem.getAttitude_grade_code());
        Log.d("apaaja",viewItem.getAttitude_grade_code()+"");

    }

    @Override
    public int getItemCount() {
        return modelDataAttidudes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_code;

        OnItemClickListener onItemClickListener;


        public MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_code       = itemView.findViewById(R.id.tv_code);


//            btn_absen.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
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
