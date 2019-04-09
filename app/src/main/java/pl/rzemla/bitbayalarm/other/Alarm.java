package pl.rzemla.bitbayalarm.other;

import pl.rzemla.bitbayalarm.enums.AlarmMode;

public class Alarm {
    private String currency;
    private String cryptoCurrency;
    private boolean running;
    private double value;
    private Song song;
    private AlarmMode alarmMode;
    private boolean additionalTracking = false;

    public Alarm(String currency, String cryptoCurrency, boolean running, double value, Song song, AlarmMode alarmMode) {
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.running = running;
        this.value = value;
        this.song = song;
        this.alarmMode = alarmMode;
    }

    public Alarm(String currency, String cryptoCurrency, boolean running, double value, Song song, AlarmMode alarmMode, boolean additionalTracking) {
        this.currency = currency;
        this.cryptoCurrency = cryptoCurrency;
        this.running = running;
        this.value = value;
        this.song = song;
        this.alarmMode = alarmMode;
        this.additionalTracking = additionalTracking;
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


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("========= ALARM =========\n");
        stringBuilder.append("Currency: ").append(currency).append("\n");
        stringBuilder.append("CryptoCurrency: ").append(cryptoCurrency).append("\n");
        stringBuilder.append("Running: ").append(running).append("\n");
        stringBuilder.append("Value: ").append(value).append("\n");
        stringBuilder.append("Song: ").append("---").append("\n");
        stringBuilder.append("AlarmMode: ").append(alarmMode).append("\n");
        stringBuilder.append("AdditionalTracking: ").append(additionalTracking).append("\n");


        return stringBuilder.toString();
    }
}
