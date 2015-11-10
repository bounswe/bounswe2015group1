package com.boun.swe.wawwe;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.boun.swe.wawwe.Models.AccessToken;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.Utils.API;
import com.google.gson.Gson;

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

    private static int userId = -1;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        API.init();
    }

    public static App getInstance() {
        return instance;
    }

    private static SharedPreferences getSharedPreferecesForUser() {
        return instance.getSharedPreferences(
                instance.getString(R.string.preference_user), Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditorForUserPreferences() {
        return getSharedPreferecesForUser().edit();
    }

    public static void setRememberMe(boolean shouldRemember) {
        SharedPreferences.Editor editor = getEditorForUserPreferences();
        editor.putBoolean(instance.getString(R.string.preference_rememberMe), shouldRemember);
        editor.commit();
    }

    public static boolean getRememberMe() {
        return getSharedPreferecesForUser().getBoolean(instance
                .getString(R.string.preference_rememberMe), false);
    }

    public static void setUser(User user) {
        SharedPreferences.Editor editor = getEditorForUserPreferences();
        String userStr = new Gson().toJson(user, User.class);
        editor.putString(instance.getString(R.string.preference_userInfo), userStr);
        editor.commit();
    }

    public static User getUser() {
        String userStr = getSharedPreferecesForUser().getString(instance
                .getString(R.string.preference_userInfo), "");
        return "".equals(userStr) ? null :
                new Gson().fromJson(userStr, User.class);
    }

    public static void setAccessValues(AccessToken accessToken) {
        instance.userId = accessToken.getUserId();
        API.setUUID(accessToken.getAccessToken());
    }

    public static int getUserId() {
        return userId;
    }
}
