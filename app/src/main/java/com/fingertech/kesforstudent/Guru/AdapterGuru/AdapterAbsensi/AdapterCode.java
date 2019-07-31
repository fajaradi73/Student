package com.fingertech.kesforstudent.Guru.AdapterGuru.AdapterAbsensi;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fingertech.kesforstudent.CustomView.OnItemClickListener;
import com.fingertech.kesforstudent.Guru.ModelGuru.ModelAttendance;
import com.fingertech.kesforstudent.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterCode extends RecyclerView.Adapter<AdapterCode.MyHolder>  {
    private List<ModelAttendance> modelCodeAttidudes;
    Context context;
    int row_index;
    private int focusedItem = 0;
    private String id_grade,id_attitude;

    private OnItemClickListener onItemClickListener;
    private JSONArray jsonArray;
    private AdapterDetailAbsen adapterDetailAbsen;
    private JSONObject jsonObject;
    private OnImageClickListener onImageClickListener;


    AdapterCode(Context context, List<ModelAttendance> viewItemList,OnImageClickListener onImageClickListener) {
        this.context = context;
        this.modelCodeAttidudes = viewItemList;
        this.onImageClickListener = onImageClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    JSONArray jsonArrays ;
    @Override
    public  MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_code, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ModelAttendance viewItem = modelCodeAttidudes.get(position);

        holder.tv_code.setText(viewItem.getCodeabsen());
        holder.bgcolor.setBackgroundColor(Color.parseColor(viewItem.getWarna()));
        if (focusedItem == position){
            holder.view.setVisibility(View.VISIBLE);
        }else {
            holder.view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return modelCodeAttidudes.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_code;
        LinearLayout bgcolor;
        View view;

        OnItemClickListener onItemClickListener;

        MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_code       = itemView.findViewById(R.id.tv_code);
            bgcolor       = itemView.findViewById(R.id.ll_color);
            view          = itemView.findViewById(R.id.ll_border);

            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
//            onItemClickListener.onItemClick(v, getLayoutPosition());
            notifyItemChanged(focusedItem);
            focusedItem = getLayoutPosition();
            notifyItemChanged(focusedItem);
            jsonObject = new JSONObject();
            if (modelCodeAttidudes.get(focusedItem).getId().equals("0")){
                if (modelCodeAttidudes.get(focusedItem).getCodeabsen().equals("H")||modelCodeAttidudes.get(focusedItem).getCodeabsen().equals("T")||modelCodeAttidudes.get(focusedItem).getCodeabsen().equals("D")){
                    id_grade    = modelCodeAttidudes.get(focusedItem).getId_attitude();

                    try {
                        jsonObject.put("absentStatus","1");
                        jsonObject.put("absentType",id_grade);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    id_grade    = modelCodeAttidudes.get(focusedItem).getId_attitude();
//                    jsonObject = new JSONObject();
                    try {
                        jsonObject.put("absentStatus", "0");
                        jsonObject.put("absentType", id_grade);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                jsonArrays = new JSONArray();
                JSONObject detailObject = new JSONObject();
                id_attitude = modelCodeAttidudes.get(focusedItem).getId();
                id_grade    = modelCodeAttidudes.get(focusedItem).getId_attitude();
//                jsonObject = new JSONObject();
                try {
                    detailObject.put("attitudeid", id_attitude);
                    detailObject.put("gradeid", id_grade);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArrays.put(detailObject);
                try {
                    jsonObject.put("detail",jsonArrays);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setdata(jsonObject);
        }
    }
    JSONArray sourceArray = new JSONArray();
    private void setdata(JSONObject jsonObject){

        sourceArray.put(jsonObject);

        Log.d("Json",sourceArray+"");

        onImageClickListener.onImageClick(jsonObject,focusedItem);
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        return tryMoveSelection(lm, -1);
                    }
                }

                return false;
            }
        });
    }

    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
        int tryFocusItem = focusedItem + direction;

        // If still within valid bounds, move the selection, notify to redraw, and scroll
        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
            notifyItemChanged(focusedItem);
            focusedItem = tryFocusItem;
            notifyItemChanged(focusedItem);
            lm.scrollToPosition(focusedItem);
            return true;
        }

        return false;
    }

    public interface OnImageClickListener {
        void onImageClick(JSONObject jsonObject,int position);
    }


}