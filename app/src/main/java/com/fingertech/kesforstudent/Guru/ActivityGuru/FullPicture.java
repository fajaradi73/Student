package com.fingertech.kesforstudent.Guru.ActivityGuru;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kesforstudent.BuildConfig;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Activity.ProfileAnak;
import com.fingertech.kesforstudent.Util.FileUtils;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class FullPicture extends AppCompatActivity {

    ImageView imageView,btn_exit;
    TextView btn_ubah;
    String picture;

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
    String mCurrentPhotoPath,authorization,school_code,memberid;
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;

    ProfileGuru profileGuru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_picture);
        imageView   =   findViewById(R.id.full_image);
        btn_exit    = findViewById(R.id.btn_exit);
        btn_ubah    = findViewById(R.id.btn_ubah);
        picture     = getIntent().getStringExtra("picture");
        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        memberid        = getIntent().getStringExtra("member_id");

        btn_ubah.setOnClickListener(v -> {
            selectImage();
        });
        btn_exit.setOnClickListener(v -> finish());
        Glide.with(FullPicture.this).load(picture).into(imageView);

    }


    private void selectImage() {
        final CharSequence[] items = {"Buka kamera", "Pilih foto",
                "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(FullPicture.this);
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
                            Toast.makeText(FullPicture.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    //check all needed permissions together
                    TedPermission.with(FullPicture.this)
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
                            Toast.makeText(FullPicture.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        }
                    };
                    //check all needed permissions together
                    TedPermission.with(FullPicture.this)
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
                    Glide.with(FullPicture.this).load(mCurrentPhotoPath).into(imageView);
                    File files = new File(mCurrentPhotoPath);
                    kirim_foto(authorization,school_code,memberid, String.valueOf(files));
                }else{
                    Glide.with(FullPicture.this).load(fileUri).into(imageView);
                    File files = FileUtils.getFile(FullPicture.this, fileUri);
                    kirim_foto(authorization,school_code,memberid, String.valueOf(files));
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                uri = data.getData();
                Glide.with(FullPicture.this).load(uri).into(imageView);
                File file = FileUtils.getFile(FullPicture.this, uri);
                kirim_foto(authorization,school_code,memberid, String.valueOf(file));
            }
        }
    }
    private void kirim_foto(String authorization,String school_code,String memberid,String foto){
        Intent intent = new Intent(FullPicture.this, ProfileGuru.class);
        intent.putExtra("authorization",authorization);
        intent.putExtra("school_code",school_code);
        intent.putExtra("member_id",memberid);
        intent.putExtra("picture",foto);
        intent.putExtra("status","1");
        setResult(RESULT_OK, intent);
        finish();
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

}
