package com.fingertech.kesforstudent;

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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fingertech.kesforstudent.Sqlite.Notifikasi;
import com.fingertech.kesforstudent.Sqlite.NotifikasiTable;
import com.fingertech.kesforstudent.Student.Activity.AbsenAnak;
import com.fingertech.kesforstudent.Student.Activity.AgendaAnak;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.fingertech.kesforstudent.Student.Activity.PesanDetail;
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class NotifikasiActivity extends AppCompatActivity {

    RecyclerView rv_notifikasi;
    Toolbar toolbar;
    NotifikasiModel notifikasiModel;
    NotifikasiAdapter notifikasiAdapter;
    List<NotifikasiModel> notifikasiModelList ;
    NotifikasiTable notifikasiTable = new NotifikasiTable();
    Notifikasi data = new Notifikasi();
    ArrayList<HashMap<String, String>> row;
    LinearLayoutManager layoutManager;
    private static final int MAX_ITEMS_PER_REQUEST = 5;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1000;
    private int page;
    LinearLayout tv_no_notifikasi;
    SharedPreferences sharedPreferences;
    String id_notif,title,body,message_id,school_code, parent_message_id,type,time,status, member_id,student_id,classroom_id,agenda_date;
    RefreshLayout refreshLayout ;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifikasi);
        toolbar             = findViewById(R.id.toolbar);
        rv_notifikasi       = findViewById(R.id.rv_notifikasi);
        tv_no_notifikasi    = findViewById(R.id.no_notifikasi);
        refreshLayout       = findViewById(R.id.refreshLayout);
        layoutManager       = new LinearLayoutManager(this);

        sharedPreferences = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar();
        showDialog();

        getAllData();
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getAllData();
            }
        });
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    private void getAllData(){
        hideDialog();
        row = notifikasiTable.getAllData();
        if (row.size() > 0){
            tv_no_notifikasi.setVisibility(View.GONE);
            rv_notifikasi.setVisibility(View.VISIBLE);
            notifikasiModelList = new ArrayList<>();
            for (int i = 0 ; i < row.size();i++){
                title               = row.get(i).get(Notifikasi.KEY_Title);
                body                = row.get(i).get(Notifikasi.KEY_Body);
                type                = row.get(i).get(Notifikasi.KEY_type);
                time                = row.get(i).get(Notifikasi.KEY_time);
                status              = row.get(i).get(Notifikasi.KEY_status);
                id_notif            = row.get(i).get(Notifikasi.KEY_NotifId);
                message_id          = row.get(i).get(Notifikasi.KEY_message_id);
                school_code         = row.get(i).get(Notifikasi.KEY_School_code);
                parent_message_id   = row.get(i).get(Notifikasi.KEY_parent_message_id);
                member_id           = row.get(i).get(Notifikasi.KEY_member_id);
                student_id          = row.get(i).get(Notifikasi.KEY_student_id);
                classroom_id        = row.get(i).get(Notifikasi.KEY_classroom_id);
                agenda_date         = row.get(i).get(Notifikasi.KEY_agenda_date);

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
            notifikasiAdapter = new NotifikasiAdapter(notifikasiModelList.subList(page,MAX_ITEMS_PER_REQUEST),this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            rv_notifikasi.setLayoutManager(layoutManager);
            rv_notifikasi.setAdapter(notifikasiAdapter);
            rv_notifikasi.addOnScrollListener(createInfiniteScrollListener());
            notifikasiAdapter.setOnItemClickListener(new NotifikasiAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    type = notifikasiModelList.get(position).getType();
                    switch (type) {
                        case "pesan_siswa": {
                            school_code         = notifikasiModelList.get(position).getSchool_code();
                            message_id          = notifikasiModelList.get(position).getMessage_id();
                            parent_message_id   = notifikasiModelList.get(position).getParent_message_id();
                            member_id           = notifikasiModelList.get(position).getMember_id();
                            id_notif            = notifikasiModelList.get(position).getId_notif();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("school_code", school_code);
                            editor.putString("message_id", message_id);
                            editor.putString("parent_message_id", parent_message_id);
                            editor.putString("member_id", member_id);
                            editor.putString("clicked","click");
                            editor.putInt("id_notif", Integer.parseInt(id_notif));
                            editor.apply();
                            Intent intent = new Intent(NotifikasiActivity.this, PesanDetail.class);
                            intent.putExtra("school_code", school_code);
                            intent.putExtra("message_id", message_id);
                            intent.putExtra("parent_message_id", parent_message_id);
                            intent.putExtra("member_id", member_id);
                            intent.putExtra("clicked","click");
                            intent.putExtra("id_notif",id_notif);
                            startActivityForResult(intent,1);
                            break;
                        }
                        case "absen_siswa": {
                            school_code     = notifikasiModelList.get(position).getSchool_code();
                            student_id      = notifikasiModelList.get(position).getStudent_id();
                            classroom_id    = notifikasiModelList.get(position).getClassroom_id();
                            id_notif        = notifikasiModelList.get(position).getId_notif();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("school_code", school_code);
                            editor.putString("student_id", student_id);
                            editor.putString("classroom_id", classroom_id);
                            editor.putString("clicked","click");
                            editor.putInt("id_notif", Integer.parseInt(id_notif));
                            editor.apply();
                            Intent intent = new Intent(NotifikasiActivity.this, AbsenAnak.class);
                            intent.putExtra("school_code", school_code);
                            intent.putExtra("student_id", student_id);
                            intent.putExtra("classroom_id", classroom_id);
                            intent.putExtra("clicked","click");
                            intent.putExtra("id_notif",id_notif);
                            startActivityForResult(intent,1);
                            break;
                        }
                        case "insert_new_agenda": {
                            school_code     = notifikasiModelList.get(position).getSchool_code();
                            student_id      = notifikasiModelList.get(position).getStudent_id();
                            classroom_id    = notifikasiModelList.get(position).getClassroom_id();
                            agenda_date     = notifikasiModelList.get(position).getAgenda_date();
                            id_notif        = notifikasiModelList.get(position).getId_notif();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("school_code", school_code);
                            editor.putString("student_id", student_id);
                            editor.putString("classroom_id", classroom_id);
                            editor.putString("calendar", agenda_date);
                            editor.putString("clicked","click");
                            editor.putInt("id_notif", Integer.parseInt(id_notif));
                            editor.apply();
                            Intent intent = new Intent(NotifikasiActivity.this, AgendaAnak.class);
                            intent.putExtra("school_code", school_code);
                            intent.putExtra("student_id", student_id);
                            intent.putExtra("classroom_id", classroom_id);
                            intent.putExtra("calendar", agenda_date);
                            intent.putExtra("clicked","click");
                            intent.putExtra("id_notif",id_notif);
                            startActivityForResult(intent,1);
                            break;
                        }
                        case "insert_new_exam": {
                            school_code     = notifikasiModelList.get(position).getSchool_code();
                            student_id      = notifikasiModelList.get(position).getStudent_id();
                            classroom_id    = notifikasiModelList.get(position).getClassroom_id();
                            agenda_date     = notifikasiModelList.get(position).getAgenda_date();
                            id_notif        = notifikasiModelList.get(position).getId_notif();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("school_code", school_code);
                            editor.putString("student_id", student_id);
                            editor.putString("classroom_id", classroom_id);
                            editor.putString("calendar", agenda_date);
                            editor.putString("clicked","click");
                            editor.putInt("id_notif", Integer.parseInt(id_notif));
                            editor.apply();
                            Intent intent = new Intent(NotifikasiActivity.this, AgendaAnak.class);
                            intent.putExtra("school_code", school_code);
                            intent.putExtra("student_id", student_id);
                            intent.putExtra("classroom_id", classroom_id);
                            intent.putExtra("calendar", agenda_date);
                            intent.putExtra("clicked","click");
                            intent.putExtra("id_notif",id_notif);
                            startActivityForResult(intent,1);
                            break;
                        }
                        default:{
                            FancyToast.makeText(getApplicationContext(),position+"", Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                            break;
                        }
                    }
                }
            });
        }else {
            tv_no_notifikasi.setVisibility(View.VISIBLE);
            rv_notifikasi.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("counting",0);
            editor.apply();
            Intent intent = new Intent(NotifikasiActivity.this, MenuUtama.class);
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("counting",0);
        editor.apply();
        Intent intent = new Intent(NotifikasiActivity.this,MenuUtama.class);
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

    @NonNull
    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(MAX_ITEMS_PER_REQUEST, layoutManager) {
            @Override public void onScrolledToEnd(final int firstVisibleItemPosition) {
                refreshLayout.setEnableLoadMore(true);
                refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                        refreshLayout.finishLoadMore(2000);
                    }
                });
                int start = ++page * MAX_ITEMS_PER_REQUEST;
                final boolean allItemsLoaded = start >= notifikasiModelList.size();
                if (allItemsLoaded) {
                    refreshLayout.finishLoadMore(2000);
                } else {
                    int end = start + MAX_ITEMS_PER_REQUEST;
                    if (end < notifikasiModelList.size()) {
                        final List<NotifikasiModel> itemsLocal = getItemsToBeLoaded(start, end);
                        refreshView(rv_notifikasi, new NotifikasiAdapter(itemsLocal,NotifikasiActivity.this), firstVisibleItemPosition);
                    }else {
                        if (end != notifikasiModelList.size()) {
                            end = notifikasiModelList.size() - start;
                            end = end + start;
                            final List<NotifikasiModel> itemsLocal = getItemsToBeLoaded(start, end);
                            refreshView(rv_notifikasi, new NotifikasiAdapter(itemsLocal,NotifikasiActivity.this), firstVisibleItemPosition);
                        }
                    }
                }
            }
        };
    }

    @NonNull private List<NotifikasiModel> getItemsToBeLoaded(int start, int end) {
        List<NotifikasiModel> newItems = notifikasiModelList.subList(start, end);
        final List<NotifikasiModel> oldItems = ((NotifikasiAdapter) rv_notifikasi.getAdapter()).getItems();
        final List<NotifikasiModel> itemsLocal = new LinkedList<>();
        itemsLocal.addAll(oldItems);
        itemsLocal.addAll(newItems);
        return itemsLocal;
    }

    //////// Progressbar - Loading Animation
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
        dialog = new ProgressDialog(NotifikasiActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
