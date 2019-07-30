package com.fingertech.kesforstudent.Guru.ActivityGuru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.NotifikasiActivity;
import com.fingertech.kesforstudent.NotifikasiAdapter;
import com.fingertech.kesforstudent.NotifikasiModel;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Sqlite.Notifikasi;
import com.fingertech.kesforstudent.Sqlite.NotifikasiGuru;
import com.fingertech.kesforstudent.Sqlite.NotifikasiGuruTable;
import com.fingertech.kesforstudent.Sqlite.NotifikasiTable;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class NotifikasiGuruActivity extends AppCompatActivity {

    RecyclerView rv_notifikasi;
    Toolbar toolbar;
    NotifikasiModel notifikasiModel;
    NotifikasiAdapter notifikasiAdapter;
    List<NotifikasiModel> notifikasiModelList ;
    NotifikasiGuruTable notifikasiTable = new NotifikasiGuruTable();
    NotifikasiGuru data = new NotifikasiGuru();
    ArrayList<HashMap<String, String>> row;
    LinearLayoutManager layoutManager;
    private static final int MAX_ITEMS_PER_REQUEST = 5;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1000;
    private int page ;
    LinearLayout tv_no_notifikasi;
    SharedPreferences sharedPreferences,sharedpreferences;
    String id_notif,title,body,message_id,school_code, parent_message_id,type,time,status, member_id,student_id,classroom_id,agenda_date;
    RefreshLayout refreshLayout ;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasi_guru);
        toolbar             = findViewById(R.id.toolbar);
        rv_notifikasi       = findViewById(R.id.rv_notifikasi);
        tv_no_notifikasi    = findViewById(R.id.no_notifikasi);
        refreshLayout       = findViewById(R.id.refreshLayout);
        layoutManager       = new LinearLayoutManager(this);

        sharedPreferences = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAllData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getAllData();
                refreshLayout.finishRefresh(200);
            }
        });
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

    }

    private void getAllData(){
        row = notifikasiTable.getAllData();
        if (row.size() > 0){
            tv_no_notifikasi.setVisibility(View.GONE);
            rv_notifikasi.setVisibility(View.VISIBLE);
            notifikasiModelList = new ArrayList<>();
            for (int i = 0 ; i < row.size();i++){
                title               = row.get(i).get(NotifikasiGuru.KEY_Title);
                body                = row.get(i).get(NotifikasiGuru.KEY_Body);
                type                = row.get(i).get(NotifikasiGuru.KEY_type);
                time                = row.get(i).get(NotifikasiGuru.KEY_time);
                status              = row.get(i).get(NotifikasiGuru.KEY_status);
                id_notif            = row.get(i).get(NotifikasiGuru.KEY_NotifId);
                message_id          = row.get(i).get(NotifikasiGuru.KEY_message_id);
                school_code         = row.get(i).get(NotifikasiGuru.KEY_School_code);
                parent_message_id   = row.get(i).get(NotifikasiGuru.KEY_parent_message_id);
                member_id           = row.get(i).get(NotifikasiGuru.KEY_member_id);
                student_id          = row.get(i).get(NotifikasiGuru.KEY_student_id);
                classroom_id        = row.get(i).get(NotifikasiGuru.KEY_classroom_id);
                agenda_date         = row.get(i).get(NotifikasiGuru.KEY_agenda_date);

                notifikasiModel = new NotifikasiModel();
                notifikasiModel.setTitle(title);
                notifikasiModel.setBody(body);
                notifikasiModel.setType(type);
                notifikasiModel.setTime(time);
                notifikasiModel.setStatus(status);
                notifikasiModel.setId_notif(id_notif);
                notifikasiModel.setMessage_id(message_id);
                notifikasiModel.setSchool_code(school_code);
                notifikasiModel.setParent_message_id(parent_message_id);
                notifikasiModel.setMember_id(member_id);
                notifikasiModel.setStudent_id(student_id);
                notifikasiModel.setClassroom_id(classroom_id);
                notifikasiModel.setAgenda_date(agenda_date);
                notifikasiModelList.add(notifikasiModel);
            }
            if (notifikasiModelList != null) {
                Collections.sort(notifikasiModelList, byDate);
            }
//            if (MAX_ITEMS_PER_REQUEST > notifikasiModelList.size()){
//                notifikasiAdapter = new NotifikasiAdapter(notifikasiModelList.subList(page,notifikasiModelList.size()),this);
//            }else {
//                notifikasiAdapter = new NotifikasiAdapter(notifikasiModelList.subList(page,MAX_ITEMS_PER_REQUEST),this);
//            }
            notifikasiAdapter = new NotifikasiAdapter(notifikasiModelList,this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rv_notifikasi.setLayoutManager(layoutManager);
            rv_notifikasi.setAdapter(notifikasiAdapter);
//            rv_notifikasi.addOnScrollListener(createInfiniteScrollListener());
            if (notifikasiAdapter != null) {
                notifikasiAdapter.setOnItemClickListener(new NotifikasiAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        type = notifikasiModelList.get(position).getType();
                        switch (type) {
                            case "pesan_guru": {
                                school_code         = notifikasiModelList.get(position).getSchool_code();
                                message_id          = notifikasiModelList.get(position).getMessage_id();
                                parent_message_id   = notifikasiModelList.get(position).getParent_message_id();
                                member_id           = notifikasiModelList.get(position).getMember_id();
                                id_notif            = notifikasiModelList.get(position).getId_notif();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("school_code", school_code);
                                editor.putString("message_id", message_id);
                                editor.putString("reply_message_id", parent_message_id);
                                editor.putString("member_id", member_id);
                                editor.putString("clicked", "click");
                                editor.putInt("id_notif", Integer.parseInt(id_notif));
                                editor.apply();
                                Intent intent = new Intent(NotifikasiGuruActivity.this, PesanMasukDetail.class);
                                intent.putExtra("school_code", school_code);
                                intent.putExtra("message_id", message_id);
                                intent.putExtra("reply_message_id", parent_message_id);
                                intent.putExtra("member_id", member_id);
                                intent.putExtra("clicked", "click");
                                intent.putExtra("id_notif", id_notif);
                                startActivityForResult(intent, 1);
                                break;
                            }
                            default: {
                                FancyToast.makeText(getApplicationContext(), position + "", Toast.LENGTH_LONG, FancyToast.INFO, false).show();
                                break;
                            }
                        }
                    }
                });
            }
        }else {
            tv_no_notifikasi.setVisibility(View.VISIBLE);
            rv_notifikasi.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("counting_guru",0);
            editor.apply();
            Intent intent = new Intent(NotifikasiGuruActivity.this, MenuUtamaGuru.class);
            intent.putExtra("status_point","true");
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
//        NavUtils.navigateUpFromSameTask(this);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("counting_guru",0);
        editor.apply();
        Intent intent = new Intent(NotifikasiGuruActivity.this,MenuUtamaGuru.class);
        intent.putExtra("status_point","true");
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    static final Comparator<NotifikasiModel> byDate = new Comparator<NotifikasiModel>() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss",new Locale("in","ID"));

        public int compare(NotifikasiModel ord1, NotifikasiModel ord2) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 = sdf.parse(ord1.getTime());
                d2 = sdf.parse(ord2.getTime());
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
//            return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1){
                getAllData();
            }
        }
    }
}
