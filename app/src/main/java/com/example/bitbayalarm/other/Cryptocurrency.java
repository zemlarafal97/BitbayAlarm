package com.example.bitbayalarm.other;

public class Cryptocurrency {

    private int layoutBackgroundRes;
    private int imageSourceRes;
    private String name;

    public Cryptocurrency(int layoutBackgroundRes, int imageSourceRes, String name) {
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
