package com.boun.swe.wawwe.Utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

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

    public static Text generateHeader(int resTextId, int resColor) {
        return generateText(getString(resTextId), 30, resColor);
    }

    public static Text generateHeader(String text, int resColor) {
        return generateText(text, 30, resColor);
    }

    public static Text generateText(int resTextId) {
        return generateText(getString(resTextId), 14, android.R.color.black);
    }

    public static Text generateText(String text) {
        return generateText(text, 14, android.R.color.black);
    }

    public static Text generateText(int resTextId, int textSize, int resColor) {
        return generateText(getString(resTextId), textSize, resColor);
    }

    public static Text generateText(String resText, int textSize, int resColor) {
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



    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float dpToPx(float dp){
        Resources resources = App.getInstance().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @return A float value to represent dp equivalent to px value
     */
    public static float pxToDp(float px){
        Resources resources = App.getInstance().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
