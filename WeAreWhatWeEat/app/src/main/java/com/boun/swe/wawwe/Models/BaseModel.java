package com.boun.swe.wawwe.Models;


import java.util.Date;

/**
 * Created by Mert on 05/01/16.
 */
abstract public class BaseModel implements Comparable<BaseModel> {

    int id;
    Date createdAt;
    double rating;

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    @Override
    public int compareTo(BaseModel another) {
        return rating > another.getRating() ? -1 :
                rating == another.getRating() ? 0 : 1;
    }

}
