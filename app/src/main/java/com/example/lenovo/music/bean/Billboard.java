package com.example.lenovo.music.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/7/26.
 */

public class Billboard implements Serializable{
    private int type;
    private String billboard_no;
    private String name;
    private String comment;
    private String picUrl;
    private String updateDate;
    private String billboard_songnum;
    private List<Song> songList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBillboard_no() {
        return billboard_no;
    }

    public void setBillboard_no(String billboard_no) {
        this.billboard_no = billboard_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getBillboard_songnum() {
        return billboard_songnum;
    }

    public void setBillboard_songnum(String billboard_songnum) {
        this.billboard_songnum = billboard_songnum;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
