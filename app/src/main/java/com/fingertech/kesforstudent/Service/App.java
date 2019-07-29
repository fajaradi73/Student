package com.fingertech.kesforstudent.Service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import net.danlew.android.joda.JodaTimeAndroid;

public class  App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    public static final String ANDROID_CHANNEL_ID = "Notifikasi";
    public static final String ANDROID_CHANNEL_NAME = "Notifikasi KES";
    public static final String ANDROID_CHANNEL_DESC = "Notifikasi dari hp";


    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper(this);
        DatabaseManager.initializeInstance(dbHelper);
        createNotificationChannels();

    }

    public static Context getContext(){
        return context;
    }


    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel2 = new NotificationChannel(
                    ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel2);
        }
    }

}