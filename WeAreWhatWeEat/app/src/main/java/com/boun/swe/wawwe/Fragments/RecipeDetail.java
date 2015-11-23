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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;

import java.util.ArrayList;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeDetail extends BaseFragment {
    static String name = "";
    static String description = "";
    static ArrayList<String> ingredientsName;
    static ArrayList<Integer> ingredientsAmount;
    static ArrayList<String> ingredientsUnit;
    public RecipeDetail() { }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_detail,
                container, false);
        ingredientsName = new ArrayList<String>();
        ingredientsAmount = new ArrayList<Integer>();
        ingredientsUnit = new ArrayList<String>();
        Recipe recipe = getArguments().getParcelable("recipe");

        TextView recipeName = (TextView) recipeCreationView.findViewById(R.id.recipeName);
        TextView directions = (TextView) recipeCreationView.findViewById(R.id.description);
        LinearLayout ingredientHolder = (LinearLayout) recipeCreationView
                .findViewById(R.id.ingredient_item_holder);

        recipeName.setText(recipe.getName());
        name = recipe.getName();
        directions.setText(recipe.getDescription());
        description = recipe.getDescription();
        for (Ingredient ingredient: recipe.getIngredients()){
            addIngredientRow(ingredientHolder, ingredient);
            ingredientsName.add(ingredient.getName());
            ingredientsAmount.add(ingredient.getAmount());
            ingredientsUnit.add(ingredient.getUnit());
        }
        return recipeCreationView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
//            main.setDisplayHomeAsUp();
        }
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
        text.setTextAppearance(context, android.R.style.TextAppearance_Large);
        text.setLayoutParams(params);
        text.setText(String.format(" - %d %s, %s", ingredient.getAmount(),
                ingredient.getUnit(), ingredient.getName()));
        ingredientHolder.addView(text, ingredientHolder.getChildCount() - 1);
    }

    public static RecipeDetail getFragment(Bundle bundle) {
        RecipeDetail recipeDetailFragment = new RecipeDetail();
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
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name );
                    bundle.putString("description",description);
                    bundle.putStringArrayList("ingredientsName", ingredientsName);
                    bundle.putIntegerArrayList("ingredientsAmount",ingredientsAmount);
                    bundle.putStringArrayList("ingredientsUnit",ingredientsUnit);
                    main.makeFragmentTransaction(RecipeCreator.getFragment(bundle));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
