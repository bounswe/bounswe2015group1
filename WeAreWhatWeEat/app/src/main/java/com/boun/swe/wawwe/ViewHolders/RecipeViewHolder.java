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

    public ImageView recipePic;
    public TextView recipeName;
    public TextView type;
    public TextView calories;
    public TextView score;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        recipePic = (ImageView) itemView.findViewById(R.id.recipePic_small);
        recipeName = (TextView) itemView.findViewById(R.id.itemRecipeName);

        type = (TextView) itemView.findViewById(R.id.type);
        calories = (TextView) itemView.findViewById(R.id.calories);
        score = (TextView) itemView.findViewById(R.id.score);
    }
}
