package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeDetail extends BaseFragment {

    LinearLayout ingredientHolder;

    public RecipeDetail() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_detail,
                container, false);

        Recipe recipe = getArguments().getParcelable("recipe");

        final TextView recipeName = (TextView) recipeCreationView.findViewById(R.id.recipeName);
        final TextView directions = (TextView) recipeCreationView.findViewById(R.id.directions);
        ingredientHolder = (LinearLayout) recipeCreationView.findViewById(R.id.ingredient_item_holder);

        recipeName.setText(recipe.getName());
        directions.setText(recipe.getDirections());

        for (Ingredient ingredient: recipe.getIngredients())
            addIngredientRow(ingredient);

        return recipeCreationView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            main.setDisplayHomeAsUp();
        }
    }

    private void addIngredientRow(Ingredient ingredient) {
        TextView text = new TextView(context);
        text.setText(String.format("%f %s, %s", ingredient.getAmount(),
                ingredient.getUnit(), ingredient.getName()));
        ingredientHolder.addView(text, ingredientHolder.getChildCount() - 1);
    }

    public static RecipeDetail getFragment(Bundle bundle) {
        RecipeDetail recipeDetailFragment = new RecipeDetail();
        recipeDetailFragment.setArguments(bundle);
        return recipeDetailFragment;
    }
}
