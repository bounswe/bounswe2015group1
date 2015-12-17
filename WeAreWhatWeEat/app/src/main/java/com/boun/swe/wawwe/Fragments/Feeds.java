package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 09/11/15.
 */

public class Feeds extends BaseFragment {

    private RecyclerView feeds;

    public Feeds() {
        TAG = App.getInstance().getString(R.string.title_menu_feeds);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View feedsView = inflater.inflate(R.layout.layout_fragment_feeds,
                container, false);

        feeds = (RecyclerView) feedsView.findViewById(R.id.feeds);
        feeds.setItemAnimator(new DefaultItemAnimator());
        feeds.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        feeds.setAdapter(adapter);

        API.getAllRecipes(getTag(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null)
                            adapter.addItems(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.error_feed),
                                Toast.LENGTH_SHORT).show();
                    }
                });

//        API.getMenu(getTag(), 1,
//                new Response.Listener<Menu>() {
//                    @Override
//                    public void onResponse(Menu response) {
//                        if (response != null) {
//                            Object[] o = {response};
//                            adapter.addItems(o);
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, context.getString(R.string.error_feed),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });

        API.getAllMenusforUser(getTag(),
                new Response.Listener<Menu[]>() {
                    @Override
                    public void onResponse(Menu[] response) {
                        if (response != null) {
                            //Object[] o = {response};
                            adapter.addItems(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.error_feed),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        return feedsView;
    }

    public static Feeds getFragment(Bundle bundle) {
        Feeds feedsFragment = new Feeds();
        feedsFragment.setArguments(bundle);
        return feedsFragment;
    }
}