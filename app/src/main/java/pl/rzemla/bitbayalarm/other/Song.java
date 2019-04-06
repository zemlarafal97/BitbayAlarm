package pl.rzemla.bitbayalarm.other;

public class Song {
    private String songPath;
    private String songTitle;
    boolean vibration;

    public Song(String songPath, String songTitle, boolean vibration) {
        this.songPath = songPath;
        this.songTitle = songTitle;
        this.vibration = vibration;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
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
}
