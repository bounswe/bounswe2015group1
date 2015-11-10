package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    // fullName...

    private int id;
    private String email;
    private String password;
    private String fullName;
    private String location;
    private Date dateOfBirth;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id, String email, String password, String fullName, String location, Date dateOfBirth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
    }

    public User update(User user) {
        this.email = user.email;
        this.fullName = user.fullName;
        this.location = user.location;
        this.dateOfBirth = user.dateOfBirth;

        return this;
    }

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        password = in.readString();
        fullName = in.readString();
        location = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(fullName);
        dest.writeString(location);
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
