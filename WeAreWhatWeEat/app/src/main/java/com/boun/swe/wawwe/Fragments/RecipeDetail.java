package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;

import java.util.ArrayList;
import java.util.List;

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

        final TextView recipeName = (TextView) recipeCreationView.findViewById(R.id.recipeName);
        final TextView directions = (TextView) recipeCreationView.findViewById(R.id.directions);
        ingredientHolder = (LinearLayout) recipeCreationView.findViewById(R.id.ingredient_item_holder);
        final TextView howTo = (TextView) recipeCreationView.findViewById(R.id.textView3);
       // final Button addIngredients = (Button) recipeCreationView.findViewById(R.id.add_new_ingredient);
         addIngredientRow();

        return recipeCreationView;
    }

    private void addIngredientRow() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        TextView text = new TextView(context);
        ingredientHolder.addView(text, ingredientHolder.getChildCount() - 1);
    }

    public static RecipeDetail getFragment() {
        RecipeDetail recipeDetailFragment = new RecipeDetail();
        recipeDetailFragment.setArguments(new Bundle());
        return recipeDetailFragment;
    }
}
