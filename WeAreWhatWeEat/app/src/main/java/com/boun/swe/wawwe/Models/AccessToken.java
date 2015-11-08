package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mert on 03/11/15.
 */
public class AccessToken implements Parcelable {

    private String UUID;
    private int userId;

    public AccessToken(String UUID, int userId) {
        this.UUID = UUID;
        this.userId = userId;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    protected AccessToken(Parcel in) {
        UUID = in.readString();
        userId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UUID);
        dest.writeInt(userId);
    }

    public static final Creator<AccessToken> CREATOR = new Creator<AccessToken>() {
        @Override
        public AccessToken createFromParcel(Parcel in) {
            return new AccessToken(in);
        }

        @Override
        public AccessToken[] newArray(int size) {
            return new AccessToken[size];
        }
    };
}
