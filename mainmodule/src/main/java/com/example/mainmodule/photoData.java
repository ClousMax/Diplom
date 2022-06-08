package com.example.mainmodule;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;


public class photoData implements Serializable {
    private String name;
    private String comment;
    private int date;
    private int photo_res;

    public photoData(String name, String comment, int date, int photo_res) {

        this.name = name;
        this.comment = comment;
        this.date = date;
        this.photo_res = photo_res;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getPhoto_res() {
        return this.photo_res;
    }

    public void setPhoto_res(int photo_res) {
        this.photo_res = photo_res;
    }

}