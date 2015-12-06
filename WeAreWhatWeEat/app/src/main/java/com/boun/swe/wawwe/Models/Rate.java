package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Mert on 05/12/15.
 */
public class Rate implements Parcelable {

    private int id;
    private int userId;
    private String type;//(Can be "user" | "recipe" | "menu")
    private int parentId;
    private float rating;
    private Date createdAt;

    public Rate(String type, int parentId, float rating, Date createdAt){
        this.type = type;
        this.parentId = parentId;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    protected Rate(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        type = in.readString();
        parentId = in.readInt();
        rating = in.readFloat();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(type);
        dest.writeInt(parentId);
        dest.writeFloat(rating);
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };
}
