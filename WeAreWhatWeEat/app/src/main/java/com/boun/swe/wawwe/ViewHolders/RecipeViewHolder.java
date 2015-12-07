package com.boun.swe.wawwe.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boun.swe.wawwe.R;

/**
 * Created by onurguler on 07/12/15.
 */
public class RecipeViewHolder extends RecyclerView.ViewHolder {

    private ImageView recipeImg;
    private TextView itemRecipeName;
    private TextView type;
    private TextView calories;
    private TextView score;

    public RecipeViewHolder(View v) {
        super(v);
        recipeImg = (ImageView) v.findViewById(R.id.recipePic_small);
        itemRecipeName = (TextView) v.findViewById(R.id.itemRecipeName);
        type = (TextView) v.findViewById(R.id.type);
        calories = (TextView) v.findViewById(R.id.calories);
        score = (TextView) v.findViewById(R.id.score);
    }

    public ImageView getRecipeImg() {
        return recipeImg;
    }

    public void setRecipeImg(ImageView recipeImg) {
        this.recipeImg = recipeImg;
    }

    public TextView getItemRecipeName() {
        return itemRecipeName;
    }

    public void setItemRecipeName(TextView itemRecipeName) {
        this.itemRecipeName = itemRecipeName;
    }

    public TextView getType() {
        return type;
    }

    public void setType(TextView type) {
        this.type = type;
    }

    public TextView getCalories() {
        return calories;
    }

    public void setCalories(TextView calories) {
        this.calories = calories;
    }

    public TextView getScore() {
        return score;
    }

    public void setScore(TextView score) {
        this.score = score;
    }

}
