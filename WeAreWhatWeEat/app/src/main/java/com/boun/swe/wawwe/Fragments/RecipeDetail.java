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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.CustomViews.CommentRatingView;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Nutrition;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;

import java.util.ArrayList;

import me.gujun.android.taggroup.TagGroup;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View recipeDetailView = inflater.inflate(R.layout.layout_fragment_recipe_detail,
                container, false);
        recipe = getArguments().getParcelable("recipe");

        ImageView recipeImage = (ImageView) recipeDetailView.findViewById(R.id.recipeImage);
        TextView directions = (TextView) recipeDetailView.findViewById(R.id.description);

        final TagGroup tagGroupStatic = (TagGroup) recipeDetailView.findViewById(R.id.tag_group_static);
        API.getRecipeTags(getTag(), recipe.getId(),
                new Response.Listener<String[]>() {
                    @Override
                    public void onResponse(String[] response) {
                        if (response == null) {
                            // TODO do not show tags area...
                        } else {
                            tagGroupStatic.setTags(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO do not show tags area...
                    }
                });
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
        int[] headerIds = new int[]{R.id.rDetail_title_recipeName, R.id.rDetail_title_ingredients,
                R.id.rDetail_title_directions, R.id.rDetail_title_tags, R.id.rDetail_title_nutritions};
        int[] headerTextIds = new int[]{R.string.title_recipe, R.string.title_ingredients,
                R.string.title_directions, R.string.title_tags, R.string.title_nutrition};
        for (int i = 0; i < headerIds.length; i++) {
            TextSurface header = (TextSurface) recipeDetailView.findViewById(headerIds[i]);
            Text text = i == 0 ? Commons.generateHeader(recipe.getName()) :
                    Commons.generateHeader(headerTextIds[i]);
            header.play(new Sequential(
                    Delay.duration(i * 100),
                    new Parallel(
                            Slide.showFrom(Side.LEFT, text, 500),
                            ChangeColor.to(text, 750, context.getResources()
                                    .getColor(R.color.colorAccent))
                    )));
        }


        API.getUserInfo(getTag(), recipe.getUserId(),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        int[] headerIds = new int[]{
                                R.id.rDetail_createdBy};
                        String[] headerTexts = new String[] {
                                response.getFullName()
                        };
                        for (int i = 0; i < headerIds.length; i++) {
                            TextSurface header = (TextSurface) recipeDetailView.findViewById(headerIds[i]);
                            Text text = Commons.generateText(headerTexts[i], 20, R.color.white);
                            header.play(new Sequential(
                                    Delay.duration(i * 100),
                                    new Parallel(
                                            Slide.showFrom(Side.LEFT, text, 500)
                                    )));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(App.getInstance(), "Could not get the user",
                                Toast.LENGTH_SHORT).show();
                    }
                });



        // Show ingredients
        directions.setText(recipe.getDescription());
        TextSurface ingredientHolder = (TextSurface) recipeDetailView.findViewById(R.id.rDetail_ingredientHolder);
        Text previous = null;
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = recipe.getIngredients().get(i);

            Text text = Commons.generateText(String.format("%d x %s",
                    ingredient.getAmount(), ingredient.getName()));
            if (previous != null)
                text.setPosition(new Position(Align.CENTER_OF | Align.BOTTOM_OF, previous));

            ingredientHolder.play(new Sequential(
                    Delay.duration((i + 1) * 100),
                    Slide.showFrom(Side.LEFT, text, 500)));

            previous = text;
        }
        if (previous != null) {
            int ingredientCount = recipe.getIngredients().size();
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ingredientHolder.getLayoutParams();
            params.height = (int) (previous.getHeight() * ingredientCount);
            ingredientHolder.setLayoutParams(params);

            if (ingredientCount > 1)
                ingredientHolder.getCamera()
                        .setTransY(-(params.height - previous.getHeight()) / 2);
        }

        TextSurface nutritionHolderLeft = (TextSurface) recipeDetailView.findViewById(R.id.nutritionsLeft);
        TextSurface nutritionHolderMiddle = (TextSurface) recipeDetailView.findViewById(R.id.nutritionsMiddle);
        TextSurface nutritionHolderRight = (TextSurface) recipeDetailView.findViewById(R.id.nutritionsRight);

        String[] names = context.getResources().getStringArray(R.array.prompt_nutritions);
        float[] values = recipe.getNutritions().getNutritionsAsArray();
        Text[] texts = new Text[9];
        for (int i = 0; i < values.length; i++)
            texts[i] = Commons.generateText(String.format(names[i], values[i]));

        for (int col = 0; col < 3; col++) {
            TextSurface nutritionHolder = col == 0 ? nutritionHolderLeft :
                    col == 1 ? nutritionHolderMiddle : nutritionHolderRight;

            for (int row = 0; row < 3; row++) {
                Text text = texts[row * 3 + col];
                if (row != 1)
                    text.setPosition(new Position(Align.CENTER_OF | (row == 0 ?
                            Align.TOP_OF : Align.BOTTOM_OF), texts[col + 3]));

                nutritionHolder.play(new Parallel(
                        Slide.showFrom(Side.LEFT, text, 500),
                        ChangeColor.to(text, 750, context.getResources()
                                .getColor(R.color.colorAccent))
                ));
            }
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) nutritionHolderLeft.getLayoutParams();
        params.height = (int) (texts[0].getHeight() * 4);
        nutritionHolderLeft.setLayoutParams(params);
        nutritionHolderMiddle.setLayoutParams(params);
        nutritionHolderRight.setLayoutParams(params);

        LinearLayout holder = (LinearLayout) recipeDetailView.findViewById(R.id.recipeDetail_holder);
        View commentView = new CommentRatingView.Builder(context, this)
                .setParent(recipe)
                .create();
        holder.addView(commentView);

        return recipeDetailView;
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
                    main.makeFragmentTransaction(RecipeCreator.getFragment(recipe, true));
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
