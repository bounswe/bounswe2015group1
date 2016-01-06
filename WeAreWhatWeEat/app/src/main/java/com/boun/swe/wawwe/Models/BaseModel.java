package com.boun.swe.wawwe.Models;


import java.util.Date;

/**
 * Created by Mert on 05/01/16.
 */
abstract public class BaseModel implements Comparable<BaseModel> {

    int id;
    Date createdAt;
    float rating;
    transient private BaseModel[] subItems = null;
    transient private boolean isRecommended = false;
    transient private boolean isExpanded = false;

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public BaseModel[] getSubItems() {
        return subItems;
    }

    public void setSubItems(BaseModel[] subItems) {
        this.subItems = subItems;
    }

    public boolean isRecommended() {
        return isRecommended;
    }

    public void setRecommended(boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setIsExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    @Override
    public int compareTo(BaseModel another) {
        return rating > another.getRating() || isRecommended ? -1 :
                rating == another.getRating() ? 0 : 1;
    }

    public boolean areItemsDifferent(BaseModel another) {
        return (subItems != null && another.subItems == null ||
                subItems == null && another.subItems != null);
    }
}
