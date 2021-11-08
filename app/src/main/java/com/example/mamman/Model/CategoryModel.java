package com.example.mamman.Model;

public class CategoryModel {
    public String ma_cate;
    public String cat_title;
    public String link_hinh;

    public CategoryModel() {

    }

    public CategoryModel(String ma_cate,String link_hinh, String cat_title) {
        this.ma_cate= ma_cate;
        this.link_hinh = link_hinh;
        this.cat_title = cat_title;
    }

    public String getMa_cate() {
        return ma_cate;
    }

    public void setMa_cate(String ma_cate) {
        this.ma_cate = ma_cate;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public String getLink_hinh() {
        return link_hinh;
    }

    public void setLink_hinh(String link_hinh) {
        this.link_hinh = link_hinh;
    }
}
