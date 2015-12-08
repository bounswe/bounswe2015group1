package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.Adapters.NewFeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //TODO You can change the adapter to old one by uncommenting this and commenting the next one.
        //final FeedAdapter adapter = new FeedAdapter(context);
        final NewFeedAdapter adapter = new NewFeedAdapter(context);
        feeds.setAdapter(adapter);

        API.getAllRecipes(getTag(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null)
                            adapter.setData(response);
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