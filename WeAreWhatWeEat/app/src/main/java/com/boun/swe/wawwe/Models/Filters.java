package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by onurguler on 06/01/16.
 */
public class Filters implements Parcelable{

    private float minRating;
    private float maxRating;
    private String period;
    private String wantedIngredients;
    private String notWantedIngredients;
    private float minCalories;
    private float maxCalories;
    private float minCarbohydrate;
    private float maxCarbohydrate;
    private float minFats;
    private float maxFats;
    private float minProteins;
    private float maxProteins;
    private float minSodium;
    private float maxSodium;
    private float minFiber;
    private float maxFiber;
    private float minCholesterol;
    private float maxCholesterol;
    private float minSugars;
    private float maxSugars;
    private float minIron;
    private float maxIron;

    protected Filters(Parcel in) {
        minRating = in.readFloat();
        maxRating = in.readFloat();
        period = in.readString();
        wantedIngredients = in.readString();
        notWantedIngredients = in.readString();
        minCalories = in.readFloat();
        maxCalories = in.readFloat();
        minCarbohydrate = in.readFloat();
        maxCarbohydrate = in.readFloat();
        minFats = in.readFloat();
        maxFats = in.readFloat();
        minProteins = in.readFloat();
        maxProteins = in.readFloat();
        minSodium = in.readFloat();
        maxSodium = in.readFloat();
        minFiber = in.readFloat();
        maxFiber = in.readFloat();
        minCholesterol = in.readFloat();
        maxCholesterol = in.readFloat();
        minSugars = in.readFloat();
        maxSugars = in.readFloat();
        minIron = in.readFloat();
        maxIron = in.readFloat();
    }

    public static final Creator<Filters> CREATOR = new Creator<Filters>() {
        @Override
        public Filters createFromParcel(Parcel in) {
            return new Filters(in);
        }

        @Override
        public Filters[] newArray(int size) {
            return new Filters[size];
        }
    };

    public float getMinRating() {
        return minRating;
    }

    public void setMinRating(float minRating) {
        this.minRating = minRating;
    }

    public float getMaxRating() {
        return maxRating;
    }

    public void setMaxRating(float maxRating) {
        this.maxRating = maxRating;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getWantedIngredients() {
        return wantedIngredients;
    }

    public void setWantedIngredients(String wantedIngredients) {
        this.wantedIngredients = wantedIngredients;
    }

    public String getNotWantedIngredients() {
        return notWantedIngredients;
    }

    public void setNotWantedIngredients(String notWantedIngredients) {
        this.notWantedIngredients = notWantedIngredients;
    }

    public float getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(float minCalories) {
        this.minCalories = minCalories;
    }

    public float getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(float maxCalories) {
        this.maxCalories = maxCalories;
    }

    public float getMinCarbohydrate() {
        return minCarbohydrate;
    }

    public void setMinCarbohydrate(float minCarbohydrate) {
        this.minCarbohydrate = minCarbohydrate;
    }

    public float getMaxCarbohydrate() {
        return maxCarbohydrate;
    }

    public void setMaxCarbohydrate(float maxCarbohydrate) {
        this.maxCarbohydrate = maxCarbohydrate;
    }

    public float getMinFats() {
        return minFats;
    }

    public void setMinFats(float minFats) {
        this.minFats = minFats;
    }

    public float getMaxFats() {
        return maxFats;
    }

    public void setMaxFats(float maxFats) {
        this.maxFats = maxFats;
    }

    public float getMinProteins() {
        return minProteins;
    }

    public void setMinProteins(float minProteins) {
        this.minProteins = minProteins;
    }

    public float getMaxProteins() {
        return maxProteins;
    }

    public void setMaxProteins(float maxProteins) {
        this.maxProteins = maxProteins;
    }

    public float getMinSodium() {
        return minSodium;
    }

    public void setMinSodium(float minSodium) {
        this.minSodium = minSodium;
    }

    public float getMaxSodium() {
        return maxSodium;
    }

    public void setMaxSodium(float maxSodium) {
        this.maxSodium = maxSodium;
    }

    public float getMinFiber() {
        return minFiber;
    }

    public void setMinFiber(float minFiber) {
        this.minFiber = minFiber;
    }

    public float getMaxFiber() {
        return maxFiber;
    }

    public void setMaxFiber(float maxFiber) {
        this.maxFiber = maxFiber;
    }

    public float getMinCholesterol() {
        return minCholesterol;
    }

    public void setMinCholesterol(float minCholesterol) {
        this.minCholesterol = minCholesterol;
    }

    public float getMaxCholesterol() {
        return maxCholesterol;
    }

    public void setMaxCholesterol(float maxCholesterol) {
        this.maxCholesterol = maxCholesterol;
    }

    public float getMinSugars() {
        return minSugars;
    }

    public void setMinSugars(float minSugars) {
        this.minSugars = minSugars;
    }

    public float getMaxSugars() {
        return maxSugars;
    }

    public void setMaxSugars(float maxSugars) {
        this.maxSugars = maxSugars;
    }

    public float getMinIron() {
        return minIron;
    }

    public void setMinIron(float minIron) {
        this.minIron = minIron;
    }

    public float getMaxIron() {
        return maxIron;
    }

    public void setMaxIron(float maxIron) {
        this.maxIron = maxIron;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(minRating);
        dest.writeFloat(maxRating);
        dest.writeString(period);
        dest.writeString(wantedIngredients);
        dest.writeString(notWantedIngredients);
        dest.writeFloat(minCalories);
        dest.writeFloat(maxCalories);
        dest.writeFloat(minCarbohydrate);
        dest.writeFloat(maxCarbohydrate);
        dest.writeFloat(minFats);
        dest.writeFloat(maxFats);
        dest.writeFloat(minProteins);
        dest.writeFloat(maxProteins);
        dest.writeFloat(minSodium);
        dest.writeFloat(maxSodium);
        dest.writeFloat(minFiber);
        dest.writeFloat(maxFiber);
        dest.writeFloat(minCholesterol);
        dest.writeFloat(maxCholesterol);
        dest.writeFloat(minSugars);
        dest.writeFloat(maxSugars);
        dest.writeFloat(minIron);
        dest.writeFloat(maxIron);
    }
}
