package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mert on 16/10/15.
 *
 * This is a parcelable class so it could
 * be serialized and saved or shared across
 * activities.
 */
public class User implements Parcelable {

    // Service has saved user names with
    // "username" string this is to meet
    // that usage and also java conventions
    // userName...
    @SerializedName("username")
    private String userName;
    private String password;

    public User(String userName, String password) {
        setUserName(userName);
        setPassword(password);
    }

    protected User(Parcel in) {
        userName = in.readString();
        password = in.readString();
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
