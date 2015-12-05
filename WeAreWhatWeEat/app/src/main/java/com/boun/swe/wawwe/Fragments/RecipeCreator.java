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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Alpha;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeCreator extends LeafFragment {

    private Recipe recipe;

    public RecipeCreator() {
        TAG = App.getInstance().getString(R.string.title_menu_recipeCreation);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate holder
        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_creation,
                container, false);

        // Get views
        final EditText recipeName = (EditText) recipeCreationView.findViewById(R.id.recipeName);
        final LinearLayout ingredientHolder = (LinearLayout) recipeCreationView.findViewById(R.id.ingredient_item_holder);
        final EditText howTo = (EditText) recipeCreationView.findViewById(R.id.description);
        final Button addIngredients = (Button) recipeCreationView.findViewById(R.id.add_new_ingredient);
        final Button submit = (Button) recipeCreationView.findViewById(R.id.button_recipe_submit);
        final TagGroup tagGroup = (TagGroup) recipeCreationView.findViewById(R.id.tag_group);
        final TagGroup tagGroupStatic = (TagGroup) recipeCreationView.findViewById(R.id.tag_group_static);

        // Set headers
        int[] headerIds = new int[] { R.id.rCreation_header_rName, R.id.rCreation_header_rIngredients,
                R.id.rCreation_header_rHowTo, R.id.rCreation_header_rTags };
        int[] headerTextIds = new int[] { R.string.title_recipe, R.string.title_ingredients,
                R.string.title_directions, R.string.title_tags };
        for (int i = 0;i < headerIds.length;i++) {
            TextSurface header = (TextSurface) recipeCreationView.findViewById(headerIds[i]);
            Text text = Commons.generateHeader(headerTextIds[i]);
            header.play(new Sequential(
                    Delay.duration(i * 100),
                    new Parallel(
                            Slide.showFrom(Side.LEFT, text, 500),
                            ChangeColor.to(text, 750, context.getResources()
                                    .getColor(R.color.colorAccent))
                    )));
        }

        recipe = getArguments().getParcelable("recipe");

        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredientRow(ingredientHolder);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipe_name = recipeName.getText().toString();
                String directions = howTo.getText().toString();
                String[] alltagsUserGenerated = tagGroup.getTags();
                String[] alltagsStatic = tagGroupStatic.getTags();

                if (recipe_name.equals("") || directions.equals("")) {
                    Toast.makeText(App.getInstance(), "Some fields are missing",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<Ingredient> ingredients = new ArrayList<Ingredient>();
                for (int i = 0; i < ingredientHolder.getChildCount() - 1; i++) {
                    ViewGroup ingredientRow = (ViewGroup) ingredientHolder.getChildAt(i);
                    EditText ingredientName = (EditText) ingredientRow.findViewById(R.id.ingredient_name);
                    EditText ingredientAmount = (EditText) ingredientRow.findViewById(R.id.ingredient_amount);

                    String ingredient_name = ingredientName.getText().toString();
                    String ingredient_amount = ingredientAmount.getText().toString();

                    if (ingredient_name.equals("") || ingredient_amount.equals("")) {
                        Toast.makeText(App.getInstance(), "Some fields are missing",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ingredients.add(new Ingredient(ingredient_name, Integer.parseInt(ingredient_amount)));
                }

                if (recipe != null) {
                    Recipe recipe = new Recipe(recipe_name, directions, ingredients);

                    API.editRecipe(getTag(), recipe, recipe.getId(),
                            new Response.Listener<Recipe>() {
                                @Override
                                public void onResponse(Recipe response) {
                                    if (context instanceof MainActivity) {
                                        MainActivity main = (MainActivity) context;
                                        main.onBackPressed();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, context.getString(R.string.error_editRecipe),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Recipe recipe = new Recipe(recipe_name, directions, ingredients);

                    API.createRecipe(getTag(), recipe,
                            new Response.Listener<Recipe>() {
                                @Override
                                public void onResponse(Recipe response) {
                                    if (context instanceof MainActivity) {
                                        MainActivity main = (MainActivity) context;
                                        main.onBackPressed();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, context.getString(R.string.error_createRecipe),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        recipeName.requestFocus();

        if (recipe != null) {
            recipeName.setText(recipe.getName());
            howTo.setText(recipe.getDescription());
            submit.setText(context.getString(R.string.button_edit_recipe));

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

            for (Ingredient ingredient: recipe.getIngredients()) {
                View ingredientRow = addIngredientRow(ingredientHolder);

                EditText ingredientName = (EditText) ingredientRow.findViewById(R.id.ingredient_name);
                EditText ingredientAmount = (EditText) ingredientRow.findViewById(R.id.ingredient_amount);

                ingredientName.setText(ingredient.getName());
                ingredientAmount.setText(ingredient.getAmount());
            }
        }
        else addIngredientRow(ingredientHolder);

        return recipeCreationView;
    }

    private View addIngredientRow(final LinearLayout ingredientHolder) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        final View ingredientRow = inflater.inflate(R.layout.item_ingredient,
                ingredientHolder, false);

        final ImageButton delete = (ImageButton) ingredientRow.findViewById(R.id.button_ingredient_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ingredientHolder.getChildCount() - 2 != 0) {
                    ingredientHolder.removeView(ingredientRow);
                } else {
                    final EditText ingName = (EditText) ingredientRow.findViewById(R.id.ingredient_name);
                    ingName.setText(null);
                    final EditText ingAmt = (EditText) ingredientRow.findViewById(R.id.ingredient_amount);
                    ingAmt.setText(null);
                }
            }
        });

        ingredientHolder.addView(ingredientRow, ingredientHolder.getChildCount() - 1);
        return ingredientRow;
    }

    public static RecipeCreator getFragment(Recipe recipe) {
        RecipeCreator recipeCreationFragment = new RecipeCreator();

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);

        recipeCreationFragment.setArguments(bundle);
        return recipeCreationFragment;
    }

}
