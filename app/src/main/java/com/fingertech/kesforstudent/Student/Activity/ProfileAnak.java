package com.fingertech.kesforstudent.Student.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.BuildConfig;
import com.fingertech.kesforstudent.Controller.Auth;
import com.fingertech.kesforstudent.MainActivity;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Rest.ApiClient;
import com.fingertech.kesforstudent.Rest.JSONResponse;
import com.fingertech.kesforstudent.Util.FileUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileAnak extends AppCompatActivity {

    Auth mApiInterface;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";

    SharedPreferences sharedpreferences;
    String authorization,memberid,username,member_type,fullname,school_code;
    TextView tv_nama,tv_nis,tv_email,tv_no_hp,tv_alamat,tv_gender,tv_tanggal,tv_tempat,tv_agama;
    String nama,nis,email,alamat,gender,tanggal,tempat,agama,Base_anak,picture,no_hp;
    ImageView image_profile;
    int status;
    Toolbar toolbar;
    CardView cv_profile;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ProgressDialog dialog;
    CardView btn_logout,btn_edit,btn_gantifoto;
    ConstraintLayout constraintLayout;
    public final int SELECT_FILE = 1;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = ProfileAnak.class.getSimpleName();

    public static final int MEDIA_TYPE_IMAGE = 1;

    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    private String postPath;

    Uri fileUri,uri;
    File image;
    Intent intent;
    String mCurrentPhotoPath,code,school_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_anak);

        mApiInterface           = ApiClient.getClient().create(Auth.class);
        image_profile           = findViewById(R.id.image_profil_anak);
        tv_nama                 = findViewById(R.id.namaanak);
        tv_nis                  = findViewById(R.id.nis);
        tv_email                = findViewById(R.id.email);
        tv_no_hp                = findViewById(R.id.no_hp);
        tv_alamat               = findViewById(R.id.alamat_profile);
        tv_gender               = findViewById(R.id.gender_profile);
        tv_tanggal              = findViewById(R.id.tanggal_lahir);
        tv_tempat               = findViewById(R.id.tempat_lahir_anak);
        tv_agama                = findViewById(R.id.agama);
        toolbar                 = findViewById(R.id.toolbar_profile_anak);
        cv_profile              = findViewById(R.id.btn_image_anak);
        collapsingToolbarLayout = findViewById(R.id.collapse_profile_anak);
        appBarLayout            = findViewById(R.id.appbar_profile_anak);
        btn_logout              = findViewById(R.id.btn_logout);
        btn_edit                = findViewById(R.id.btn_edit);
        btn_gantifoto           = findViewById(R.id.btn_edit_foto);
        constraintLayout        = findViewById(R.id.content_profile);
        Base_anak               = "http://genpin.co.id/schoolc/assets/images/profile/mm_";

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization     = sharedpreferences.getString(TAG_TOKEN,"");
        memberid          = sharedpreferences.getString(TAG_MEMBER_ID,"");
        username          = sharedpreferences.getString(TAG_EMAIL,"");
        fullname          = sharedpreferences.getString(TAG_FULLNAME,"");
        member_type       = sharedpreferences.getString(TAG_MEMBER_TYPE,"");
        school_code       = sharedpreferences.getString(TAG_SCHOOL_CODE,"");
        school_name       = sharedpreferences.getString("school_name","");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Profile Anak");
                    cv_profile.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");//carefull there should a space between double quote otherwise it wont work
                    cv_profile.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
        get_profile();
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihan();
            }
        });
        btn_gantifoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAnak.this,EditProfile.class);
                intent.putExtra("nama",fullname);
                intent.putExtra("nis",nis);
                intent.putExtra("email",email);
                intent.putExtra("alamat",alamat);
                intent.putExtra("gender",gender);
                intent.putExtra("tanggal_lahir",tanggal);
                intent.putExtra("tempat_lahir",tempat);
                intent.putExtra("agama",agama);
                intent.putExtra("nohp",no_hp);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("member_id",memberid);
                startActivityForResult(intent,1);
            }
        });
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

    private void get_profile(){
        progressBar();
        showDialog();
        Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization.toString(),school_code.toLowerCase(),memberid.toString());
        call.enqueue(new Callback<JSONResponse.GetProfile>() {
            @Override
            public void onResponse(Call<JSONResponse.GetProfile> call, Response<JSONResponse.GetProfile> response) {
                Log.d("onRespone",response.code()+"");
                hideDialog();
                JSONResponse.GetProfile resource = response.body();
                status = resource.status;
                if (status == 1){
                    nama        = response.body().getData().getFullname();
                    nis         = response.body().getData().getMember_code();
                    email       = response.body().getData().getEmail();
                    alamat      = response.body().getData().getAddress();
                    gender      = response.body().getData().getGender();
                    tanggal     = response.body().getData().getBirth_date();
                    tempat      = response.body().getData().getBirth_place();
                    agama       = response.body().getData().getReligion();
                    picture     = response.body().getData().getPicture();
                    no_hp       = response.body().getData().getMobile_phone();

                    tv_nama.setText(nama);
                    tv_nis.setText("Nomor induk siswa (NIS) : "+nis);
                    tv_email.setText(email);
                    tv_alamat.setText(alamat);
                    tv_gender.setText(gender);
                    tv_tanggal.setText(converDate(tanggal));
                    tv_tempat.setText(tempat);
                    tv_agama.setText(agama);
                    tv_no_hp.setText(no_hp);
                    Glide.with(ProfileAnak.this).load(Base_anak + picture).into(image_profile);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.GetProfile> call, Throwable t) {
                Log.d("onfailure",t.toString());
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
        dialog = new ProgressDialog(ProfileAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    private void pilihan() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileAnak.this,R.style.DialogTheme);
        builder.setTitle("Log out");
        builder.setMessage("Apakah anda ingin keluar?");
        builder.setIcon(R.drawable.ic_alarm);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Masuk.session_status, false);
                editor.putString(TAG_EMAIL, null);
                editor.putString(TAG_MEMBER_ID, null);
                editor.putString(TAG_FULLNAME, null);
                editor.putString(TAG_MEMBER_TYPE, null);
                editor.putString(TAG_TOKEN, null);
                editor.commit();

                Intent intent = new Intent(ProfileAnak.this, MainActivity.class);
                finish();
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

    private void selectImage() {
        final CharSequence[] items = {"Buka kamera", "Pilih foto",
                "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileAnak.this);
        builder.setTitle("Ganti foto profile!");
        builder.setIcon(R.drawable.ic_kamera);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Buka kamera")) {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            captureImage();
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            Toast.makeText(ProfileAnak.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    //check all needed permissions together
                    TedPermission.with(ProfileAnak.this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Jika Anda menolak izin, Anda tidak dapat menggunakan layanan ini\n\nSilakan aktifkan izin di [Pengaturan] > [Izin]")
                            .setPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                            .check();

                } else if (items[item].equals("Pilih foto")) {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                        }

                        @Override
                        public void onPermissionDenied(List<String> deniedPermissions) {
                            Toast.makeText(ProfileAnak.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    //check all needed permissions together
                    TedPermission.with(ProfileAnak.this)
                            .setPermissionListener(permissionlistener)
                            .setDeniedMessage("Jika Anda menolak izin, Anda tidak dapat menggunakan layanan ini\n\nSilakan aktifkan izin di [Pengaturan] > [Izin]")
                            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .check();

                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST){
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(ProfileAnak.this).load(mCurrentPhotoPath).into(image_profile);
                    File files = new File(mCurrentPhotoPath);
                    UploadImage(files);
                }else{
                    Glide.with(ProfileAnak.this).load(fileUri).into(image_profile);
                    File files = FileUtils.getFile(ProfileAnak.this, fileUri);
                    UploadImage(files);

                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                uri = data.getData();
                Glide.with(ProfileAnak.this).load(uri).into(image_profile);
                File file = FileUtils.getFile(ProfileAnak.this, uri);
                UploadImage(file);
            }
            else if (requestCode == 1) {
                authorization = data.getStringExtra("authorization");
                school_code   = data.getStringExtra("school_code");
                memberid      = data.getStringExtra("member_id");
                get_profile();
            }
        }
    }

    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }

    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();

        return image;
    }

    private void UploadImage(File file){
        progressBar();
        showDialog();
        String pic_type = "png";
        RequestBody photoBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("picture",
                file.getName(), photoBody);
        RequestBody ids = RequestBody.create(MediaType.parse("multipart/form-data"), memberid);
        RequestBody picss = RequestBody.create(MediaType.parse("multipart/form-data"), pic_type);
        RequestBody schoolcode= RequestBody.create(MediaType.parse("multipart/form-data"), school_code);

        Call<JSONResponse.UpdatePicture> call = mApiInterface.kes_update_picture_post(authorization.toString(),schoolcode,ids,photoPart,picss);

        call.enqueue(new Callback<JSONResponse.UpdatePicture>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.UpdatePicture> call, final Response<JSONResponse.UpdatePicture> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.UpdatePicture resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("UPP_SCS_0001")) {
                    FancyToast.makeText(getApplicationContext(),"Foto berhasil diupload",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                } else{
                    if (status == 0 && code.equals("UPP_ERR_0001")) {
                        FancyToast.makeText(getApplicationContext(),"Data tidak ditemnukan",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.UpdatePicture> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
    }

    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
