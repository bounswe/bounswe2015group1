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
        return generateText(getString(resTextId), 30, android.R.color.white);
    }

    public static Text generateHeader(String text) {
        return generateText(text, 30, android.R.color.white);
    }

    public static Text generateText(int resTextId) {
        return generateText(getString(resTextId), 14, android.R.color.black);
    }

    public static Text generateText(String text) {
        return generateText(text, 14, android.R.color.black);
    }

    private static Text generateText(String resText, int textSize, int resColor) {
        return TextBuilder
                .create(resText)
                .setSize(textSize)
                .setAlpha(0)
                .setColor(App.getInstance().getResources().getColor(resColor))
                .setPosition(Align.SURFACE_CENTER)
                .build();
    }

    public static String getString(int resourceId){
        return App.getInstance().getString(resourceId);
    }
}
