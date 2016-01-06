package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Mert on 16/10/15.
 *
 * This is a parcelable class so it could
 * be serialized and saved or shared across
 * activities.
 */
public class User extends BaseModel implements Parcelable {

    private String email;
    private boolean isRestaurant;
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

    //For Signup
    public User(String email, boolean isRestaurant, String password, String fullName, String location, Date dateOfBirth){
        this.email = email;
        this.isRestaurant = isRestaurant;
        this.password = password;
        this.fullName = fullName;
        this.location = location;
        this.dateOfBirth = dateOfBirth;
    }

    protected User(Parcel in) {
        id = in.readInt();
        email = in.readString();
        password = in.readString();
        fullName = in.readString();
        location = in.readString();
        rating = in.readFloat();
        dateOfBirth = new Date(in.readLong());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRestaurant() {
        return isRestaurant;
    }

    public void setIsRestaurant(boolean isRestaurant) {
        this.isRestaurant = isRestaurant;
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
        dest.writeFloat(rating);
        dest.writeLong(dateOfBirth.getTime());
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
