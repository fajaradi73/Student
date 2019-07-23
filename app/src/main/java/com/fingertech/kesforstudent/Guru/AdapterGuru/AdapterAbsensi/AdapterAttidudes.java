package com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.CustomView.OnItemClickListener;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelAtitude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAbsen.ModelDataAttidude;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAttendance;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAttidudes extends RecyclerView.Adapter<AdapterAttidudes.MyHolder> {
    private List<ModelDataAttidude> modelDataAttidudes;
    private List<ModelAttendance> modelAttendanceList;
    private List<ModelAtitude> modelAtitudeList;
    private ModelAttendance modelAttendance;
    private Context context;
    AdapterCode adapterCodeAbsen;
    private OnItemClickListener onItemClickListener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    private String[] namaabsen = new String[]{"H","A","I","S","T","D"};
    private String[] colorabsen = new String[]{"#A2FB5E","#CF2138","#EFE138","#36B2E9","#2C3039","#529FBF"};
    JSONObject myJsonObject;

    AdapterAttidudes(Context context, List<ModelDataAttidude> viewItemList,List<ModelAtitude> modelAtitudeList) {
        this.context = context;
        this.modelDataAttidudes = viewItemList;
        this.modelAtitudeList   = modelAtitudeList;
        this.viewPool = new RecyclerView.RecycledViewPool();

    }

    public void setOnItemClickListener(AdapterAttidudes.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fragmentabsen, parent, false);
        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelDataAttidude viewItem = modelDataAttidudes.get(position);
        holder.tv_attidude.setText(viewItem.getAttitude_name());
        holder.rv_code.setRecycledViewPool(viewPool);
        if (position == 0) {
            modelAttendanceList = new ArrayList<>();
            for (int o = 0; o < namaabsen.length; o++) {
                modelAttendance = new ModelAttendance();
                modelAttendance.setId("0");
                modelAttendance.setCodeabsen(namaabsen[o]);
                modelAttendance.setWarna(colorabsen[o]);
                modelAttendanceList.add(modelAttendance);
            }
        } else {
            modelAttendanceList = new ArrayList<>();
            for (int i = 0; i < modelAtitudeList.size(); i++) {
                String grade_code   = modelAtitudeList.get(i).getNama();
                String color        = viewItem.getColour_code();
                String id           = modelAtitudeList.get(i).getId_atitude();
                modelAttendance = new ModelAttendance();
                modelAttendance.setId(id);
                modelAttendance.setWarna(color);
                modelAttendance.setCodeabsen(grade_code);
                modelAttendanceList.add(modelAttendance);
            }
        }

        adapterCodeAbsen = new AdapterCode(context,modelAttendanceList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.rv_code.setHasFixedSize(true);
        holder.rv_code.setLayoutManager(layoutManager);
        holder.rv_code.setAdapter(adapterCodeAbsen);
        adapterCodeAbsen.onAttachedToRecyclerView(holder.rv_code);
    }

    @Override
    public int getItemCount() {
        return modelDataAttidudes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_attidude;
        RecyclerView rv_code;
        OnItemClickListener onItemClickListener;
        LinearLayout linearLayout;
        MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_attidude       = itemView.findViewById(R.id.tv_attidude);
            rv_code           = itemView.findViewById(R.id.rv_code);

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
