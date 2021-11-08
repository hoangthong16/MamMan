package com.example.mamman.Model;

public class GreatOffersModel {
    private int shop_img;
    private String shop_name,time,discount,rating;

    public GreatOffersModel() {
    }

    public GreatOffersModel(int shop_img, String shop_name, String time, String discount, String rating) {
        this.shop_img = shop_img;
        this.shop_name = shop_name;
        this.time = time;
        this.discount = discount;
        this.rating = rating;
    }

    public int getShop_img() {
        return shop_img;
    }

    public void setShop_img(int shop_img) {
        this.shop_img = shop_img;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
