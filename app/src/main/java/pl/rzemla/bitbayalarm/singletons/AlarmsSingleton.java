package pl.rzemla.bitbayalarm.singletons;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import pl.rzemla.bitbayalarm.other.Alarm;

public class AlarmsSingleton {
    private static AlarmsSingleton mInstance;
    private static List<Alarm> list;


    private AlarmsSingleton() {
        list = new ArrayList<>();

        for (Alarm a : getAlarmsList()) {
            System.out.println(a.toString());
        }

    }

    public void saveAlarmsToSharedPrefs(Context mContext) {

        SharedPreferences appSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPreferences.edit();

        prefsEditor.putInt("AlarmListSize", list.size());
        prefsEditor.apply();

        int position = 0;

        for (Alarm a : list) {
            //Save alarms to shared prefs
            Gson gson = new Gson();
            String json = gson.toJson(a);
            prefsEditor.putString("alarm" + position, json);
            position++;
            prefsEditor.apply();

        }

    }

    public boolean isAnyAlarmRunning() {
        for (Alarm a : list) {
            if (a.isRunning()) return true;
        }

        return false;
    }

    public static AlarmsSingleton getInstance(Context mContext) {

        if (mInstance == null) {
            mInstance = new AlarmsSingleton();
            loadAlarmsFromSharedPrefs(mContext);
        }

        return mInstance;
    }

    private static void loadAlarmsFromSharedPrefs(Context mContext) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        int listSize = appSharedPrefs.getInt("AlarmListSize", 0);

        Gson gson = new Gson();

        for (int i = 0; i < listSize; i++) {
            String json = appSharedPrefs.getString("alarm" + i, "");
            Alarm a = gson.fromJson(json, Alarm.class);
            list.add(a);
        }
    }

    public List<Alarm> getAlarmsList() {
        return list;
    }


}
