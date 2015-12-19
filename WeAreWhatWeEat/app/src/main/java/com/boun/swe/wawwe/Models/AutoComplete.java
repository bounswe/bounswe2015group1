package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mert on 19/12/15.
 */
public class AutoComplete implements Parcelable {

    private String id;
    private String text;

    public AutoComplete() { }

    protected AutoComplete(Parcel in) {
        id = in.readString();
        text = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(text);
    }

    public static final Creator<AutoComplete> CREATOR = new Creator<AutoComplete>() {
        @Override
        public AutoComplete createFromParcel(Parcel in) {
            return new AutoComplete(in);
        }

        @Override
        public AutoComplete[] newArray(int size) {
            return new AutoComplete[size];
        }
    };

    @Override
    public String toString() {
        return getText();
    }
}
