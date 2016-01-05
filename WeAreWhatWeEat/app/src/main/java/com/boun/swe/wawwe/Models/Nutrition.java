package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mert on 05/12/15.
 */
public class Nutrition implements Parcelable {

    private int id;
    private float calories;
    private float carbohydrate;
    private float fats;
    private float proteins;
    private float sodium;
    private float fiber;
    private float cholesterol;
    private float sugars;
    private float iron;

    public Nutrition() { }

    public Nutrition(Parcel in) {
        id = in.readInt();
        calories = in.readFloat();
        carbohydrate = in.readFloat();
        fats = in.readFloat();
        proteins = in.readFloat();
        sodium = in.readFloat();
        fiber = in.readFloat();
        cholesterol = in.readFloat();
        sugars = in.readFloat();
        iron = in.readFloat();
    }

    public float[] getNutritionsAsArray() {
        return new float[] {
            calories,
            carbohydrate,
            fats,
            proteins,
            sodium,
            fiber,
            cholesterol,
            sugars,
            iron
        };
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    /**
     * Adds or decrements this nutrition with given amount.
     *
     * @param nutrition
     * @param amount
     */
    public void updateNutrition(Nutrition nutrition, int amount) {
        calories += amount * nutrition.calories;
        carbohydrate += amount * nutrition.carbohydrate;
        fats += amount * nutrition.fats;
        proteins += amount * nutrition.proteins;
        sodium += amount * nutrition.sodium;
        fiber += amount * nutrition.fiber;
        cholesterol += amount * nutrition.cholesterol;
        sugars += amount * nutrition.sugars;
        iron += amount * nutrition.iron;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeFloat(calories);
        dest.writeFloat(carbohydrate);
        dest.writeFloat(fats);
        dest.writeFloat(proteins);
        dest.writeFloat(sodium);
        dest.writeFloat(fiber);
        dest.writeFloat(cholesterol);
        dest.writeFloat(sugars);
        dest.writeFloat(iron);
    }

    public static final Creator<Nutrition> CREATOR = new Creator<Nutrition>() {
        @Override
        public Nutrition createFromParcel(Parcel in) {
            return new Nutrition(in);
        }

        @Override
        public Nutrition[] newArray(int size) {
            return new Nutrition[size];
        }
    };
}
