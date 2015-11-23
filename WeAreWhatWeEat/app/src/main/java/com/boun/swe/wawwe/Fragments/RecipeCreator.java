package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mert on 31/10/15.
 */

public class RecipeCreator extends BaseFragment {

    static String name = "";
    static String description  = "";
    static ArrayList<String> ingredientNames;
    static ArrayList<Integer> ingredientAmounts;
    static ArrayList<String> ingredientUnits;
    LinearLayout ingredientHolder;

    public RecipeCreator() { }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recipeCreationView = inflater.inflate(R.layout.layout_fragment_recipe_creation,
                container, false);

        final EditText recipeName = (EditText) recipeCreationView.findViewById(R.id.recipeName);
        ingredientHolder = (LinearLayout) recipeCreationView.findViewById(R.id.ingredient_item_holder);
        final EditText howTo = (EditText) recipeCreationView.findViewById(R.id.description);
        final Button addIngredients = (Button) recipeCreationView.findViewById(R.id.add_new_ingredient);
        final Button submit = (Button) recipeCreationView.findViewById(R.id.button_recipe_submit);

        addIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addIngredientRow();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipe_name = recipeName.getText().toString();
                String directions = howTo.getText().toString();

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
                    Spinner amountType = (Spinner) ingredientRow.findViewById(R.id.spinner_amountType);

                    String ingredient_name = ingredientName.getText().toString();
                    String ingredient_amount = ingredientAmount.getText().toString();

                    if (ingredient_name.equals("") || ingredient_amount.equals("")) {
                        Toast.makeText(App.getInstance(), "Some fields are missing",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ingredients.add(new Ingredient(ingredient_name, Integer.parseInt(ingredient_amount),
                            (String) amountType.getSelectedItem()));
                }
                if (name != null && !name.equals("")) {
                    //TODO edit recipe API comes here
                }else {
                    Recipe recipe = new Recipe(recipe_name, directions, ingredients);

                    API.createRecipe(RecipeCreator.class.getSimpleName(), recipe,
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

        addIngredientRow();
        if (name != null && !name.equals("")) {

            recipeName.setText(name);
            howTo.setText(description);
            submit.setText("Save Changes");
            for (int i = ingredientNames.size() - 1; i > -1; i--) {
                ViewGroup ingredientRow = (ViewGroup) ingredientHolder.getChildAt(ingredientNames.size() - 1 - i);
                EditText ingredientName = (EditText) ingredientRow.findViewById(R.id.ingredient_name);
                EditText ingredientAmount = (EditText) ingredientRow.findViewById(R.id.ingredient_amount);
                Spinner amountType = (Spinner) ingredientRow.findViewById(R.id.spinner_amountType);
                ingredientName.setText(ingredientNames.get(i));
                ingredientAmount.setText(ingredientAmounts.get(i).toString());
                if (ingredientUnits.get(i).equals("gr"))
                    amountType.setSelection(0);
                else if (ingredientUnits.get(i).equals("kg"))
                    amountType.setSelection(1);
                else if (ingredientUnits.get(i).equals("tsp"))
                    amountType.setSelection(2);
                else if (ingredientUnits.get(i).equals("tbsp"))
                    amountType.setSelection(3);
                else
                    amountType.setSelection(4);
                if (i != 0) {
                    addIngredientRow();
                }

            }
        }
        return recipeCreationView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            main.getSupportActionBar()
                    .setTitle(R.string.title_menu_recipeCreation);
//            main.setDisplayHomeAsUp();
        }
    }

    private void addIngredientRow() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        final View ingredient = inflater.inflate(R.layout.item_ingredient,
                ingredientHolder, false);

        final ImageButton delete = (ImageButton) ingredient.findViewById(R.id.button_ingredient_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ingredientHolder.getChildCount() - 2 != 0){
                    ingredientHolder.removeView(ingredient);
                }
                else {
                    final EditText ingName = (EditText) ingredient.findViewById(R.id.ingredient_name);
                    ingName.setText(null);
                    final EditText  ingAmt = (EditText) ingredient.findViewById(R.id.ingredient_amount);
                    ingAmt.setText(null);
                    final Spinner amountType = (Spinner) ingredient.findViewById(R.id.spinner_amountType);
                    amountType.setSelection(0);
                }
            }
        });

        Spinner amountType = (Spinner) ingredient.findViewById(R.id.spinner_amountType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.amount_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountType.setAdapter(adapter);

        ingredientHolder.addView(ingredient, ingredientHolder.getChildCount() - 1);
    }

    public static RecipeCreator getFragment(Bundle bundle) {
        RecipeCreator recipeCreationFragment = new RecipeCreator();
        recipeCreationFragment.setArguments(bundle);
        name = bundle.getString("name");
        description = bundle.getString("description");
        ingredientNames = bundle.getStringArrayList("ingredientsName");
        ingredientAmounts = bundle.getIntegerArrayList("ingredientsAmount");
        ingredientUnits = bundle.getStringArrayList("ingredientsUnit");
        return recipeCreationFragment;
    }

}
