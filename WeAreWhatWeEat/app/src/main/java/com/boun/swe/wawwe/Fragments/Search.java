package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.SearchPrefs;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by onurguler on 28/11/15.
 */
public class Search extends LeafFragment {

    private EditText searchBox;
    private Button AdvancedSearchEnabled;
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

        AdvancedSearchEnabled = (Button) searchView
                .findViewById(R.id.button_advanceSearchEnable);
        AdvancedSearchEnabled.setSelected(App.getSearchPrefs()
                .isAdvanceSearchSelected());
        AdvancedSearchEnabled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdvancedSearchEnabled.setSelected(!AdvancedSearchEnabled.isSelected());

                SearchPrefs searchPrefs = App.getSearchPrefs();
                searchPrefs.setAdvanceSearchSelected(AdvancedSearchEnabled.isSelected());
                App.setSearchPrefs(searchPrefs);
            }
        });

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

    private void makeSearch(String query, final FeedAdapter adapter) {
        adapter.clear();
        if (AdvancedSearchEnabled.isSelected()) {
            SearchPrefs searchPrefs = App.getSearchPrefs();
            if (searchPrefs.isIncludeRecipes()) {
                API.advanceSearchRecipe(getTag(), query,
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

                            }
                        });
            }
            if (searchPrefs.isIncludeMenus()) {
                API.advanceSearchMenu(getTag(), query,
                        new Response.Listener<Menu[]>() {
                            @Override
                            public void onResponse(Menu[] response) {
                                if (response != null)
                                    adapter.addItems(response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
            }
        }
        else {
            API.searchRecipe(getTag(), query,
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
                            // No response...
                        }
                    });
            API.searchMenus(getTag(), query,
                    new Response.Listener<Menu[]>() {
                        @Override
                        public void onResponse(Menu[] response) {
                            if (response != null)
                                adapter.addItems(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // No response...
                        }
                    });
        }
    }

    public static Search getFragment(String query) {
        Search searchFragment = new Search();

        Bundle searchBundle = new Bundle();
        searchBundle.putString("query", query);
        searchFragment.setArguments(searchBundle);

        return searchFragment;
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.menu_profile_editDone).setVisible(true);
        menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    main.makeFragmentTransaction(SearchPreferences.getFragment());
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
