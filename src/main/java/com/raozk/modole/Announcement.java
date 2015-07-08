package com.raozk.modole;

import java.util.Date;

/**
 * Created by raozhikun on 15/7/8.
 */
public class Announcement {

    private int id;
    private String title;
    private String content;
    private String band;
    private String type;
    private String time;
    private Date st = new Date();

    public Announcement(){}

    public Announcement(String title, String content, String band, String type, String time){
        this.title = title;
        this.content = content;
        this.band = band;
        this.type = type;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getSt() {
        return st;
    }

    public void setSt(Date st) {
        this.st = st;
    }
}