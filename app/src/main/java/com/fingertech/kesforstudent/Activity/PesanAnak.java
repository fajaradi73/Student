package com.fingertech.kesforstudent.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kesforstudent.Adapter.PesanAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Model.PesanModel;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanAnak extends AppCompatActivity {

    Toolbar toolbar;
    String authorization,school_code,member_id;
    EditText date_from,date_to;
    CardView go;
    RecyclerView recyclerView;
    Auth mApiInterface;
    int status;
    String code;
    PesanAdapter pesanAdapter;
    List<PesanModel> pesanModelList;
    PesanModel pesanModel;
    DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String tanggal,kelas,mapel,pesan,guru;
    ProgressDialog dialog;
    TextView no_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_anak);
        toolbar         = findViewById(R.id.toolbar_pesan);
        date_from       = findViewById(R.id.et_dari);
        date_to         = findViewById(R.id.et_sampai);
        go              = findViewById(R.id.btn_go);
        recyclerView    = findViewById(R.id.rv_pesan);
        no_pesan        = findViewById(R.id.tv_no_pesan);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        member_id       = getIntent().getStringExtra("member_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, R.style.DialogTheme,
                (datepicker, selectedyear, selectedmonth, selectedday) ->
                        date_from.setText(convertDate(selectedyear, selectedmonth, selectedday)), mYear, mMonth, mDay);

        date_from.setOnClickListener(view -> mDatePicker.show());

        date_from.setOnFocusChangeListener((view, b) -> {
            if (b) {
                mDatePicker.show();
            }
        });

        final DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                (datepicker, selectedyear, selectedmonth, selectedday) ->
                        date_to.setText(convertDate(selectedyear, selectedmonth, selectedday)), mYear, mMonth, mDay);

        date_to.setText(converDate(dateFormat.format(Calendar.getInstance().getTime())));

        date_to.setOnClickListener(view -> datePickerDialog.show());

        date_to.setOnFocusChangeListener((view, b) -> {
            if (b) {
                datePickerDialog.show();
            }
        });

        go.setOnClickListener(v -> {
                dapat_pesan();
        });
        dapat_pesan();
    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.PesanAnak> call = mApiInterface.kes_message_inbox_get(authorization.toString(),school_code.toLowerCase(),member_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanAnak>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanAnak> call, final Response<JSONResponse.PesanAnak> response) {
                Log.d("onRespone",response.code()+"");
                hideDialog();
                JSONResponse.PesanAnak resource = response.body();

                status  = resource.status;
                code    = resource.code;

                if (status == 1 & code.equals("DTS_SCS_0001")){
                    pesanModelList  = new ArrayList<PesanModel>();
                    for (int i = 0; i < response.body().getData().size();i++){
                        tanggal     = response.body().getData().get(i).getDatez_ok();
                        mapel       = response.body().getData().get(i).getCources_name();
                        pesan       = response.body().getData().get(i).getMessage_cont();
                        guru        = response.body().getData().get(i).getSender_name();
                        kelas       = response.body().getData().get(i).getClassroom_name();
                        pesanModel = new PesanModel();
                        pesanModel.setTanggal(tanggal);
                        pesanModel.setMapel(mapel);
                        pesanModel.setPesan(pesan);
                        pesanModel.setKelas(kelas);
                        pesanModel.setDari(guru);
                        pesanModelList.add(pesanModel);
                    }
                    no_pesan.setVisibility(View.GONE);
                    pesanAdapter = new PesanAdapter(pesanModelList);
                    pesanAdapter.setOnItemClickListener(new PesanAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                            Intent intent = new Intent(getApplicationContext(),PesanDetail.class);
//                            intent.putExtra("authorization",authorization);
//                            intent.putExtra("school_code",school_code);
//                            intent.putExtra("member_id",member_id);
//                            intent.putExtra("message_id",response.body().getData().get(position).getMessageid());
//                            intent.putExtra("parent_message_id",response.body().getData().get(position).getParent_message_id());
//                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesanAnak.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(pesanAdapter);

                }
                else if (status == 0 & code.equals("DTS_ERR_0001")){
                    no_pesan.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanAnak> call, Throwable t) {
                Log.i("onFailure",t.toString());
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
    }
    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
        dialog.setContentView(R.layout.progressbar);
    }
    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }
    public void progressBar(){
        dialog = new ProgressDialog(PesanAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
