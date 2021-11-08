package com.example.mamman.Model;

public class ChiTietDonHang {
    String machitietDH,madonhang,idsp,tenmonan,link_hinh;
    float dongia,soluong;

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(String machitietDH, String madonhang, String idsp, String tenmonan, float dongia, float soluong,String link_hinh) {
        this.machitietDH = machitietDH;
        this.madonhang = madonhang;
        this.idsp = idsp;
        this.tenmonan = tenmonan;
        this.dongia = dongia;
        this.soluong = soluong;
        this.link_hinh= link_hinh;
    }

    public String getLink_hinh() {
        return link_hinh;
    }

    public void setLink_hinh(String link_hinh) {
        this.link_hinh = link_hinh;
    }

    public String getMachitietDH() {
        return machitietDH;
    }

    public void setMachitietDH(String machitietDH) {
        this.machitietDH = machitietDH;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getIdsp() {
        return idsp;
    }

    public void setIdsp(String idsp) {
        this.idsp = idsp;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public float getDongia() {
        return dongia;
    }

    public void setDongia(float dongia) {
        this.dongia = dongia;
    }

    public float getSoluong() {
        return soluong;
    }

    public void setSoluong(float soluong) {
        this.soluong = soluong;
    }
}
