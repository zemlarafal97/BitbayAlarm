package pl.rzemla.bitbayalarm.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.RequestQueue;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.other.Bitbay;
import pl.rzemla.bitbayalarm.singletons.VolleySingleton;
import pl.rzemla.bitbayalarm.widget.WidgetTicker;
import pl.rzemla.bitbayalarm.widget.WidgetTickerConfigureActivity;

public class UpdateService extends Service {
    private RequestQueue mRequestQueue;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("onStartCommand","UpdateService");

        setAlarmPendingIntent();
        makeQueries();


        return START_STICKY;
    }

    private void setAlarmPendingIntent() {
        Intent serviceIntent = new Intent(this, UpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20 * 1000, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30 * 1000, pendingIntent);
        }
    }

    private void makeQueries() {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(this, WidgetTicker.class);
        int []IDs = AppWidgetManager.getInstance(this).getAppWidgetIds(name);

        for(final int id: IDs) {

            String url = WidgetTickerConfigureActivity.loadUrlPref(this,id);
            final String currency = WidgetTickerConfigureActivity.loadCurrencyPreference(this,id);

            BitbayRequest.makeBitbayTickerRequest(mRequestQueue, url, new ServerCallback() {
                @Override
                public void onSuccess(Bitbay bitbay) {
                    WidgetTicker.setWidgetValues(UpdateService.this,appWidgetManager,id,bitbay.getLast(),currency);
                }
            });


        }



    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = VolleySingleton.getInstance(this).getRequestQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy","UpdateService");
    }
}

