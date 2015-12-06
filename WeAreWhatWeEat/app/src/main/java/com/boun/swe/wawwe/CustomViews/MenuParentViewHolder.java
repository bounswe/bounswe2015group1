package com.boun.swe.wawwe.CustomViews;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.boun.swe.wawwe.R;

/**
 * Created by akintoksan on 06/12/15.
 */
public class MenuParentViewHolder extends ParentViewHolder {

    public TextView mCrimeTitleTextView;
    public ImageButton mParentDropDownArrow;

    public MenuParentViewHolder(View itemView) {
        super(itemView);

        mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
        mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
    }
}
