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
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;

import java.util.ArrayList;
import java.util.List;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by onurguler on 06/12/15.
 */
public class MenuCreator extends LeafFragment{

    List<Recipe> userSpecificRecipes = new ArrayList<Recipe>();

    public MenuCreator() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        TAG = context.getString(R.string.title_menu_menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View menuView = inflater.inflate(R.layout.layout_fragment_menu_creation,
                container, false);

        final EditText menuName = (EditText) menuView.findViewById(R.id.menuName);
        final EditText menuDesc = (EditText) menuView.findViewById(R.id.menuDesc);

        Button addRecipeToMenuButton = (Button) menuView.findViewById(R.id.addNewRecipeToMenu);

        final LinearLayout recipeHolder = (LinearLayout) menuView.findViewById(R.id.recipe_item_holder);

        addRecipeToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipeRow(recipeHolder);
            }
        });

        //Get Recipes of the user from server for showing them on spinner
        API.getAllRecipes(getTag(),    //TODO Change the api call to getUserRecipes after api is ready
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null) {
                            for(int i=0;i<response.length;i++){
                                userSpecificRecipes.add(response[i]);
                            }
                        }
                        addRecipeRow(recipeHolder);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, context.getString(R.string.error_getUserRecipes),
                                Toast.LENGTH_SHORT).show();
                    }
                });


        // Set headers
        int[] headerIds = new int[] { R.id.menuCreationNamelbl, R.id.menuCreationDesclbl,
                R.id.menuCreationPeriodlbl, R.id.menuCreationRecipeIdslbl };
        int[] headerTextIds = new int[] { R.string.label_menuName, R.string.label_menuDesc,
                R.string.label_menuPeriod, R.string.label_menuRecIds };
        for (int i = 0;i < headerIds.length;i++) {
            TextSurface header = (TextSurface) menuView.findViewById(headerIds[i]);
            Text text = Commons.generateHeader(headerTextIds[i]);
            header.play(new Sequential(
                    Delay.duration(i * 100),
                    new Parallel(
                            Slide.showFrom(Side.LEFT, text, 500),
                            ChangeColor.to(text, 750, context.getResources()
                                    .getColor(R.color.colorAccent))
                    )));
        }

        //Set Spinner for period field
        final Spinner periodSpinner = (Spinner) menuView.findViewById(R.id.menuPeriodSpinner);
        ArrayAdapter<CharSequence> periodSpinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.menu_period_array, android.R.layout.simple_spinner_item);
        periodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodSpinner.setAdapter(periodSpinnerAdapter);

        menuView.findViewById(R.id.button_menu_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = menuName.getText().toString();
                String period = periodSpinner.getSelectedItem().toString();
                String description = menuDesc.getText().toString();
                List<Integer> recipeIds = new ArrayList<Integer>();

                int count = recipeHolder.getChildCount();
                for(int i=0;i<count;i++){
                    View view = recipeHolder.getChildAt(i);
                    if(view instanceof Spinner){
                        int itemPos = ((Spinner) view).getSelectedItemPosition();
                        recipeIds.add(userSpecificRecipes.get(itemPos).getId());
                    }
                }

                com.boun.swe.wawwe.Models.Menu menu = new com.boun.swe.wawwe.Models.Menu(name,period,recipeIds,description);

                API.createMenu(getTag(), menu,
                        new Response.Listener<com.boun.swe.wawwe.Models.Menu>() {
                            @Override
                            public void onResponse(com.boun.swe.wawwe.Models.Menu response) {
                                if (context instanceof MainActivity) {
                                    MainActivity main = (MainActivity) context;
                                    main.onBackPressed();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, context.getString(R.string.error_createMenu),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        return menuView;
    }

    private View addRecipeRow(final LinearLayout recipeHolder) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        final View recipeRow = inflater.inflate(R.layout.item_recipespinner_menucreation,
                recipeHolder, false);

        //Initialize Spinner with User Recipes
        final Spinner spinner = (Spinner) recipeRow.findViewById(R.id.menu_spinner_recipe_row);
        ArrayAdapter<Recipe> userRecipesArrayAdapter = new ArrayAdapter<Recipe>(context,
                android.R.layout.simple_spinner_item, userSpecificRecipes);
        userRecipesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(userRecipesArrayAdapter);

        final ImageButton delete = (ImageButton) recipeRow.findViewById(R.id.button_recipe_row_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeHolder.getChildCount() - 2 != 0) {
                    recipeHolder.removeView(recipeRow);
                } else {
                    spinner.setSelection(0);
                }
            }
        });

        recipeHolder.addView(recipeRow, recipeHolder.getChildCount() - 1);
        return recipeRow;
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
        return super.onOptionsItemSelected(item);
    }

    public static MenuCreator getFragment(Bundle bundle) {
        MenuCreator menuFragment = new MenuCreator();
        menuFragment.setArguments(bundle);
        return menuFragment;
    }

}
