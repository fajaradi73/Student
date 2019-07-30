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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fingertech.kesforstudent.Guru.ActivityGuru.PesanMasukDetail;
import com.fingertech.kesforstudent.MainActivity;
import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Sqlite.Notifikasi;
import com.fingertech.kesforstudent.Sqlite.NotifikasiGuru;
import com.fingertech.kesforstudent.Sqlite.NotifikasiGuruTable;
import com.fingertech.kesforstudent.Sqlite.NotifikasiTable;
import com.fingertech.kesforstudent.Student.Activity.AbsenAnak;
import com.fingertech.kesforstudent.Student.Activity.AgendaAnak;
import com.fingertech.kesforstudent.Student.Activity.MenuUtama;
import com.fingertech.kesforstudent.Student.Activity.PesanDetail;
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
    String in,parent_id,member_type;
    JSONObject reader;
    private NotifikasiTable notifikasiTable = new NotifikasiTable();
    private NotifikasiGuruTable notifikasiGuruTable = new NotifikasiGuruTable();
    SharedPreferences sharedPreferences,sharedpreferences;
    String path;
    boolean sound;
    Notification notification;
    NotifikasiGuru notifikasiGuru;
    int id = 0;

    int id_notifikasi = 0;

    @Override
    public void onMessageReceived(RemoteMessage remotemsg) {

        String title       = remotemsg.getData().get("title");
        String body        = remotemsg.getData().get("body");

        Log.d("data_notifikasi",remotemsg.getData()+"");
        if (remotemsg.getData().size() > 0) {
            try {
                reader      = new JSONObject(remotemsg.getData().get("data"));
                in          = reader.getString("type_id");
                member_type = reader.getString("member_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            nada();
            sharedpreferences   = getSharedPreferences(Masuk.my_shared_preferences,Context.MODE_PRIVATE);
            sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
            parent_id = sharedPreferences.getString("member_id", null);
            id_notifikasi   = sharedPreferences.getInt("id_notif",0);
            sendnotification(title, body, in, reader, parent_id);
        }

    }

    private void nada() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        path    = preferences.getString("Ringtone", "");
        sound   = preferences.getBoolean("SW_Notifikasi",false);
        Log.d("pats",sound+"");
        Ringtone ringtone;
        Vibrator vibrator;
        if (!path.isEmpty()) {
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(path));
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(400);
            ringtone.play();
        }else {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(400);
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(path));
            ringtone.stop();
        }
    }


    private void sendnotification(String title,String messageBody,String type,JSONObject jsonObject,String parent_id){
        PendingIntent pendingIntent;
        Notifikasi data;
        id = messageBody.hashCode();
        id_notifikasi++;

        switch (type) {
            case "pesan_siswa": {
                Intent ins = new Intent();
                ins.putExtra("counting","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString("school_code", jsonObject.getString("school_code"));
                    editor.putString("message_id", jsonObject.getString("message_id"));
                    editor.putString("parent_message_id", jsonObject.getString("parent_message_id"));
                    editor.putString("member_id", parent_id);
                    editor.putString("clicked","click");
                    editor.putInt("id_notif", id_notifikasi);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, PesanDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    intent.putExtra("school_code", jsonObject.getString("school_code"));
                    intent.putExtra("message_id", jsonObject.getString("message_id"));
                    intent.putExtra("parent_message_id", jsonObject.getString("parent_message_id"));
                    intent.putExtra("member_id", parent_id);
                    intent.putExtra("clicked","click");
                    intent.putExtra("id_notif",id_notifikasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                try {
                    data = new Notifikasi();
                    data.setId_notifikasi(id_notifikasi);
                    data.setTitle(reader.getString("message_title"));
                    data.setBody(messageBody);
                    data.setMessage_id(reader.getString("message_id"));
                    data.setSchool_code(reader.getString("school_code"));
                    data.setParent_message_id(reader.getString("parent_message_id"));
                    data.setType(type);
                    data.setTime(convertTime(System.currentTimeMillis()));
                    data.setStatus("0");
                    data.setMember_id(parent_id);
                    data.setStudent_id("");
                    data.setClassroom_id("");
                    data.setAgenda_date("");
                    notifikasiTable.insert(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent in = new Intent();
                in.putExtra("pesan_baru","true");
                in.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);

                break;
            }
            case "insert_new_agenda": {
                Intent ins = new Intent();
                ins.putExtra("counting","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString("school_code", jsonObject.getString("school_code"));
                    editor.putString("student_id", jsonObject.getString("student_id"));
                    editor.putString("classroom_id", jsonObject.getString("classroom_id"));
                    editor.putString("calendar", jsonObject.getString("agenda_date"));
                    editor.putString("clicked","click");
                    editor.putInt("id_notif", id_notifikasi);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, AgendaAnak.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    intent.putExtra("school_code", jsonObject.getString("school_code"));
                    intent.putExtra("student_id", jsonObject.getString("student_id"));
                    intent.putExtra("classroom_id", jsonObject.getString("classroom_id"));
                    intent.putExtra("calendar", jsonObject.getString("agenda_date"));
                    intent.putExtra("clicked","click");
                    intent.putExtra("id_notif",id_notifikasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                try {
                    data = new Notifikasi();
                    data.setId_notifikasi(id_notifikasi);
                    data.setTitle("Agenda " + reader.getString("cources_name"));
                    data.setBody(messageBody);
                    data.setMessage_id("");
                    data.setSchool_code(reader.getString("school_code"));
                    data.setParent_message_id("");
                    data.setType(type);
                    data.setTime(convertTime(System.currentTimeMillis()));
                    data.setStatus("0");
                    data.setMember_id(parent_id);
                    data.setStudent_id(jsonObject.getString("student_id"));
                    data.setClassroom_id(jsonObject.getString("classroom_id"));
                    data.setAgenda_date(jsonObject.getString("agenda_date"));
                    notifikasiTable.insert(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "absen_siswa": {
                Intent ins = new Intent();
                ins.putExtra("counting","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString("school_code", jsonObject.getString("school_code"));
                    editor.putString("student_id", jsonObject.getString("student_id"));
                    editor.putString("classroom_id", jsonObject.getString("classroom_id"));
                    editor.putString("clicked","click");
                    editor.putInt("id_notif", id_notifikasi);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, AbsenAnak.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    intent.putExtra("school_code", jsonObject.getString("school_code"));
                    intent.putExtra("student_id", jsonObject.getString("student_id"));
                    intent.putExtra("classroom_id", jsonObject.getString("classroom_id"));
                    intent.putExtra("clicked","click");
                    intent.putExtra("id_notif",id_notifikasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                try {
                    data = new Notifikasi();
                    data.setId_notifikasi(id_notifikasi);
                    data.setTitle(title);
                    data.setBody(messageBody);
                    data.setMessage_id("");
                    data.setSchool_code(reader.getString("school_code"));
                    data.setParent_message_id("");
                    data.setType(type);
                    data.setTime(convertTime(System.currentTimeMillis()));
                    data.setStatus("0");
                    data.setMember_id(parent_id);
                    data.setStudent_id(jsonObject.getString("student_id"));
                    data.setClassroom_id(jsonObject.getString("classroom_id"));
                    data.setAgenda_date("");
                    notifikasiTable.insert(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case "pesan_guru": {
                Intent ins = new Intent();
                ins.putExtra("counting_guru","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString("school_code", jsonObject.getString("school_code"));
                    editor.putString("message_id", jsonObject.getString("message_id"));
                    editor.putString("reply_message_id", jsonObject.getString("repply_message_id"));
                    editor.putString("member_id", parent_id);
                    editor.putString("clicked","click");
                    editor.putInt("id_notif", id_notifikasi);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, PesanMasukDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    intent.putExtra("school_code", jsonObject.getString("school_code"));
                    intent.putExtra("message_id", jsonObject.getString("message_id"));
                    intent.putExtra("reply_message_id", jsonObject.getString("repply_message_id"));
                    intent.putExtra("member_id", parent_id);
                    intent.putExtra("clicked","click");
                    intent.putExtra("id_notif",id_notifikasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                try {
                    notifikasiGuru = new NotifikasiGuru();
                    notifikasiGuru.setId_notifikasi(id_notifikasi);
                    notifikasiGuru.setTitle(reader.getString("message_title"));
                    notifikasiGuru.setBody(messageBody);
                    notifikasiGuru.setMessage_id(reader.getString("message_id"));
                    notifikasiGuru.setSchool_code(reader.getString("school_code"));
                    notifikasiGuru.setParent_message_id(reader.getString("repply_message_id"));
                    notifikasiGuru.setType(type);
                    notifikasiGuru.setTime(convertTime(System.currentTimeMillis()));
                    notifikasiGuru.setStatus("0");
                    notifikasiGuru.setMember_id(parent_id);
                    notifikasiGuru.setStudent_id("");
                    notifikasiGuru.setClassroom_id("");
                    notifikasiGuru.setAgenda_date("");
                    notifikasiGuruTable.insert(notifikasiGuru);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent in = new Intent();
                in.putExtra("pesan_baru","true");
                in.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(in);

                break;
            }
            case "logout": {
                Intent ins = new Intent();
                ins.putExtra("counting","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                Intent intent = new Intent(this, MenuUtama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                break;
            }
            case "insert_new_exam": {
                Intent ins = new Intent();
                ins.putExtra("counting","true");
                ins.setAction("NOW");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(ins);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString("school_code", jsonObject.getString("school_code"));
                    editor.putString("student_id", jsonObject.getString("student_id"));
                    editor.putString("classroom_id", jsonObject.getString("classroom_id"));
                    editor.putString("calendar", jsonObject.getString("exam_date"));
                    editor.putString("clicked","click");
                    editor.putInt("id_notif", id_notifikasi);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(this, AgendaAnak.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    intent.putExtra("school_code", jsonObject.getString("school_code"));
                    intent.putExtra("student_id", jsonObject.getString("student_id"));
                    intent.putExtra("classroom_id", jsonObject.getString("classroom_id"));
                    intent.putExtra("calendar", jsonObject.getString("exam_date"));
                    intent.putExtra("clicked","click");
                    intent.putExtra("id_notif",id_notifikasi);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                try {
                    data = new Notifikasi();
                    data.setId_notifikasi(id_notifikasi);
                    data.setTitle(reader.getString("exam_name") + reader.getString("cources_name"));
                    data.setBody(messageBody);
                    data.setMessage_id("");
                    data.setSchool_code(reader.getString("school_code"));
                    data.setParent_message_id("");
                    data.setType(type);
                    data.setTime(convertTime(System.currentTimeMillis()));
                    data.setStatus("0");
                    data.setMember_id(parent_id);
                    data.setStudent_id(jsonObject.getString("student_id"));
                    data.setClassroom_id(jsonObject.getString("classroom_id"));
                    data.setAgenda_date(jsonObject.getString("exam_date"));
                    notifikasiTable.insert(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            default: {
                Intent intent = new Intent(this, MenuUtama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, id, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                break;
            }
        }
        getManager();
        create();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,App.ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo))
                .setContentIntent(pendingIntent)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(messageBody))
                .setWhen(System.currentTimeMillis());
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_notifikasi);
            notificationBuilder.setColor(ContextCompat.getColor(this,R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_notifikasi);
        }
        if (sound) {
            notificationBuilder.setSound(Uri.parse(path));
        }else {
            notificationBuilder.setSound(null);
            notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        }
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

