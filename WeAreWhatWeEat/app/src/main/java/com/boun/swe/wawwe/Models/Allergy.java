package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.boun.swe.wawwe.App;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akintoksan on 05/01/16.
 */
public class Allergy implements Parcelable {

    int userId;
    @SerializedName("ingredientId")
    String ingredientName;

    public Allergy() { }

    public Allergy(String ingredientName) {
        userId = App.getUserId();
        this.ingredientName = ingredientName;
    }

    public Allergy(int userId, String ingredientName) {
        this.userId = userId;
        this.ingredientName = ingredientName;
    }

    protected Allergy(Parcel in) {
        userId = in.readInt();
        ingredientName = in.readString();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(ingredientName);
    }

    public static final Creator<Allergy> CREATOR = new Creator<Allergy>() {
        @Override
        public Allergy createFromParcel(Parcel in) {
            return new Allergy(in);
        }

        @Override
        public Allergy[] newArray(int size) {
            return new Allergy[size];
        }
    };
}
