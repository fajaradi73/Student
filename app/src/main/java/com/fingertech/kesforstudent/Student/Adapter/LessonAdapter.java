package com.fingertech.kesforstudent.Student.Adapter;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Student.Model.LessonModel;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyHolder> {

    private List<LessonModel> viewItemList;

    private Context context;
    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private String fileName,nama_file;
    private String folder;
    public LessonAdapter(Context context,List<LessonModel> viewItemList) {
        this.viewItemList = viewItemList;
        this.context        = context;
    }

    public void setOnItemClickListener(Context context,OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context            = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lesson, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        LessonModel viewItem = viewItemList.get(position);
        holder.tanggal.setText(viewItem.getTanggal());
        holder.pembuat.setText(viewItem.getNama()+"("+viewItem.getMapel()+")");
        if (viewItem.getLampiran().equals(ApiClient.BASE_LESSON)){
            holder.lampiran.setTextColor(Color.BLACK);
            holder.lampiran.setText("Belum ada file yang dilampirkan");
        }else {
            holder.lampiran.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.lampiran.setText(viewItem.getTitle()+"(lampiran)");
            nama_file = viewItem.getTitle();
            holder.lampiran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            new DownloadFile().execute(viewItem.getLampiran());
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            Toast.makeText(context, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    //check all needed permissions together
                    TedPermission.with(context)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Jika Anda menolak izin, Anda tidak dapat menggunakan layanan ini\n\nSilakan aktifkan izin di [Pengaturan] > [Izin]")
                            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .check();
                }
            });
        }
        holder.judul.setText(viewItem.getMateri());
        holder.desc.setText("*"+viewItem.getDesc());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal,pembuat,lampiran,judul,desc;
        OnItemClickListener onItemClickListener;

        MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tv_tanggal);
            pembuat     = itemView.findViewById(R.id.tv_pembuat);
            lampiran    = itemView.findViewById(R.id.tv_lampiran);
            judul       = itemView.findViewById(R.id.tv_judul);
            desc        = itemView.findViewById(R.id.tv_deskripsi);
//            itemView.setOnClickListener(this);
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
    private class DownloadFile extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(context);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setTitle("Sedang Mendowload");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();

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
                fileName = nama_file + "_" + fileName;

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
        }

        @Override
        protected void onPostExecute(String message) {
            // dismiss the dialog after the file was downloaded
            this.progressDialog.dismiss();
            // Display File path after downloading
            if (message.equals("Something went wrong")) {
                new KAlertDialog(context, KAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Download gagal")
                        .show();
            } else {
                new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Download selesai")
                        .setContentText("Apakah anda ingin membuka file ?")
                        .setCancelText("Tidak")
                        .setConfirmText("Lihat")
                        .showCancelButton(true)
                        .confirmButtonColor(R.color.colorPrimary)
                        .cancelButtonColor(R.color.colorAccent)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismiss();
                            }
                        })
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }
}