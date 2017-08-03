package com.example.lenovo.music.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/7/18.
 */

public class Movie implements Serializable{
    private String title;
    private String director;
    private String casts;
    private String rating;
    private String sawNum;
    private String posterUrl;
    private String type;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSawNum() {
        return sawNum;
    }

    public void setSawNum(String sawNum) {
        this.sawNum = sawNum;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
