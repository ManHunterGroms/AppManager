package com.bymotors.android.appmanager.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by bymot on 27.06.2017.
 */

public class AppModel {
    private String name;
    private String desc;
    private long size;
    private Date date;
    private Bitmap icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
