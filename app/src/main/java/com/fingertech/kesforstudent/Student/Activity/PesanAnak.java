package com.fingertech.kesforstudent.Student.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kesforstudent.Student.Adapter.PesanAdapter;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Student.Model.PesanModel;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Rest.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PesanAnak extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_pesan;
    Auth mApiInterface,mApi;
    String authorization,school_code,student_id;
    int status;
    String code;
    SharedPreferences sharedPreferences;
    PesanAdapter pesanAnakAdapter;
    List<PesanModel> pesanAnakModelList = new ArrayList<>();
    PesanModel pesanAnakModel;
    ProgressDialog dialog;
    String tanggal,jam,mapel,pesan,guru,classroom_id,message_id,title,read_status,nama_anak;
    LinearLayout no_pesan;

    ArrayList<HashMap<String, String>> contactList;
    String date_from,date_to;
    private SimpleDateFormat formattanggal  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_anak);
        toolbar         = findViewById(R.id.toolbar_pesan_anak);
        rv_pesan        = findViewById(R.id.rv_pesan_anak);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        mApi            = UtilsApi.getAPIService();
        no_pesan        = findViewById(R.id.hint_pesan);


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        nama_anak           = sharedPreferences.getString("fullname",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-2);
        date_from = formattanggal.format(calendar.getTime());
        date_to   = formattanggal.format(Calendar.getInstance().getTime());
        dapat();

    }

    void dapat(){
        progressBar();
        showDialog();
        mApi.kes_message_get(authorization,school_code.toLowerCase(),student_id,date_from,date_to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse.PesanAnak>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONResponse.PesanAnak response) {
                        status = response.status;
                        code   = response.code;
                        if (status == 1 && code.equals("DTS_SCS_0001")){
                            for (int i = 0; i<response.getData().size();i++){
                                jam     = response.getData().get(i).getDatez();
                                tanggal     = response.getData().get(i).getMessage_date();
                                mapel       = response.getData().get(i).getCources_name();
                                pesan       = response.getData().get(i).getMessage_cont();
                                guru        = response.getData().get(i).getSender_name();
                                title       = response.getData().get(i).getMessage_title();
                                read_status = response.getData().get(i).getRead_status();
                                message_id  = response.getData().get(i).getMessageid();
                                pesanAnakModel = new PesanModel();
                                pesanAnakModel.setTanggal(tanggal);
                                pesanAnakModel.setJam(jam);
                                pesanAnakModel.setTitle(title);
                                pesanAnakModel.setPesan(pesan);
                                pesanAnakModel.setDari(guru);
                                pesanAnakModel.setMessage_id(message_id);
                                pesanAnakModel.setRead_status(read_status);
                                pesanAnakModelList.add(pesanAnakModel);
                            }
                            no_pesan.setVisibility(View.GONE);
                        }
                        else if (status == 0 && code.equals("DTS_ERR_0001")){
                            no_pesan.setVisibility(View.VISIBLE);
                            rv_pesan.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Log.d("eror",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        hideDialog();
                        if (pesanAnakModelList!=null) {
                            pesanAnakAdapter = new PesanAdapter(pesanAnakModelList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesanAnak.this);
                            rv_pesan.setLayoutManager(layoutManager);
                            rv_pesan.setAdapter(pesanAnakAdapter);
                            pesanAnakAdapter.setOnItemClickListener(new PesanAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    message_id = pesanAnakModelList.get(position).getMessage_id();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("authorization", authorization);
                                    editor.putString("school_code", school_code);
                                    editor.putString("member_id", student_id);
                                    editor.putString("classroom_id", classroom_id);
                                    editor.putString("message_id", message_id);
                                    editor.putString("student_name", nama_anak);
                                    editor.apply();
                                    Intent intent = new Intent(PesanAnak.this, PesanDetail.class);
                                    intent.putExtra("authorization", authorization);
                                    intent.putExtra("school_code", school_code);
                                    intent.putExtra("member_id", student_id);
                                    intent.putExtra("classroom_id", classroom_id);
                                    intent.putExtra("message_id", message_id);
                                    intent.putExtra("student_name", nama_anak);
                                    startActivityForResult(intent, 1);
                                }
                            });
                        }
                    }
                });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                authorization = data.getStringExtra("authorization");
                school_code   = data.getStringExtra("school_code");
                student_id    = data.getStringExtra("member_id");
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH,-2);
                date_from   = formattanggal.format(calendar.getTime());
                date_to     = formattanggal.format(Calendar.getInstance().getTime());
                dapat();
            }
        }
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