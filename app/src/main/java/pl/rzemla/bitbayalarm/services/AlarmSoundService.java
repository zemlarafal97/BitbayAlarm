package pl.rzemla.bitbayalarm.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import pl.rzemla.bitbayalarm.R;
import pl.rzemla.bitbayalarm.activities.AlarmActivity;
import pl.rzemla.bitbayalarm.activities.MainActivity;
import pl.rzemla.bitbayalarm.enums.AlarmMode;
import pl.rzemla.bitbayalarm.other.Resources;
import pl.rzemla.bitbayalarm.other.Song;

public class AlarmSoundService extends Service {
    private MediaPlayer player;
    private Vibrator vibrator;
    private Song song;

    private final int PENDING_INTENT_REQUEST_CODE = 10000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void preparePlayer() {

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        player.setLooping(true);

        try {
            player.setDataSource(this, Uri.parse(song.getSongUri()));
        } catch (Exception e) {
            try {
                player.setDataSource(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        player.prepareAsync();
    }

    private void prepareVibrator() {
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    private String getAlarmModeText(AlarmMode mode) {
        if(mode == AlarmMode.FALL_BELOW) {
            return getResources().getString(R.string.has_fallen_below);
        } else if(mode == AlarmMode.RISE_ABOVE) {
           return getResources().getString(R.string.has_risen_above);
        }
        return "-";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        song = (Song) intent.getSerializableExtra("song");
        String cryptocurrency = intent.getStringExtra("cryptocurrency");
        String currency = intent.getStringExtra("currency");
        double value = intent.getDoubleExtra("value", 0.0);
        AlarmMode mode = (AlarmMode) intent.getSerializableExtra("alarmMode");


        preparePlayer();
        prepareVibrator();
        showAlarmForegroundNotification(cryptocurrency,getAlarmModeText(mode),value, Resources.getCurrencySymbol(currency));

        final long[] mVibratePattern = new long[]{300, 1000, 800};

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                if(song.isVibration()) {
                    vibrator.vibrate(mVibratePattern,0);
                }
            }
        });


        return START_STICKY;
    }

    private void showAlarmForegroundNotification(String cryptocurrency, String alarmType, double limit, String currencySymbol) {
        Intent showTaskIntent = new Intent(this, MainActivity.class);
        showTaskIntent.setAction(Intent.ACTION_MAIN);
        showTaskIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        showTaskIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(this,PENDING_INTENT_REQUEST_CODE,showTaskIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        df.setMaximumFractionDigits(10);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.turn_off_alarm_notification);
        contentView.setTextViewText(R.id.alarmTV,getString(R.string.Alarm_title));
        contentView.setTextViewText(R.id.descriptionValueTV, cryptocurrency + " " +alarmType + " " + df.format(limit) + currencySymbol);

        Intent turnOffAlarmIntent = new Intent(this,TurnOffAlarm.class);
        PendingIntent turnOffAlarmNotificationIntent = PendingIntent.getBroadcast(this,PENDING_INTENT_REQUEST_CODE,turnOffAlarmIntent,0);
        contentView.setOnClickPendingIntent(R.id.turnOffAlarmNotifB,turnOffAlarmNotificationIntent);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.dollarico)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.bitbayico256))
                .setCustomContentView(contentView)
                .setContentIntent(contentIntent)
                .build();
        startForeground(PENDING_INTENT_REQUEST_CODE, notification);

    }

    public static class TurnOffAlarm extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TurnOffAlarm","received!");
            context.stopService(new Intent(context,AlarmSoundService.class));
        }
    }


    @Override
    public void onDestroy() {

        if (player != null) {
            player.stop();
        }

        if(vibrator != null) {
            vibrator.cancel();
        }

        super.onDestroy();
    }

}
