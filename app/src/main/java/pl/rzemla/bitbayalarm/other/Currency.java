package pl.rzemla.bitbayalarm.other;

public class Currency {

    private int layoutBackgroundRes;
    private int imageSourceRes;
    private String name;

    public Currency(int layoutBackgroundRes, int imageSourceRes, String name) {
        this.layoutBackgroundRes = layoutBackgroundRes;
        this.imageSourceRes = imageSourceRes;
        this.name = name;
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

}
