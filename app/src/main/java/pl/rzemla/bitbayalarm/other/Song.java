package pl.rzemla.bitbayalarm.other;

import android.net.Uri;

import java.io.Serializable;

public class Song implements Serializable {
    private String songUri;
    private String songTitle;
    private boolean vibration;


    public Song(String songUri, String songTitle, boolean vibration) {
        this.songUri = songUri;
        this.songTitle = songTitle;
        this.vibration = vibration;
    }

    public Song() {
        songUri = null;
        songTitle = "default";
        vibration = false;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }

    @Override
    public String toString() {
        return "URI: " + songUri + "\n" +
                        "TITLE: " + songTitle + "\n"+
                        "VIBRATION: " + vibration;
    }
}
