package com.boun.swe.wawwe.Utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.boun.swe.wawwe.App;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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

    public static Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0);
        return c.getTime();
    }
    /**
     * Gets date and returns time values as String.
     * Ex: 01:12, 21 July 1993
     *
     * @param date Date to be converted to String format
     * @return
     */
    public static String[] prettifyDate(Date date) {

        if (date == null) return new String[] { "", "" };
        return new String[] {
                new SimpleDateFormat("HH:mm",
                        App.getInstance().getResources()
                                .getConfiguration().locale)
                        .format(date),
                new SimpleDateFormat("dd MMMM yyyy",
                        App.getInstance().getResources()
                                .getConfiguration().locale)
                        .format(date) };
    }

    public static String getString(int resourceId){
        return App.getInstance().getString(resourceId);
    }

    public static String getString(int resourceId, String... args){
        return App.getInstance().getString(resourceId, args);
    }

    public static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = App.getInstance().getApplicationContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String getUrlForGet(String url, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(url);
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if(i == 1) {
                stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
            }
            iterator.remove(); // avoids a ConcurrentModificationException
            i++;
        }
        url = stringBuilder.toString();
        return url;
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
