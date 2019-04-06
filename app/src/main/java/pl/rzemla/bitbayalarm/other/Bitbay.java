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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LAST: ");
        stringBuilder.append(this.last);
        stringBuilder.append("\n");
        stringBuilder.append("MAX24H: ");
        stringBuilder.append(this.max24h);
        stringBuilder.append("\n");
        stringBuilder.append("MIN24H: ");
        stringBuilder.append(this.min24h);
        stringBuilder.append("\n");
        stringBuilder.append("VOLUME: ");
        stringBuilder.append(this.volume);
        stringBuilder.append("\n");


        return stringBuilder.toString();
    }
}
