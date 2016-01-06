package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.boun.swe.wawwe.R;


/**
 * Created by onurguler on 06/01/16.
 */
public class AdvancedSearch extends LeafFragment{

    public AdvancedSearch() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = context.getString(R.string.title_menu_advanced_search);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View advSearchView = inflater.inflate(R.layout.layout_fragment_advanced_filter,
                container, false);

        final Spinner periodSpinner = (Spinner) advSearchView.findViewById(R.id.periodSpinner);
        ArrayAdapter<CharSequence> periodSpinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.menu_period_array, android.R.layout.simple_spinner_item);
        periodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(periodSpinnerAdapter);

        return advSearchView;
    }

    public static AdvancedSearch getFragment(Bundle bundle) {
        AdvancedSearch advancedSearchFragment = new AdvancedSearch();
        advancedSearchFragment.setArguments(bundle);
        return advancedSearchFragment;
    }


}
