package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Mert on 31/10/15.
 */
public class Recipe implements Parcelable {

    private String name;
    private String directions;
    private List<Ingredient> ingredients;

    public Recipe(String name, String directions, List<Ingredient> ingredients) {
        this.name = name;
        this.directions = directions;
        this.ingredients = ingredients;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        directions = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(directions);
        dest.writeTypedList(ingredients);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
