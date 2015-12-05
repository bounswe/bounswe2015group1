package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mert on 31/10/15.
 */
public class Ingredient implements Parcelable {

    private String ingredientId = "PlaceHolder";
    private String name;
    private int amount;
    private Nutrition nutritions;

    public Ingredient(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    protected Ingredient(Parcel in) {
        ingredientId = in.readString();
        name = in.readString();
        amount = in.readInt();
        nutritions = in.readParcelable(Nutrition.class.getClassLoader());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredientId);
        dest.writeString(name);
        dest.writeFloat(amount);
        dest.writeParcelable(nutritions, flags);
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
