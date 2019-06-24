package com.fingertech.kesforstudent.Guru.ActivityGuru.AdapterAbsen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsen;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAbsenGuru;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAttidudes extends RecyclerView.Adapter<AdapterAttidudes.MyHolder> {
    private List<ModelDataAttidude> modelDataAttidudes;
    SharedPreferences sharedPreferences;
    String authorization,school_code,member_id,codeattidude,attidudename,attidudegradecode,scyear_id;
    int statusattidude;
    List<ModelDataAttidude> modelDataAttidudeList = new ArrayList<>();
    ModelDataAttidude modelDataAttidude;
    Auth mApiInterface;
    Context context;
    AdapterCodeAbsen adapterCodeAbsen;
    private OnItemClickListener onItemClickListener;

    public AdapterAttidudes(Context context,List<ModelDataAttidude> viewItemList) {
        this.context = context;
        this.modelDataAttidudes = viewItemList;
    }

    public void setOnItemClickListener(AdapterAttidudes.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fragmentabsen, parent, false);
        mApiInterface               = ApiClient.getClient().create(Auth.class);
        authorization               ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImZhamFyYWRpcHJhc3RAZ21haWwuY29tIiwibWVtYmVyX2lkIjoiODUzIiwiZnVsbG5hbWUiOiJNb25hbGlzYSIsIm1lbWJlcl90eXBlIjoiMyJ9.GDytEt9XgLGPzAMUUyC5YkDSE378H2i-T-b-q8_w4U4";
        member_id                   ="777";
        scyear_id                   ="1";
        school_code                 ="bpk02";

        Call<JSONResponse.Attidude> Callat = mApiInterface.kes_attitude_get(authorization,school_code,member_id,scyear_id);
        Callat.enqueue(new Callback<JSONResponse.Attidude>() {
            @Override
            public void onResponse(Call<JSONResponse.Attidude> call, Response<JSONResponse.Attidude> response) {
                Log.d("cattidude",response.code()+"");
                modelDataAttidudes.clear();
                if (response.isSuccessful()){
                    JSONResponse.Attidude resourceattidude = response.body();
                    statusattidude = resourceattidude.statusattidude;
                    codeattidude   = resourceattidude.codeattidude;
                    if (statusattidude==1 && codeattidude.equals("DTS_SCS_0001")){
                        for (int at = 0; at<response.body().getDataattidude().size();at++){
                            attidudename = response.body().getDataattidude().get(at).getAttitude_grade_name();
                            modelDataAttidude = new ModelDataAttidude();
                            modelDataAttidude.setAttitude_name(attidudename);
                            modelDataAttidudes.add(modelDataAttidude);
                            Log.d("nama",attidudename.toString());
                            for (int i = 0; i < response.body().getDataattidude().get(at).getData().size();i++)
                            {
                                attidudegradecode = response.body().getDataattidude().get(at).getData().get(i).getAttitude_grade_code();

                            }
                        }
//                        AdapterAttidudes    adapterAttidudes    = new AdapterAttidudes(context,modelDataAttidudes);
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        rv_attidude.setLayoutManager(linearLayoutManager);
//                        rv_attidude.setAdapter(adapterAttidudes);
                    }

                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Attidude> call, Throwable t) {

            }
        });


        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelDataAttidude viewItem = modelDataAttidudes.get(position);
        holder.tv_attidude.setText(viewItem.getAttitude_name());


    }

    @Override
    public int getItemCount() {
        return modelDataAttidudes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_attidude;
        AdapterAttidudes.OnItemClickListener onItemClickListener;
        LinearLayout linearLayout;
        public MyHolder(View itemView, AdapterAttidudes.OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_attidude       = itemView.findViewById(R.id.tv_attidude);



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
