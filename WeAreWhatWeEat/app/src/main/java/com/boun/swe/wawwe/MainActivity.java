package com.boun.swe.wawwe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.boun.swe.wawwe.Fragments.BaseFragment;
import com.boun.swe.wawwe.Fragments.Login;
import com.boun.swe.wawwe.Fragments.RecipeCreator;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

/**
 * Created by Mert on 16/10/15.
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    // This is the menu component for application
    private ResideMenu resideMenu;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isRemembers = App.getRememberMe();
        if (!isRemembers)
            getSupportActionBar().hide();

        setContentView(R.layout.layout_activity_main);

        handler = new Handler(getMainLooper());

        // For now this application has only one static
        // fragment, later on will have multiple fragments
        // and can be navigated from left menu.
        // Do not add navigator for now...
        //prepareResideMenu();

        // initially directly goes into login fragment...
        makeFragmentTransaction(!isRemembers ? Login.getFragment() :
                RecipeCreator.getFragment(), isRemembers);
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
        makeFragmentTransaction(fragment, true);
    }

    public void makeFragmentTransaction(final BaseFragment fragment, final boolean addToBackStack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (addToBackStack) {
                    transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
                    transaction.addToBackStack(fragment.getClass().getSimpleName());
                }
                else transaction.add(R.id.container, fragment, fragment.getClass().getSimpleName());
                transaction.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO handle menu clicks
    }
}

