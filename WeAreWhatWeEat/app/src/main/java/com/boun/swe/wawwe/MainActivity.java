package com.boun.swe.wawwe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.boun.swe.wawwe.Fragments.BaseFragment;
import com.boun.swe.wawwe.Fragments.Login;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by Mert on 16/10/15.
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    // This is the menu component for application
    private ResideMenu resideMenu;
    private Handler handler = new Handler(getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For now this application has only one static
        // fragment, later on will have multiple fragments
        // and can be navigated from left menu.
        setContentView(R.layout.layout_activity_main);

        // Do not add navigator for now...
        //prepareResideMenu();

        // initially directly goes into login fragment...
        makeFragmentTransaction(Login.getFragment());

    }

    private void prepareResideMenu() {
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

    public void makeFragmentTransaction(final BaseFragment fragment) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO handle menu clicks
    }
}

