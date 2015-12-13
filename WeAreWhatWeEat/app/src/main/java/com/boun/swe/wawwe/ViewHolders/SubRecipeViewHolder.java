package com.boun.swe.wawwe.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.boun.swe.wawwe.R;

/**
 * Created by Mert on 12/12/15.
 */
public class SubRecipeViewHolder extends RecyclerView.ViewHolder {

    public TextView recipeName;

    public SubRecipeViewHolder(View itemView) {
        super(itemView);

        recipeName = (TextView) itemView.findViewById(R.id.subItemRecipeName);
    }
}
