package com.boun.swe.wawwe.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by onurguler on 28/11/15.
 */
public class Search extends BaseFragment {

    EditText searchBox;
    private RecyclerView searchResults;

    public Search() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TAG = context.getString(R.string.title_menu_search);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.layout_fragment_search,
                container, false);

        searchBox = (EditText) searchView.findViewById(R.id.search_searchBox);

        searchResults = (RecyclerView) searchView.findViewById(R.id.searchResults);
        searchResults.setItemAnimator(new DefaultItemAnimator());
        searchResults.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        searchResults.setAdapter(adapter);

        String query = getArguments().getString("query", null);
        if (query != null) {
            searchBox.setText(query);
            makeSearch(query, adapter);
        }

        searchView.findViewById(R.id.search_searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchBox.getText().toString();
                if (!searchText.isEmpty()) {
                    makeSearch(searchText, adapter);
                    getArguments().putString("query", searchText);
                }
            }
        });

        return searchView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void makeSearch(String query, final FeedAdapter adapter) {
        API.searchRecipe(getTag(), query,
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
    }

    public static Search getFragment(String query) {
        Search searchFragment = new Search();

        Bundle searchBundle = new Bundle();
        searchBundle.putString("query", query);
        searchFragment.setArguments(searchBundle);

        return searchFragment;
    }

}
