package com.example.mamman.Model;


import java.util.Date;

public class DanhGia {
    String id;
    String id_User;
    String id_MonAn;
    String ten;
    String noiDung;
    float star;
    Long time;

    public DanhGia() {
    }

    public DanhGia(String id, String id_User, String id_MonAn, String ten, String noiDung, float star, Long time) {
        this.id = id;
        this.id_User = id_User;
        this.id_MonAn = id_MonAn;
        this.ten = ten;
        this.noiDung = noiDung;
        this.star = star;
        this.noiDung = noiDung;
        this.star = star;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getId_MonAn() {
        return id_MonAn;
    }

    public void setId_MonAn(String id_MonAn) {
        this.id_MonAn = id_MonAn;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
