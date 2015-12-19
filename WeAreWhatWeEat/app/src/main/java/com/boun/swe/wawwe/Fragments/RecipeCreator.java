package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.CustomViews.CustomAutoCompleteTextView;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.AutoComplete;
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
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeCreator extends LeafFragment {

    private Recipe recipe;

    TagGroup tagGroupStatic;

    public RecipeCreator() {
        TAG = App.getInstance().getString(R.string.title_menu_recipeCreation);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate holder
        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_creation,
                container, false);

        recipe = getArguments().getParcelable("recipe");

        // Get views
        final EditText recipeName = (EditText) recipeCreationView.findViewById(R.id.recipeName);
        final LinearLayout ingredientHolder = (LinearLayout) recipeCreationView.findViewById(R.id.ingredient_item_holder);
        final EditText howTo = (EditText) recipeCreationView.findViewById(R.id.description);
        final Button addIngredients = (Button) recipeCreationView.findViewById(R.id.add_new_ingredient);
        final Button submit = (Button) recipeCreationView.findViewById(R.id.button_recipe_submit);
        final TagGroup tagGroup = (TagGroup) recipeCreationView.findViewById(R.id.tag_group);
        tagGroupStatic = (TagGroup) recipeCreationView.findViewById(R.id.tag_group_static);

        tagGroupStatic.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                if (context instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) context;
                    mainActivity.makeFragmentTransaction(Search.getFragment(tag));
                }
            }
        });

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

                List<Ingredient> ingredients = new ArrayList<>();
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
                ingredientAmount.setText(String.format("%d", ingredient.getAmount()));

            }
        }
        else addIngredientRow(ingredientHolder);

        return recipeCreationView;
    }

    private View addIngredientRow(final LinearLayout ingredientHolder) {
        final LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        final View ingredientRow = inflater.inflate(R.layout.item_ingredient,
                ingredientHolder, false);

        final CustomAutoCompleteTextView ingName = (CustomAutoCompleteTextView)
                ingredientRow.findViewById(R.id.ingredient_name);
        ArrayAdapter<AutoComplete> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_dropdown_item_1line);
        ingName.setAdapter(adapter);

        ingName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    ingName.showDropDown();
            }
        });
        ingName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ingName.itemReceived) {
                    API.getIngredientItem(RecipeCreator.class.getSimpleName(),
                            ((AutoComplete) ingName.getAdapter().getItem(position)).getId(),
                            new Response.Listener<Ingredient>() {
                                @Override
                                public void onResponse(Ingredient response) {
                                    // TODO ingredient selected...
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                }
                else {
                    ingName.itemReceived = true;
                    API.searchIngredients(RecipeCreator.class.getSimpleName(),
                            ((AutoComplete) ingName.getAdapter().getItem(position)).getText(),
                            new Response.Listener<AutoComplete[]>() {
                                @Override
                                public void onResponse(AutoComplete[] response) {
                                    ingName.dismissDropDown();
                                    final ArrayAdapter<AutoComplete> adapter = new ArrayAdapter<>(
                                            context, android.R.layout.simple_list_item_1);
                                    adapter.addAll(response);
                                    ingName.setAdapter(adapter);
                                    ingName.showDropDown();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                }
            }
        });
        ingName.addTextChangedListener(new TextWatcher() {
            long lastPress = 0l;

            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                if (lastPress == 0) lastPress = System.currentTimeMillis();

                if(System.currentTimeMillis() - lastPress > 500
                        && !ingName.itemReceived) {
                    lastPress = System.currentTimeMillis();
                    if (s.toString().isEmpty()) return;

                    API.autocompleteIngredients(RecipeCreator.class.getSimpleName(),
                            s.toString(),
                            new Response.Listener<AutoComplete[]>() {
                                @Override
                                public void onResponse(AutoComplete[] response) {
                                    if (!ingName.itemReceived) return;

                                    ingName.dismissDropDown();
                                    final ArrayAdapter<AutoComplete> adapter = new ArrayAdapter<>(
                                            context, android.R.layout.simple_list_item_1);
                                    adapter.addAll(response);
                                    ingName.setAdapter(adapter);
                                    ingName.showDropDown();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        final ImageButton delete = (ImageButton) ingredientRow.findViewById(R.id.button_ingredient_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ingredientHolder.getChildCount() - 2 != 0) {
                    ingredientHolder.removeView(ingredientRow);
                } else {
                    ingName.adapter.clear();
                    ingName.adapter.notifyDataSetChanged();
                    ingName.itemReceived = false;
                    ingName.setText(null);
                    EditText ingAmt = (EditText) ingredientRow.findViewById(R.id.ingredient_amount);
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
