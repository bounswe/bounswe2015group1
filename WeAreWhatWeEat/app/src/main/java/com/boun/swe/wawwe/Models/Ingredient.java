package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mert on 31/10/15.
 */
public class Ingredient implements Parcelable {

    private String name;
    private float amount;
    private int amountType;

    public Ingredient(String name, float amount, int amountType) {
        this.name = name;
        this.amount = amount;
        this.amountType = amountType;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        amount = in.readFloat();
        amountType = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(amount);
        dest.writeInt(amountType);
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
