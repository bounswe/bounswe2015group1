package com.boun.swe.wawwe.CustomViews;

import android.content.Context;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by Mert on 09/11/15.
 */
public class MenuItem extends ResideMenuItem {

    public MenuItem(Context context) {
        super(context);
    }

    public MenuItem(Context context, int icon, int title) {
        super(context, icon, title);
    }

    public MenuItem(Context context, int icon, String title) {
        super(context, icon, title);
    }

    public void setTitleColor(int resId) {
        TextView tv_title = (TextView) findViewById(com.special.ResideMenu.R.id.tv_title);
        tv_title.setTextColor(getContext().getResources().getColor(resId));
    }
}
