package com.example.mamman.Model;

import java.io.Serializable;

public class MonAnModel implements Serializable {
    public String mamonan,link_hinh,tenmonan,category;
    public float giamgia,danhgia,dongia;
    public boolean yeuthich;

    public MonAnModel() {
    }

    public MonAnModel(String mamonan,String link_hinh, String tenmonan, String category, float giamgia, float danhgia, float dongia,boolean yeuthich) {
        this.mamonan= mamonan;
        this.link_hinh = link_hinh;
        this.tenmonan = tenmonan;
        this.category = category;
        this.giamgia = giamgia;
        this.danhgia = danhgia;
        this.dongia = dongia;
        this.yeuthich=yeuthich;
    }

    public boolean getYeuthich() {
        return yeuthich;
    }

    public void setYeuthich(boolean yeuthich) {
        this.yeuthich = yeuthich;
    }

    public String getMamonan() {
        return mamonan;
    }

    public void setMamonan(String mamonan) {
        this.mamonan = mamonan;
    }

    public String getLink_hinh() {
        return link_hinh;
    }

    public void setLink_hinh(String link_hinh) {
        this.link_hinh = link_hinh;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(float giamgia) {
        this.giamgia = giamgia;
    }

    public float getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(float danhgia) {
        this.danhgia = danhgia;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

}
