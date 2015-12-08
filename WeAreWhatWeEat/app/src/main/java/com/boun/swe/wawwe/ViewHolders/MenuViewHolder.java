package com.boun.swe.wawwe.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boun.swe.wawwe.R;

/**
 * Created by onurguler on 07/12/15.
 */
public class MenuViewHolder extends RecyclerView.ViewHolder {

    private TextView menuName;
    private ImageView menuImg;

    public MenuViewHolder(View v) {
        super(v);
        menuName = (TextView) v.findViewById(R.id.itemMenuName);
        menuImg = (ImageView) v.findViewById(R.id.menuPic_small);
    }

    public TextView getMenuName() {
        return menuName;
    }

    public void setMenuName(TextView menuName) {
        this.menuName = menuName;
    }

    public ImageView getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(ImageView menuImg) {
        this.menuImg = menuImg;
    }
}