package com.boun.swe.wawwe;

import android.app.Application;

import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 16/10/15.
 *
 * This is the basic preview application for initial
 *
 */
public class App extends Application {

    // This is the singletons, these objects most likely will be
    // used frequently and across the app so we made them
    // static and put it under application class.
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        API.init();
    }

    public static App getInstance() {
        return instance;
    }
}
