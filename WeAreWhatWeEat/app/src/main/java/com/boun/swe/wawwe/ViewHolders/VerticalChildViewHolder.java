package com.boun.swe.wawwe.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.boun.swe.wawwe.R;

/**
 * Created by akintoksan on 11/12/15.
 */

public class VerticalChildViewHolder extends ChildViewHolder {

    public TextView mDataTextView;

    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public VerticalChildViewHolder(View itemView) {
        super(itemView);

        mDataTextView = (TextView) itemView.findViewById(R.id.list_item_vertical_child_textView);
    }

    public void bind(String childText) {
        mDataTextView.setText(childText);
    }
}
