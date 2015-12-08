package com.boun.swe.wawwe.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Mert on 05/12/15.
 */
public class Menu implements Parcelable {

    private int id;
    private int userId;
    private String name;
    private String period;  //(Can be "daily" | "weekly" | "monthly")
    private List<Integer> recipeIds;
    private List<String> recipeNames;
    private String description;
    private int createdAt;

    public Menu(String name, String period, List<Integer> recipeIds, String description){
        this.name = name;
        this.period = period;
        this.recipeIds = recipeIds;
        this.description = description;
    }

    protected Menu(Parcel in) {
        id = in.readInt();
        name = in.readString();
        userId = in.readInt();
        period = in.readString();
        recipeNames = in.createStringArrayList();
        description = in.readString();
        createdAt = in.readInt();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Integer> getRecipeIds() {
        return recipeIds;
    }

    public void setRecipeIds(List<Integer> recipeIds) {
        this.recipeIds = recipeIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(period);
        dest.writeStringList(recipeNames);
        dest.writeString(description);
        dest.writeInt(createdAt);
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };
}
