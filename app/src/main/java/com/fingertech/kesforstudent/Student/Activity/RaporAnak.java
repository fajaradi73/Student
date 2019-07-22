package com.fingertech.kesforstudent.Student.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.Student.Adapter.RaportAdapter;
import com.fingertech.kesforstudent.Student.Model.RaporModel;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.pepperonas.materialdialog.MaterialDialog;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kesforstudent.Service.App.ANDROID_CHANNEL_ID;

public class RaporAnak extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_semester,nama_kelas,tv_status,tv_kritik,tv_kelas,tv_naik,tv_peringkat;
    Button btn_download,btn_go;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String statusrapor,peringkat,kritik,kelas_sekarang,kelas_naik;
    String authorization,school_code,classroom_id,student_id,semester_id,date;
    String teori,ulangan_harian,praktikum,eskul,ujian_sekolah,ujian_negara,mapel,nilai_akhir,kkm,rata_rata,rr_angkatan;
    RecyclerView rv_rapor;
    Spinner sp_semester;
    SharedPreferences sharedPreferences;
    private List<JSONResponse.DataSemester> dataSemesters;
    String semester_nama,start_date,end_date,start_year,start_end;
    List<RaporModel>raporModelList = new ArrayList<>();
    RaporModel raporModel;
    RaportAdapter raportAdapter;
    List<JSONResponse.DetailScoreItem>detailScoreItemList;
    private static final int WRITE_REQUEST_CODE = 300;
    private NotificationManagerCompat notificationManager;
    NotificationCompat.Builder notification;
    int progressMax = 100;
    PendingIntent pendingIntent;

    String fileName,nama_anak;
    String folder;
    private boolean isDownloaded;
    LinearLayout ll_kritik,ll_status,no_rapor;
    List<String> listSpinner = new ArrayList<String>();
    int spinnerPosition;
    LinearLayout raport_view,ll_ajaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapor_anak);
        toolbar             = findViewById(R.id.toolbar_rapor);
        rv_rapor            = findViewById(R.id.rv_raport);
        sp_semester         = findViewById(R.id.sp_semester);
        tv_semester         = findViewById(R.id.tv_tanggal_semester);
        tv_status           = findViewById(R.id.status_raport);
        tv_kritik           = findViewById(R.id.kritik_saran);
        ll_kritik           = findViewById(R.id.ll_kritik);
        ll_status           = findViewById(R.id.ll_status);
        ll_ajaran           = findViewById(R.id.hint_ajaran);
        no_rapor            = findViewById(R.id.hint_raport);
        raport_view         = findViewById(R.id.rapor_view);
        tv_kelas            = findViewById(R.id.tv_kelas);
        tv_naik             = findViewById(R.id.tv_naik);
        tv_peringkat        = findViewById(R.id.peringkat);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        nama_anak           = sharedPreferences.getString("student_name",null);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        Check_Semester();

        notificationManager = NotificationManagerCompat.from(this);
    }
    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
                Log.i("check_semester", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.CheckSemester resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    semester_id = response.body().getData();
                    dapat_semester();
                    dapat_rapor();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                if (response.isSuccessful()) {
                    JSONResponse.ListSemester resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataSemesters = response.body().getData();
                        listSpinner.add("Pilih Semester...");
                        for (int i = 0; i < dataSemesters.size(); i++) {
                            Collections.sort(dataSemesters, new Comparator<JSONResponse.DataSemester>() {
                                @Override
                                public int compare(JSONResponse.DataSemester o1, JSONResponse.DataSemester o2) {
                                    return (o1.getStart_date().compareTo(o2.getStart_date()));
                                }
                            });
                            start_year  = converTahun(dataSemesters.get(0).getStart_date());
                            start_end   = converTahun(dataSemesters.get(dataSemesters.size()-1).getEnd_date());
                            listSpinner.add("Semester "+dataSemesters.get(i).getSemester_name()+" "+start_year+"/"+start_end);

                        }
                        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                RaporAnak.this, R.layout.spinner_white, listSpinner) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.WHITE);
                                }
                                return view;
                            }
                        };
                        spinnerPosition = adapterRaport.getPosition("Semester "+semester_nama+" "+start_year+"/"+start_end);
                        adapterRaport.setDropDownViewResource(R.layout.custom_spinner_dropdown);
                        sp_semester.setAdapter(adapterRaport);
                        sp_semester.setOnItemSelectedListener((parent, view, position, id) -> {
                            if (position > 0) {
                                semester_id     = dataSemesters.get(position - 1).getSemester_id();
                                semester_nama   = dataSemesters.get(position - 1).getSemester_name();
                                start_date      = dataSemesters.get(position - 1).getStart_date();
                                end_date        = dataSemesters.get(position - 1).getEnd_date();
                                spinnerPosition = adapterRaport.getPosition("Semester "+semester_nama+" "+start_year+"/"+start_end);
                                sp_semester.setSelection(spinnerPosition);
                                dapat_rapor();
                                if (semester_id.equals("0")){
                                    tv_semester.setText("Tahun ajaran telah selesai");
                                }else {
                                    tv_semester.setText(convertTanggal(start_date) + " Sampai " + convertTanggal(end_date));
                                }
                            }
                        });
                        sp_semester.setSelection(spinnerPosition);
                        if (semester_id.equals("0")){
                            tv_semester.setText("Tahun ajaran telah selesai");
                        }else {
                            tv_semester.setText(convertTanggal(start_date) + " Sampai " + convertTanggal(end_date));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tahun));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTanggal(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tahun));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void dapat_rapor(){
        progressBar();
        showDialog();
        Call<JSONResponse.Raport>call = mApiInterface.kes_rapor_score_get(authorization,school_code.toLowerCase(),student_id,classroom_id,semester_id);
        call.enqueue(new Callback<JSONResponse.Raport>() {
            @Override
            public void onResponse(Call<JSONResponse.Raport> call, Response<JSONResponse.Raport> response) {
                Log.d("raporSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.Raport resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        if (response.body().getData().getDetailScore()!=null){
                            detailScoreItemList = response.body().getData().getDetailScore();
                            no_rapor.setVisibility(View.GONE);
                            raport_view.setVisibility(View.VISIBLE);
                            ll_ajaran.setVisibility(View.GONE);
                            peringkat       = response.body().getData().getClassroom().getPromoteRanking();
                            statusrapor     = response.body().getData().getClassroom().getPromoteText();
                            kritik          = response.body().getData().getClassroom().getDescriptionText();
                            tv_peringkat.setText(peringkat);
                            tv_status.setText(statusrapor);
                            tv_kritik.setText(kritik);
                            ll_kritik.setOnClickListener(v -> {
                                new MaterialDialog.Builder(RaporAnak.this)
                                        .title("Kritik dan Saran")
                                        .message(kritik)
                                        .positiveText("Ok")
                                        .show();
                            });
                            ll_status.setOnClickListener(v -> {
                                new MaterialDialog.Builder(RaporAnak.this)
                                        .title("Status")
                                        .message(statusrapor)
                                        .positiveText("Ok")
                                        .show();
                            });

                            if (raporModelList!=null) {
                                raporModelList.clear();
                                for (JSONResponse.DetailScoreItem detailScoreItem : detailScoreItemList) {
                                    nilai_akhir = detailScoreItem.getFinalScore();
                                    kkm         = detailScoreItem.getCources_kkm();
                                    rata_rata   = detailScoreItem.getClassAverageScore();
                                    mapel       = detailScoreItem.getCourcesName();
                                    rr_angkatan = detailScoreItem.getClass_average_edu();
                                    raporModel = new RaporModel();
                                    raporModel.setMapel(mapel);
                                    raporModel.setNilaiakhir(convertZero(nilai_akhir));
                                    raporModel.setKkm(convertZero(kkm));
                                    raporModel.setRr_kelas(convertZero(rata_rata));
                                    raporModel.setRr_angkatan(convertZero(rr_angkatan));
                                    raporModelList.add(raporModel);
                                }
                                raportAdapter = new RaportAdapter(raporModelList);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(RaporAnak.this);
                                layoutManager.setOrientation(RecyclerView.VERTICAL);
                                rv_rapor.setLayoutManager(layoutManager);
                                rv_rapor.setAdapter(raportAdapter);
                                raportAdapter.setOnItemClickListener(new RaportAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Intent intent   = new Intent(RaporAnak.this,DetailRaport.class);
                                        intent.putExtra("authorization",authorization);
                                        intent.putExtra("school_code",school_code);
                                        intent.putExtra("student_id",student_id);
                                        intent.putExtra("classroom_id",classroom_id);
                                        intent.putExtra("posisi",position);
                                        intent.putExtra("semester_id",semester_id);
                                        startActivityForResult(intent,1);
                                    }
                                });
                            }
                        }else {
                            detailScoreItemList = null;
                            if (semester_id.equals("0")){
                                no_rapor.setVisibility(View.GONE);
                                ll_ajaran.setVisibility(View.VISIBLE);
                                raport_view.setVisibility(View.GONE);
                            }else {
                                no_rapor.setVisibility(View.VISIBLE);
                                raport_view.setVisibility(View.GONE);
                                ll_ajaran.setVisibility(View.GONE);
                                tv_status.setText("-");
                                tv_peringkat.setText("-");
                                tv_kritik.setText("-");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Raport> call, Throwable t) {
                Log.e("Erordata",t.toString());
                hideDialog();
            }
        });
    }

    String convertZero(String data) {
        if (data.equals("0.0")){
            data = "-";
        }else if (data.equals("0")){
            data = "-";
        }
        return data;
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
        dialog = new ProgressDialog(RaporAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_download:
                if (detailScoreItemList != null){
                    new DownloadFile().execute(ApiClient.BASE_URL+"students/kes_rapor_pdf?school_code="+school_code.toLowerCase()+"&student_id="+student_id+"&classroom_id="+classroom_id+"&semester_id="+semester_id);
                }else {
                    FancyToast.makeText(getApplicationContext(),"Rapor Belum diterbitkan oleh guru",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private class DownloadFile extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(RaporAnak.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setTitle("Sedang Mendowload");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();

            notification = new NotificationCompat.Builder(RaporAnak.this, ANDROID_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_logo_grey)
                    .setContentTitle("Download")
                    .setContentText("Download in progress")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setOngoing(true)
                    .setOnlyAlertOnce(true)
                    .setProgress(progressMax, 0, true);
            notificationManager.notify(2, notification.build());

        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss", Locale.getDefault()).format(new Date());

                //Extract file name from URL
                fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1, f_url[0].length());

                //Append timestamp to file name
                fileName = "Raport "+nama_anak+"_"+timestamp+".pdf";

                //External directory path to save file
                folder = Environment.getExternalStorageDirectory() + File.separator + "KES Documents/";

                //Create androiddeft folder if it does not exist
                File directory = new File(folder);

                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Output stream to write file
                OutputStream output = new FileOutputStream(folder + fileName);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return "Something went wrong";
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
            notification.setContentText(Integer.parseInt(progress[0])+" %")
                    .setProgress(progressMax, Integer.parseInt(progress[0]), false);
            notificationManager.notify(2, notification.build());
//            SystemClock.sleep(1000);
        }


        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();
            // Display File path after downloading
            if (message.equals("Something went wrong")){
                notification.setContentText("Download Gagal")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(2, notification.build());
            }else {
                Intent intent = new Intent(RaporAnak.this, LihatPdf.class);
                intent.putExtra("file", fileName);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(RaporAnak.this, (int) (Math.random() * 100), intent,
                        PendingIntent.FLAG_ONE_SHOT);

                notification.setContentText("Download selesai")
                        .setProgress(0, 0, false)
                        .setOngoing(false)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(2, notification.build());
                pilihan();
            }
        }
    }
    private void pilihan(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RaporAnak.this,R.style.DialogTheme);
        builder.setTitle("Download Selesai");
        builder.setMessage("Apakah anda ingin melihat file yang sudah didownload?");
        builder.setIcon(R.drawable.ic_pdf);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RaporAnak.this, LihatPdf.class);
                intent.putExtra("file",fileName);
                startActivity(intent);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                authorization   = data.getStringExtra("authorization");
                school_code     = data.getStringExtra("school_code");
                student_id      = data.getStringExtra("student_id");
                classroom_id    = data.getStringExtra("classroom_id");
                if (semester_id.equals(data.getStringExtra("semester_id"))){
                    Log.d("rapor","semester sama");
                }else {
                    semester_id     = data.getStringExtra("semester_id");
                    dapat_semester();
                    dapat_rapor();
                }
            }
        }
    }
}


