package pl.rzemla.bitbayalarm.other;

import java.io.Serializable;

import pl.rzemla.bitbayalarm.enums.AlarmMode;

public class Alarm implements Serializable{
    private String currency;
    private String cryptoCurrency;
    private boolean running;
    private double value;
    private Song song;
    private AlarmMode alarmMode;
    private static int refreshTime = 60;
    private boolean additionalTracking = false;

    public Alarm(String currency, String cryptoCurrency, boolean running, double value, Song song, AlarmMode alarmMode, int refreshTime) {
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.running = running;
        this.value = value;
        this.song = song;
        this.alarmMode = alarmMode;
        Alarm.refreshTime = refreshTime;
    }

    public Alarm(String currency, String cryptoCurrency, boolean running, double value, Song song, AlarmMode alarmMode,int refreshTime, boolean additionalTracking) {
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.running = running;
        this.value = value;
        this.song = song;
        this.alarmMode = alarmMode;
        this.additionalTracking = additionalTracking;
        Alarm.refreshTime = refreshTime;
    }

    public boolean isAdditionalTracking() {
        return additionalTracking;
    }

    public void setAdditionalTracking(boolean additionalTracking) {
        this.additionalTracking = additionalTracking;
    }

    public static int getRefreshTime() {
        return refreshTime;
    }

    public static void setRefreshTime(int refreshTime) {
        Alarm.refreshTime = refreshTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCryptoCurrency() {
        return cryptoCurrency;
    }

    public void setCryptoCurrency(String cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public AlarmMode getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(AlarmMode alarmMode) {
        this.alarmMode = alarmMode;
    }

    public boolean isAlarmMode(AlarmMode alarmMode) {
        return this.alarmMode == alarmMode;
    }


    @Override
    public String toString() {


        String result = "========= ALARM =========\n" +
                "Currency: " + currency + "\n" +
                "CryptoCurrency: " + cryptoCurrency + "\n" +
                "Running: " + running + "\n" +
                "Value: " + value + "\n" +
                "Song: " + "---" + "\n" +
                "AlarmMode: " + alarmMode + "\n" +
                "AdditionalTracking: " + additionalTracking + "\n" +
                "Refresh time: " + refreshTime + "\n";

        result += "\n";
        result += song.toString();

        return result;
    }
}
