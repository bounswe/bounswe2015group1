package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.SearchPrefs;
import com.boun.swe.wawwe.R;


/**
 * Created by onurguler on 06/01/16.
 */
public class SearchPreferences extends LeafFragment {

    CheckBox includeMenus;
    CheckBox includeRecipes;

    Spinner periodSpinner;

    EditText includedIngredients;
    EditText excludedIngredients;

    LinearLayout searchPreferencesHolder;

    EditText[] inputs = new EditText[20];

    public SearchPreferences() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TAG = context.getString(R.string.title_menu_search_preferences);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View advSearchView = inflater.inflate(R.layout.layout_fragment_search_preferences,
                container, false);

        SearchPrefs searchPrefs = App.getSearchPrefs();

        searchPreferencesHolder = (LinearLayout) advSearchView.findViewById(R.id.searchPreferences_holder);

        periodSpinner = (Spinner) advSearchView.findViewById(R.id.periodSpinner);
        String[] periods = new String[4];
        periods[0] = "";
        int c = 1;
        for (String period: context.getResources().getStringArray(R.array.menu_period_array))
            periods[c++] = period;
        ArrayAdapter<CharSequence> periodSpinnerAdapter = new ArrayAdapter<CharSequence>(context,
                android.R.layout.simple_spinner_item, periods);
        periodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(periodSpinnerAdapter);
        for (int i = 0;i < 4;i++) {
            if (periodSpinnerAdapter.getItem(i).equals(searchPrefs.getPeriod())) {
                periodSpinner.setSelection(i);
                break;
            }
        }

        includeMenus = (CheckBox) advSearchView.findViewById(R.id.checkbox_includeMenus);
        includeRecipes = (CheckBox) advSearchView.findViewById(R.id.checkbox_includeRecipes);
        includeMenus.setChecked(searchPrefs.isIncludeMenus());
        includeRecipes.setChecked(searchPrefs.isIncludeRecipes());

        includedIngredients = (EditText) advSearchView.findViewById(R.id.text_includedIng);
        excludedIngredients = (EditText) advSearchView.findViewById(R.id.text_excludedIng);
        includedIngredients.setText(searchPrefs.getIncludedIngredients());
        excludedIngredients.setText(searchPrefs.getExcludedIngredients());

        float[] savedLimits = searchPrefs.getLimitsAsArray();
        String[] titles = context.getResources().getStringArray(R.array.title_searchPreferences);
        for (int i = 0;i < titles.length;i += 2) {
            View prefRow = inflater.inflate(R.layout.item_filter_two_input,
                    searchPreferencesHolder, false);

            ((TextView) prefRow.findViewById(R.id.item_titleFilterLeft))
                    .setText(titles[i]);
            ((TextView) prefRow.findViewById(R.id.item_titleFilterRight))
                    .setText(titles[i + 1]);

            inputs[i] = (EditText) prefRow.findViewById(R.id.item_filterLeft);
            inputs[i].setText(Float.toString(savedLimits[i]));

            inputs[i + 1] = (EditText) prefRow.findViewById(R.id.item_filterRight);
            inputs[i + 1].setText(Float.toString(savedLimits[i + 1]));

            searchPreferencesHolder.addView(prefRow);
        }

        return advSearchView;
    }

    private SearchPrefs generateSearchPrefs() {
        SearchPrefs searchPrefs = App.getSearchPrefs();

        searchPrefs.setIncludeRecipes(includeRecipes.isChecked());
        searchPrefs.setIncludeMenus(includeMenus.isChecked());

        searchPrefs.setPeriod(periodSpinner.getSelectedItem().toString());
        searchPrefs.setIncludedIngredients(includedIngredients.getText().toString());
        searchPrefs.setExcludedIngredients(excludedIngredients.getText().toString());

        float[] limits = new float[20];
        for (int i = 0;i < inputs.length;i++)
            limits[i] = Float.parseFloat(inputs[i].getText().toString());
        searchPrefs.setLimitsWithArray(limits);

        return searchPrefs;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.menu_profile_editDone).setIcon(R.mipmap.ic_done_black_24dp);
        menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                App.setSearchPrefs(generateSearchPrefs());
                ((MainActivity) context).onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.setSearchPrefs(generateSearchPrefs());
    }

    public static SearchPreferences getFragment() {
        SearchPreferences searchPreferencesFragment = new SearchPreferences();

        return searchPreferencesFragment;
    }
}
