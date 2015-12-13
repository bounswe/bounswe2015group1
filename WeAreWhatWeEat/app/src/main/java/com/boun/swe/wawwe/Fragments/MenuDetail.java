package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by onurguler on 08/12/15.
 *
 * TODO prettify this fragment as well...
 */
public class MenuDetail extends LeafFragment {

    Menu menu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TAG = App.getInstance().getString(R.string.title_menu_menuDetail);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View menuDetailView = inflater.inflate(R.layout.layout_fragment_menu_detail,
                container, false);

        menu = getArguments().getParcelable("menu");

        TextView menuNameTextView = (TextView) menuDetailView.findViewById(R.id.menuNameText);
        TextView descriptionTextView = (TextView) menuDetailView.findViewById(R.id.menuDescText);
        TextView periodTextView = (TextView) menuDetailView.findViewById(R.id.periodText);
        final LinearLayout recipeHolder = (LinearLayout) menuDetailView.findViewById(R.id.recipe_item_holder);
        final TextView createdbyTextView = (TextView) menuDetailView.findViewById(R.id.createdByText);

        menuNameTextView.setText(menu.getName());
        descriptionTextView.setText(menu.getDescription());
        periodTextView.setText(menu.getPeriod());

        API.getUserInfo(getTag(),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        createdbyTextView.setText(response.getFullName());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(App.getInstance(), "Could not get the user",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        // TODO use correct call, if not set up by backend; write a test...
        API.getAllRecipes(getTag(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        for(Recipe recipe : response){
                            addRecipeRow(recipeHolder, recipe);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(App.getInstance(), "Could not get recipes",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        return menuDetailView;
    }

    // TODO show with a recycler view, with on click functionality to recipe detail fragment
    private void addRecipeRow(LinearLayout recipeHolder, Recipe recipe) {
        TextView text = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int margin = (int) context.getResources().
                getDimension(R.dimen.activity_horizontal_margin);
        params.setMargins(margin, margin, margin, margin);
        text.setTextColor(context.getResources().getColor(R.color.black));
        text.setTextAppearance(context, android.R.style.TextAppearance);
        text.setLayoutParams(params);
        text.setText(String.format(" - %s", recipe.getName()));
        recipeHolder.addView(text, recipeHolder.getChildCount() - 1);
    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    main.makeFragmentTransaction(ProfileEdit.getFragment(new Bundle()));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static MenuDetail getFragment(Menu menu) {
        MenuDetail menuDetailFragment = new MenuDetail();

        Bundle bundle = new Bundle();
        bundle.putParcelable("menu", menu);

        menuDetailFragment.setArguments(bundle);
        return menuDetailFragment;
    }
}
