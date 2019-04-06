package pl.rzemla.bitbayalarm.other;

public class Cryptocurrency {

    private int layoutBackgroundRes;
    private int imageSourceRes;
    private String name;
    private boolean clicked = false;

    public Cryptocurrency(int layoutBackgroundRes, int imageSourceRes, String name, boolean clicked) {
        this.layoutBackgroundRes = layoutBackgroundRes;
        this.imageSourceRes = imageSourceRes;
        this.name = name;
        this.clicked = clicked;
    }

    public int getLayoutBackgroundRes() {
        return layoutBackgroundRes;
    }

    public void setLayoutBackgroundRes(int layoutBackgroundRes) {
        this.layoutBackgroundRes = layoutBackgroundRes;
    }

    public int getImageSourceRes() {
        return imageSourceRes;
    }

    public void setImageSourceRes(int imageSourceRes) {
        this.imageSourceRes = imageSourceRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
