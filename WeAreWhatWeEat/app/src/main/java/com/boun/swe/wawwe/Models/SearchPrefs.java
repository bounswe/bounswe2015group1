package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by onurguler on 06/01/16.
 */
public class SearchPrefs implements Parcelable {

    private static float MIN = 0;
    private static float MAX = 100000;
    private static float MAX_RATING = 5;

    private boolean advanceSearchSelected = false;
    private boolean includeRecipes = true;
    private boolean includeMenus = true;
    private float minRating = MIN;
    private float maxRating = MAX_RATING;
    private String period;
    private String includedIngredients;
    private String excludedIngredients;
    private float minCalories = MIN;
    private float maxCalories = MAX;
    private float minCarbohydrate = MIN;
    private float maxCarbohydrate = MAX;
    private float minFats = MIN;
    private float maxFats = MAX;
    private float minProteins = MIN;
    private float maxProteins = MAX;
    private float minSodium = MIN;
    private float maxSodium = MAX;
    private float minFiber = MIN;
    private float maxFiber = MAX;
    private float minCholesterol = MIN;
    private float maxCholesterol = MAX;
    private float minSugars = MIN;
    private float maxSugars = MAX;
    private float minIron = MIN;
    private float maxIron = MAX;

    public SearchPrefs() {}

    protected SearchPrefs(Parcel in) {
        advanceSearchSelected = in.readInt() == 1;
        includeRecipes = in.readInt() == 1;
        includeMenus = in.readInt() == 1;
        minRating = in.readFloat();
        maxRating = in.readFloat();
        period = in.readString();
        includedIngredients = in.readString();
        excludedIngredients = in.readString();
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

    public boolean isAdvanceSearchSelected() {
        return advanceSearchSelected;
    }

    public void setAdvanceSearchSelected(boolean advanceSearchSelected) {
        this.advanceSearchSelected = advanceSearchSelected;
    }

    public boolean isIncludeRecipes() {
        return includeRecipes;
    }

    public void setIncludeRecipes(boolean includeRecipes) {
        this.includeRecipes = includeRecipes;
    }

    public boolean isIncludeMenus() {
        return includeMenus;
    }

    public void setIncludeMenus(boolean includeMenus) {
        this.includeMenus = includeMenus;
    }

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

    public String getIncludedIngredients() {
        return includedIngredients;
    }

    public void setIncludedIngredients(String includedIngredients) {
        this.includedIngredients = includedIngredients;
    }

    public String getExcludedIngredients() {
        return excludedIngredients;
    }

    public void setExcludedIngredients(String excludedIngredients) {
        this.excludedIngredients = excludedIngredients;
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

    public float[] getLimitsAsArray() {
        return new float[] { minRating,maxRating,minCalories,maxCalories,
                minCarbohydrate,maxCarbohydrate,minFats,maxFats,minProteins,maxProteins,minSodium,maxSodium,
                minFiber,maxFiber,minCholesterol,maxCholesterol,minSugars,maxSugars,minIron,maxIron };
    }

    public void setLimitsWithArray(float[] limits) {
        minRating = limits[0];
        maxRating = limits[1];
        minCalories = limits[2];
        maxCalories = limits[3];
        minCarbohydrate = limits[4];
        maxCarbohydrate = limits[5];
        minFats = limits[6];
        maxFats = limits[7];
        minProteins = limits[8];
        maxProteins = limits[9];
        minSodium = limits[10];
        maxSodium = limits[11];
        minFiber = limits[12];
        maxFiber = limits[13];
        minCholesterol = limits[14];
        maxCholesterol = limits[15];
        minSugars = limits[16];
        maxSugars = limits[17];
        minIron = limits[18];
        maxIron = limits[19];
    }

    public Map<String, String> getFiltersAsParams() {
        Map<String, String> params = new HashMap<>();

        if (period != null && !period.equals(""))
            params.put("period", period);
        if (includedIngredients != null && !includedIngredients.equals(""))
            params.put("wantedIngredients", includedIngredients);
        if (excludedIngredients != null && !excludedIngredients.equals(""))
            params.put("notWantedIngredients", excludedIngredients);

        if (minRating > MIN && minRating < MAX_RATING)
            params.put("minRating", Float.toString(minRating));
        if (maxRating > MIN && maxRating < MAX_RATING)
            params.put("maxRating", Float.toString(maxRating));

        if (minCalories > MIN && minCalories < MAX)
            params.put("minCalories", Float.toString(minCalories));
        if (maxCalories > MIN && maxCalories < MAX)
            params.put("maxCalories", Float.toString(maxCalories));

        if (minCarbohydrate > MIN && minCarbohydrate < MAX)
            params.put("minCarbohydrate", Float.toString(minCarbohydrate));
        if (maxCarbohydrate > MIN && maxCarbohydrate < MAX)
            params.put("maxCarbohydrate", Float.toString(maxCarbohydrate));

        if (minFats > MIN && minFats < MAX)
            params.put("minFats", Float.toString(minFats));
        if (maxFats > MIN && maxFats < MAX)
            params.put("maxFats", Float.toString(maxFats));

        if (minProteins > MIN && minProteins < MAX)
            params.put("minProteins", Float.toString(minProteins));
        if (maxProteins > MIN && maxProteins < MAX)
            params.put("maxProteins", Float.toString(maxProteins));

        if (minSodium > MIN && minSodium < MAX)
            params.put("minSodium", Float.toString(minSodium));
        if (maxSodium > MIN && maxSodium < MAX)
            params.put("maxSodium", Float.toString(maxSodium));

        if (minFiber > MIN && minFiber < MAX)
            params.put("minFiber", Float.toString(minFiber));
        if (maxFiber > MIN && maxFiber < MAX)
            params.put("maxFiber", Float.toString(maxFiber));

        if (minCholesterol > MIN && minCholesterol < MAX)
            params.put("minCholesterol", Float.toString(minCholesterol));
        if (maxCholesterol > MIN && maxCholesterol < MAX)
            params.put("maxCholesterol", Float.toString(maxCholesterol));

        if (minSugars > MIN && minSugars < MAX)
            params.put("minSugars", Float.toString(minSugars));
        if (maxSugars > MIN && maxSugars < MAX)
            params.put("maxSugars", Float.toString(maxSugars));

        if (minIron > MIN && minIron < MAX)
            params.put("minIron", Float.toString(minIron));
        if (maxIron > MIN && maxIron < MAX)
            params.put("maxIron", Float.toString(maxIron));

        return params;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(advanceSearchSelected ? 1 : 0);
        dest.writeInt(includeRecipes ? 1 : 0);
        dest.writeInt(includeMenus ? 1 : 0);
        dest.writeFloat(minRating);
        dest.writeFloat(maxRating);
        dest.writeString(period);
        dest.writeString(includedIngredients);
        dest.writeString(excludedIngredients);
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

    public static final Creator<SearchPrefs> CREATOR = new Creator<SearchPrefs>() {
        @Override
        public SearchPrefs createFromParcel(Parcel in) {
            return new SearchPrefs(in);
        }

        @Override
        public SearchPrefs[] newArray(int size) {
            return new SearchPrefs[size];
        }
    };
}
