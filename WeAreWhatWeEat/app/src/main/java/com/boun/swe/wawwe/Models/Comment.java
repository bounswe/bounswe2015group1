package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Mert on 05/12/15.
 */
public class Comment implements Parcelable {

    private int id;
    private int userId;
    private String userFullName;
    private String type;//(Can be "user" | "recipe" | "menu")
    private int parentId;
    private String body;
    private Date createdAt;

    public Comment(String userFullName, String type, int parentId, String body){
        this.userFullName = userFullName;
        this.type = type;
        this.parentId = parentId;
        this.body = body;
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        userFullName = in.readString();
        type = in.readString();
        parentId = in.readInt();
        body = in.readString();
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

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
        dest.writeString(userFullName);
        dest.writeString(type);
        dest.writeInt(parentId);
        dest.writeString(body);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
