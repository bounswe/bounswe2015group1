package com.boun.swe.wawwe.CustomViews;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.boun.swe.wawwe.R;

/**
 * Created by akintoksan on 06/12/15.
 */
public class MenuChildViewHolder extends ChildViewHolder {

    public TextView mCrimeDateText;
    public CheckBox mCrimeSolvedCheckBox;

    public MenuChildViewHolder(View itemView) {
        super(itemView);
        mCrimeDateText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);
    }
}
