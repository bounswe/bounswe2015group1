package com.boun.swe.wawwe;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.CustomViews.MenuItem;
import com.boun.swe.wawwe.Fragments.BaseFragment;
import com.boun.swe.wawwe.Fragments.Feeds;
import com.boun.swe.wawwe.Fragments.LeafFragment;
import com.boun.swe.wawwe.Fragments.Login;
import com.boun.swe.wawwe.Fragments.Profile;
import com.boun.swe.wawwe.Fragments.Search;
import com.boun.swe.wawwe.Models.AccessToken;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.Utils.API;
import com.special.ResideMenu.ResideMenu;

/**
 * Created by Mert on 16/10/15.
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    private Toolbar toolbar;
    // This is the menu component for application
    private ResideMenu resideMenu;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler(getMainLooper());

        setContentView(R.layout.layout_activity_main);
        prepareToolbar();
        prepareResideMenu();
        getSupportActionBar().hide();

        if (App.getRememberMe())
            API.login(MainActivity.class.getSimpleName(), App.getUser(),
                    new Response.Listener<AccessToken>() {

                        @Override
                        public void onResponse(AccessToken response) {
                            App.setAccessValues(response);
                            getSupportActionBar().show();
                            makeFragmentTransaction(Feeds.getFragment(new Bundle()));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isRemembers", true);
                            makeFragmentTransaction(Login.getFragment(bundle));
                        }
                    });
        else
            makeFragmentTransaction(Login.getFragment(new Bundle()));

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fr = getSupportFragmentManager().findFragmentById(R.id.container);
                if (fr != null) {
                    if (fr instanceof LeafFragment)
                        setDisplayHomeAsUp();
                    else
                        setDisplayHomeAsNavigator();
                    toolbar.setTitle(((BaseFragment) fr).TAG);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void prepareToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setDisplayHomeAsNavigator();
    }

    private void prepareResideMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu.setBackground(R.color.colorPrimaryDark);
        resideMenu.attachToActivity(this);

        // Create and add menu items
        String titles[] = { "Feed", "Profile", "Search" };
        int icon[] = { R.mipmap.ic_whatshot_black_24dp, R.mipmap.ic_person_black_24dp, R.mipmap.ic_add_black_24dp };
        int ids[] = { R.id.menu_feeds, R.id.menu_profile, R.id.menu_search };

        for (int i = 0; i < titles.length; i++){
            MenuItem item = new MenuItem(this, icon[i], titles[i]);
            item.setId(ids[i]);
            item.setOnClickListener(this);
            item.setTitleColor(R.color.black);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
        }
    }

    private void setDisplayHomeAsUp() {
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onBackPressed();
            }
        });
    }

    private void setDisplayHomeAsNavigator() {
        toolbar.setNavigationIcon(R.mipmap.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
    }

    public void makeFragmentTransaction(final BaseFragment fragment) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,  android.R.anim.slide_out_right);
                transaction.replace(R.id.container, fragment, fragment.TAG);
                transaction.addToBackStack(fragment.getClass().getSimpleName());
                transaction.commit();
            }
        });
    }

    public void removeFragment(final BaseFragment fragment) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStack(fragment.getClass().getSimpleName(),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_subLogout:
                API.logout(MainActivity.class.getSimpleName(),
                        new Response.Listener<User>() {
                            @Override
                            public void onResponse(User response) {
                                getSupportActionBar().hide();
                                App.setRememberMe(false);
                                App.setUser(null);
                                getSupportFragmentManager()
                                        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                MainActivity.this.makeFragmentTransaction(Login
                                        .getFragment(new Bundle()));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, MainActivity.this
                                        .getString(R.string.error_logoutError),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_feeds:
                makeFragmentTransaction(Feeds.getFragment(new Bundle()));
                resideMenu.closeMenu();
                break;
            case R.id.menu_profile:
                makeFragmentTransaction(Profile.getFragment(new Bundle()));
                resideMenu.closeMenu();
                break;
            case R.id.menu_search:
                makeFragmentTransaction(Search.getFragment(new Bundle()));
                resideMenu.closeMenu();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        API.logout(MainActivity.class.getSimpleName(), null, null);
    }
}

