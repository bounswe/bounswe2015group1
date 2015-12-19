package com.boun.swe.wawwe.CustomViews;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.boun.swe.wawwe.Models.AutoComplete;

/**
 * Created by Mert on 19/12/15.
 */
public class CustomAutoCompleteTextView extends AutoCompleteTextView {
    public boolean itemReceived = false;
    public boolean itemSelected = false;
    public ArrayAdapter<AutoComplete> adapter;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
        adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1);
        this.setAdapter(adapter);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1);
        this.setAdapter(adapter);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_list_item_1);
        this.setAdapter(adapter);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            performFiltering(getText(), 0);
        }
    }
}
