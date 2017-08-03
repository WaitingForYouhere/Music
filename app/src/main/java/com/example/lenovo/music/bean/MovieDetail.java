package com.example.lenovo.music.bean;

/**
 * Created by lenovo on 2017/8/2.
 */

public class MovieDetail {
    private String name;
    private String originName;
    private String average;//评分
    private String comments_count;
    private String type;
    private String year;
    private String countries;
    private String[] piclist;
    private String[] starUrllist;
    private String imageUrl;
    private String summary;
    private String mobileUrl;

    public String[] getStarUrllist() {
        return starUrllist;
    }

    public void setStarUrllist(String[] starUrllist) {
        this.starUrllist = starUrllist;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String[] getPiclist() {
        return piclist;
    }

    public void setPiclist(String[] piclist) {
        this.piclist = piclist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
