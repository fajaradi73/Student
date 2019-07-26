package com.fingertech.kesforstudent.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.fingertech.kesforstudent.MainActivity;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.fingertech.kesforstudent.Service.App.ANDROID_CHANNEL_DESC;

public class FirebaseMessaging extends FirebaseMessagingService {
    NotificationManager notificationManager;

    int id = 0;
    Notification notification;

    @Override
    public void onMessageReceived(RemoteMessage remotemsg) {

        String title       = remotemsg.getData().get("title");
        String body        = remotemsg.getData().get("body");

        Log.d("data_notifikasi",remotemsg.getData()+"");
        if (remotemsg.getData().size() > 0) {
            sendnotification(title, body);
        }

    }

    private void sendnotification(String title,String messageBody){
        PendingIntent pendingIntent;
        Intent intent = new Intent(this, MenuUtama.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, id, intent,
                PendingIntent.FLAG_ONE_SHOT);

        getManager();
        create();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,App.ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_genpin_grey))
                .setContentIntent(pendingIntent)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_genpin_grey);
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_genpin_grey);
        }
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);

        notification = notificationBuilder.build();

        notificationManager.notify(id, notification);

    }

    public void create(){
        // create android channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(App.ANDROID_CHANNEL_ID,
                    App.ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            androidChannel.setDescription(ANDROID_CHANNEL_DESC);
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);
            androidChannel.setSound(null,null);
//            if (sound){
//                androidChannel.setSound(Uri.parse(path),attributes);
//            }else {
//                androidChannel.setSound(null,null);
//            }
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(androidChannel);
        }
    }

    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss",new Locale("in","ID"));
        return format.format(date);
    }
    @Override
    public void onNewToken(String token) {
        Log.d("", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}

