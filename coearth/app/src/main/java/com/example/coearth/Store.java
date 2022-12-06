package com.example.coearth;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Store {
    private String img;
    private String title;
    private String category;
    private String detail;
    private String score;
    private Double lat;
    private Double lng;
    private Double rat;
    private String tel;
    private String views;

    public Store() {}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){ this.category = category; }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getRat(){ return rat; }

    public void setRat(Double rat){ this.rat = rat; }

    public String getTel(){ return tel; }

    public void setTel(String tel){ this.tel = tel; }

    public String getViews(){ return views; }

    public void setViews(String views){ this.tel = views; }
}
