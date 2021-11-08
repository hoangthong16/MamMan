package com.example.mamman.Model;

public class MonAnBanChayModel {
    public String mamonan,link_hinh,tenmonan;
    public float giamgia,dongia,danhgia;

    public MonAnBanChayModel() {
    }

    public MonAnBanChayModel(String mamonan,String link_hinh, String tenmonan, float giamgia, float dongia, float danhgia) {
        this.mamonan=mamonan;
        this.link_hinh = link_hinh;
        this.tenmonan = tenmonan;
        this.giamgia = giamgia;
        this.dongia = dongia;
        this.danhgia = danhgia;
    }

    public String getMamonan() {
        return mamonan;
    }

    public void setMamonan(String mamonan) {
        this.mamonan = mamonan;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public String getLink_hinh() {
        return link_hinh;
    }

    public void setLink_hinh(String link_hinh) {
        this.link_hinh = link_hinh;
    }

    public float getGiamgia() {
        return giamgia;
    }
    public String getGiamgiaformat(){

        return giamgia*100 + " %";
    }


    public void setGiamgia(float giamgia) {
        this.giamgia = giamgia;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public float getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(float danhgia) {
        this.danhgia = danhgia;
    }
}
