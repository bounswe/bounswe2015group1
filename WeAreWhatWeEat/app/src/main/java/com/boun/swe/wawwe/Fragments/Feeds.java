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
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 09/11/15.
 */
public class Feeds extends BaseFragment {

    private RecyclerView feeds;

    public Feeds() { }

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

        API.getAllRecipes(Feeds.class.getSimpleName(),
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

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity)
            ((MainActivity) context).getSupportActionBar()
                    .setTitle(R.string.title_menu_feeds);
    }

    public static Feeds getFragment(Bundle bundle) {
        Feeds feedsFragment = new Feeds();
        feedsFragment.setArguments(bundle);
        return feedsFragment;
    }
}
