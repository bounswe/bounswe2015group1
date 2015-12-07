package com.boun.swe.wawwe.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Nutrition;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeDetail extends LeafFragment {

    private Recipe recipe;

    public RecipeDetail() {
        TAG = App.getInstance().getString(R.string.title_menu_recipeDetail);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        TAG = context.getString(R.string.title_menu_recipeDetail);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_detail,
                container, false);
        recipe = getArguments().getParcelable("recipe");

        ImageView recipeImage = (ImageView) recipeCreationView.findViewById(R.id.recipeImage);
        TextView recipeName = (TextView) recipeCreationView.findViewById(R.id.recipeName);
        TextView directions = (TextView) recipeCreationView.findViewById(R.id.description);
        LinearLayout ingredientHolder = (LinearLayout) recipeCreationView
                .findViewById(R.id.ingredient_item_holder);
        LinearLayout nutritionHolder = (LinearLayout) recipeCreationView
                .findViewById(R.id.nutrition_item_holder);

        final TagGroup tagGroupStatic = (TagGroup) recipeCreationView.findViewById(R.id.tag_group_static);
        API.getRecipeTags(getTag(), recipe.getId(), new Response.Listener<String[]>() {
            @Override
            public void onResponse(String[] response) {
                tagGroupStatic.setTags(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        recipeName.setText(recipe.getName());
        directions.setText(recipe.getDescription());
        for (Ingredient ingredient: recipe.getIngredients())
            addIngredientRow(ingredientHolder, ingredient);


        return recipeCreationView;
    }

    private void addIngredientRow(LinearLayout ingredientHolder, Ingredient ingredient) {
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
        text.setText(String.format(" - %d %s", ingredient.getAmount(), ingredient.getName()));
        ingredientHolder.addView(text, ingredientHolder.getChildCount() - 1);
    }

//    private void addNutrition(LinearLayout nutritionHolder, Nutrition nutrition) {
//        TextView text = new TextView(context);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT);
//        int margin = (int) context.getResources().
//                getDimension(R.dimen.activity_horizontal_margin);
//        params.setMargins(margin, margin, margin, margin);
//        text.setTextColor(context.getResources().getColor(R.color.black));
//        text.setTextAppearance(context, android.R.style.TextAppearance_Large);
//        text.setLayoutParams(params);
//        text.setText(String.format(" - %d %d", nutrition.getId(), nutrition.getCalories()));
//        nutritionHolder.addView(text, nutritionHolder.getChildCount() - 1);
//    }


    public static RecipeDetail getFragment(Recipe recipe) {
        RecipeDetail recipeDetailFragment = new RecipeDetail();

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);

        recipeDetailFragment.setArguments(bundle);
        return recipeDetailFragment;
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
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    main.makeFragmentTransaction(RecipeCreator.getFragment(recipe));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
