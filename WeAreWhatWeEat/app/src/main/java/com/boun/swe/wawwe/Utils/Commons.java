package com.boun.swe.wawwe.Utils;

import com.boun.swe.wawwe.App;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Mert on 02/12/15.
 */
public class Commons {

    public static Text generateHeader(int resTextId) {
        return  generateText(resTextId, 30, android.R.color.white);
    }

    public static Text generateText(int resTextId) {
        return  generateText(resTextId, 14, android.R.color.black);
    }

    private static Text generateText(int resTextId, int textSize, int resColor) {
        return TextBuilder
                .create(App.getInstance().getString(resTextId))
                .setSize(textSize)
                .setAlpha(0)
                .setColor(App.getInstance().getResources().getColor(resColor))
                .setPosition(Align.SURFACE_CENTER)
                .build();
    }
}
