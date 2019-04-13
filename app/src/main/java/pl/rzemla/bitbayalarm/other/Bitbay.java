package pl.rzemla.bitbayalarm.other;

public class Bitbay {
    private double last;
    private double max24h;
    private double min24h;
    private double volume;


    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getMax24h() {
        return max24h;
    }

    public void setMax24h(double max24h) {
        this.max24h = max24h;
    }

    public double getMin24h() {
        return min24h;
    }

    public void setMin24h(double min24h) {
        this.min24h = min24h;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }


    @Override
    public String toString() {


        String result = "LAST: " +
                this.last +
                "\n" +
                "MAX24H: " +
                this.max24h +
                "\n" +
                "MIN24H: " +
                this.min24h +
                "\n" +
                "VOLUME: " +
                this.volume +
                "\n";
        return result;
    }
}
