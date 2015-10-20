package com.boun.swe.wawwe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by Mert on 16/10/15.
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

    // This is the menu component for application
    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For now this application has only one static
        // fragment, later on will have multiple fragments
        // and can be navigated from left menu.
        setContentView(R.layout.layout_activity_main);

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setBackground(R.drawable.shadow);
        resideMenu.attachToActivity(this);

        // Create and add menu items
        String titles[] = { "Login", "Settings" };
        // int icon[] = { R.drawable.icon_home, R.drawable.icon_settings };

        for (int i = 0; i < titles.length; i++){
            //ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            ResideMenuItem item = new ResideMenuItem(this, R.drawable.ic_launcher, titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT
        }
    }

    @Override
    public void onClick(View v) {
        // TODO handle menu clicks
    }
}

