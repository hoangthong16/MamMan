package com.example.mamman.Model;

public class DonHangModel {
    String madonhang,userID,tenkhachhang,sdt,diachi,trangthai;

    public DonHangModel() {
    }

    public DonHangModel(String madonhang,String userID, String tenkhachhang, String sdt, String diachi, String trangthai) {
        this.madonhang = madonhang;
        this.userID=userID;
        this.tenkhachhang = tenkhachhang;
        this.sdt = sdt;
        this.diachi = diachi;
        this.trangthai = trangthai;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
