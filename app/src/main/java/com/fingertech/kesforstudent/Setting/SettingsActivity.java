package com.fingertech.kesforstudent.Setting;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;


import com.fingertech.kesforstudent.Masuk;
import com.fingertech.kesforstudent.R;
import com.fingertech.kesforstudent.Sqlite.Sound;
import com.fingertech.kesforstudent.Sqlite.SoundTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class  SettingsActivity extends  PreferenceFragment{

    SharedPreferences sharedPreferences;
    static String sound;
    ArrayList<HashMap<String, String>> row;
    static SoundTable soundTable = new SoundTable();
    Sound sounds = new Sound();
    CardView iv_close;
    View view;
    WebView tv_privacy;
    Preference privacy,versi;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        SwitchPreference RingtoneSwitch = (SwitchPreference) findPreference("SW_Notifikasi");
        SwitchPreference vibrateSwitch  = (SwitchPreference) findPreference("key_vibrate");
        RingtonePreference ringtonePreference = (RingtonePreference)findPreference("Ringtone");
        privacy = findPreference("privacy");
        versi = findPreference("versi");

        bindPreferenceSummaryToValue(ringtonePreference);

        sharedPreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        sound             = sharedPreferences.getString("sounds","");

        if (vibrateSwitch != null) {
            vibrateSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object isVibrateOnObject) {
                    boolean isVibrateOn = (Boolean) isVibrateOnObject;
                    if (isVibrateOn) {
                        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                        v.vibrate(400);
                    }else {
                        Vibrator v=(Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(0);
                    }
                    return true;
                }
            });
        }

        if (RingtoneSwitch != null){
            RingtoneSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String path = preferences.getString("Ringtone", "");
                    boolean checked = (boolean) newValue;
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(path));
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("SW_Notifikasi",checked);
                    editor.apply();

                    row = soundTable.getAllData();
                    if (row.size() <= 0){
                        sounds.setName(path);
                        soundTable.insert(sounds);
                        sound  = "";
                    }else {
                        sound = (Objects.requireNonNull(row.get(0).get(Sound.KEY_Name)));
                    }

                    if (checked) {
                        SharedPreferences.Editor editorsound = preferences.edit();
                        editorsound.putString("Ringtone",sound);
                        editorsound.apply();
                        Log.d("On","oke");
                        if (!path.isEmpty()){
                            ringtone.play();
                        }
                    }else {
                        Log.d("On","off");
                        SharedPreferences.Editor editorsound = preferences.edit();
                        editorsound.putString("Ringtone","");
                        editorsound.apply();
                        String off = preferences.getString("Ringtone", null);
                        Ringtone ringtoneoff= RingtoneManager.getRingtone(preference.getContext(),Uri.parse(off));
                        if (!off.isEmpty()){
                            ringtoneoff.stop();
                        }else {
                            ringtoneoff.stop();
                        }
                    }

                    return true;
                }
            });
        }
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pInfo != null) {
            versi.setSummary(pInfo.versionName);
        }

        privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                privacy_policy();
                return true;
            }
        });

    }
    private void privacy_policy(){

        view        = getActivity().getLayoutInflater().inflate(R.layout.privacy_policy,null);
        tv_privacy  = view.findViewById(R.id.wv_privacy);
        iv_close    = view.findViewById(R.id.iv_close);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        tv_privacy.loadUrl("file:///android_asset/privacy-policy.html");

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    public  static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(R.string.summary_choose_ringtone);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        soundTable.updateName(sound, name);
                        preference.setSummary(name);
                    }
                }

            } else if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("key_gallery_name")) {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue);
                }
            }
            else {
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


}