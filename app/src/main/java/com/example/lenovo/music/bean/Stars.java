package com.example.lenovo.music.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/7/18.
 */

public class Stars implements Serializable {
    private String name;
    private String id;
    private String Image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
