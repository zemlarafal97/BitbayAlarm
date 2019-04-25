package pl.rzemla.bitbayalarm.services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.android.volley.RequestQueue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pl.rzemla.bitbayalarm.BitbayRequest;
import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.activities.AlarmActivity;
import pl.rzemla.bitbayalarm.activities.AlarmSettingsActivity;
import pl.rzemla.bitbayalarm.activities.MainActivity;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.interfaces.ServerCallback;
import pl.rzemla.bitbayalarm.other.Alarm;
import pl.rzemla.bitbayalarm.other.Bitbay;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.singletons.AlarmsSingleton;
import pl.rzemla.bitbayalarm.singletons.VolleySingleton;



public class AlarmTrackingService extends Service {
    private List<Alarm> alarmList;
    private RequestQueue mRequestQueue;
    private int identifier;
    private int refreshFrequency;


    private final int PENDING_INTENT_REQUEST_CODE = 10001;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("OnCreate", "AlarmTrackingService");

        alarmList = AlarmsSingleton.getInstance(this).getAlarmsList();
        mRequestQueue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("OnStartCommand", "AlarmTrackingService");

        refreshFrequency = AlarmSettingsActivity.loadRefreshFreqPreference(this);
        showAlarmForegroundNotification();
        setAlarmPendingIntent();
        makeQueries();


        return START_STICKY;
    }

    private void setAlarmPendingIntent() {
        Intent serviceIntent = new Intent(this, AlarmTrackingService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + refreshFrequency * 1000, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + refreshFrequency * 1000, pendingIntent);
        }
    }

    public void sendNotificationNormal(int id, String currency, String cryptocurrency, double last) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String currencySymbol = Resources.getCurrencySymbol(currency);

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(10);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.track_crypto_notification);
        contentView.setTextViewText(R.id.actualExchangeRatingValueTV, cryptocurrency + ": " + df.format(last) + currencySymbol);
        contentView.setTextViewText(R.id.refreshedTimeTV, getString(R.string.Refreshed) + ": " + date);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.dollarico)
                        .setContent(contentView)
                        .setContentText(String.valueOf(last));

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(id,mBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendONotification(int id, String currency, String cryptocurrency, double last) {
        String NOTIFICATION_CHANNEL_ID = "pl.rzemla.bitbayalarm";
        String channelName = "Alarm Tracking Service";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        String currencySymbol = Resources.getCurrencySymbol(currency);

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(10);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.track_crypto_notification);
        contentView.setTextViewText(R.id.actualExchangeRatingValueTV, cryptocurrency + ": " + df.format(last) + currencySymbol);
        contentView.setTextViewText(R.id.refreshedTimeTV, getString(R.string.Refreshed) + ": " + date);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(channel);

        Notification.Builder notificationBuilder = new Notification.Builder(this,NOTIFICATION_CHANNEL_ID);

        Notification notification = notificationBuilder
                .setSmallIcon(R.drawable.dollarico)
                .setCustomContentView(contentView)
                .setContentText(String.valueOf(last))
                .build();

        manager.notify(id,notification);
    }

    private void showNormalForegroundNotification() {
        Intent showTaskIntent = new Intent(this, MainActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(this,PENDING_INTENT_REQUEST_CODE,showTaskIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.alarm_notification);
        contentView.setTextViewText(R.id.descriptionValueTV, getString(R.string.Alarms_working));
        contentView.setTextViewText(R.id.alarmsWorkingDate,date);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.dollarico)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bitbayico256))
                .setCustomContentView(contentView)
                .setContentIntent(contentIntent)
                .build();

        startForeground(PENDING_INTENT_REQUEST_CODE,notification);
    }

    private void sendNotification(int id, String currency, String cryptocurrency, double last) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sendONotification(id, currency, cryptocurrency, last);
        } else {
            sendNotificationNormal(id, currency, cryptocurrency, last);
        }
    }

    private void showAlarmForegroundNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            showOForegroundNotification();
        } else {
            showNormalForegroundNotification();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showOForegroundNotification() {
        Intent showTaskIntent = new Intent(this, MainActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this,PENDING_INTENT_REQUEST_CODE,showTaskIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        String NOTIFICATION_CHANNEL_ID = "pl.rzemla.bitbayalarm";
        String channelName = "Alarm Tracking Service";
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,channelName,NotificationManager.IMPORTANCE_HIGH);
        channel.setLightColor(Color.BLUE);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert manager != null;
        manager.createNotificationChannel(channel);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.alarm_notification);
        contentView.setTextViewText(R.id.descriptionValueTV, getString(R.string.Alarms_working));
        contentView.setTextViewText(R.id.alarmsWorkingDate,date);

        Notification.Builder notificationBuilder = new Notification.Builder(this,NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.dollarico)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.bitbayico256))
                .setPriority(Notification.PRIORITY_MAX)
                .setCustomContentView(contentView)
                .setContentIntent(contentIntent)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();

        startForeground(PENDING_INTENT_REQUEST_CODE,notification);
    }

    private void makeQueries() {

        identifier = 0;

        for (final Alarm a : alarmList) {

            if (a.isRunning()) {
                BitbayRequest.makeBitbayTickerRequest(mRequestQueue, a, new ServerCallback() {
                    @Override
                    public void onSuccess(Bitbay bitbay) {

                        if(a.isAdditionalTracking() || a.getAlarmMode()==AlarmMode.TRACK_RATE) {
                            sendNotification(identifier++,a.getCurrency(),a.getCryptoCurrency(),bitbay.getLast());
                        }

                        if (shouldAlarmRing(a, bitbay) && !isMyServiceRunning(AlarmSoundService.class)) {

                            Intent serviceIntent = new Intent(AlarmTrackingService.this,AlarmTrackingService.class);
                            PendingIntent pendingIntent = PendingIntent.getService(AlarmTrackingService.this, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);


                            Intent alarmSoundIntent = new Intent(AlarmTrackingService.this, AlarmSoundService.class);
                            alarmSoundIntent.putExtra("cryptocurrency", a.getCryptoCurrency());
                            alarmSoundIntent.putExtra("currency", a.getCurrency());
                            alarmSoundIntent.putExtra("value", a.getValue());
                            alarmSoundIntent.putExtra("alarmMode", a.getAlarmMode());
                            alarmSoundIntent.putExtra("song",a.getSong());
                            startService(alarmSoundIntent);

                            Intent alarmActivityIntent = new Intent(AlarmTrackingService.this, AlarmActivity.class);
                            alarmActivityIntent.putExtra("cryptocurrency", a.getCryptoCurrency());
                            alarmActivityIntent.putExtra("currency", a.getCurrency());
                            alarmActivityIntent.putExtra("value", a.getValue());
                            alarmActivityIntent.putExtra("alarmMode", a.getAlarmMode());
                            alarmActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(alarmActivityIntent);

                            a.setRunning(false);


                            stopSelf();
                        }


                    }
                });
            }
        }


    }



    private boolean shouldAlarmRing(Alarm a, Bitbay bitbay) {

        if (a.isAlarmMode(AlarmMode.RISE_ABOVE) && bitbay.getLast() > a.getValue()) {
            Log.d("Alarm rise above", "TRUE");
            return true;
        } else if (a.isAlarmMode(AlarmMode.FALL_BELOW) && bitbay.getLast() < a.getValue()) {
            Log.d("Alarm fall below", "TRUE");
            return true;
        }

        return false;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
