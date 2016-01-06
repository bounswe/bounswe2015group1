package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Allergy;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Nutrition;
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
import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by onurguler on 06/01/16.
 */
public class Stats extends BaseFragment{

    private RecyclerView consumedRecipes;

    public Stats() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = context.getString(R.string.title_menu_stats);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View statsView = inflater.inflate(R.layout.layout_fragment_stats,
                container, false);

        consumedRecipes = (RecyclerView) statsView.findViewById(R.id.consumed_recipes);
        consumedRecipes.setItemAnimator(new DefaultItemAnimator());
        consumedRecipes.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        consumedRecipes.setAdapter(adapter);

        // Set headers
        int[] headerIds = new int[] { R.id.stats_title_nutritions,
                R.id.title_consumed_recipes, R.id.title_allergies };
        int[] headerTextIds = new int[] { R.string.label_nutritients,
                R.string.label_consumed, R.string.label_allergies };
        for (int i = 0;i < headerIds.length;i++) {
            TextSurface header = (TextSurface) statsView.findViewById(headerIds[i]);
            Text text = Commons.generateHeader(headerTextIds[i]);
            header.play(new Sequential(
                    Delay.duration(i * 100),
                    new Parallel(
                            Slide.showFrom(Side.LEFT, text, 500),
                            ChangeColor.to(text, 750, context.getResources()
                                    .getColor(R.color.colorAccent))
                    )));
        }

        final TagGroup tagGroup = (TagGroup) statsView.findViewById(R.id.allergies_tagGroup);
        final List<String> allergies = new ArrayList<>();
        API.getUserAllergy(getTag(),
                new Response.Listener<Allergy[]>() {
                    @Override
                    public void onResponse(Allergy[] response) {
                        if(response != null){
                            for(int i=0;i<response.length;i++){
                                allergies.add(response[i].getIngredientName());
                            }
                        }
                        //TODO no allergy text should be added
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could not get user allergies", Toast.LENGTH_SHORT).show();
                    }
                });
        tagGroup.setTags(allergies);


        API.getAllRecipesConsumed(getTag(), new Response.Listener<Recipe[]>() {
            @Override
            public void onResponse(Recipe[] response) {
                if (response != null)
                    adapter.addItems(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Could not get consumed recipes", Toast.LENGTH_SHORT).show();
            }
        });

        final TextSurface nutritionHolderLeft = (TextSurface) statsView.findViewById(R.id.nutritionsLeft);
        final TextSurface nutritionHolderMiddle = (TextSurface) statsView.findViewById(R.id.nutritionsMiddle);
        final TextSurface nutritionHolderRight = (TextSurface) statsView.findViewById(R.id.nutritionsRight);

        API.getDailyAverageConsumed(getTag(),
                new Response.Listener<Nutrition>() {
                    @Override
                    public void onResponse(Nutrition response) {
                        if(response != null) {
                            String[] names = context.getResources().getStringArray(R.array.prompt_nutritions);
                            float[] values = response.getNutritionsAsArray();

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
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Could not get consumed nutritions", Toast.LENGTH_SHORT).show();
                    }
                });

        return statsView;
    }

    public static Stats getFragment(Bundle bundle) {
        Stats statsFragment = new Stats();
        statsFragment.setArguments(bundle);
        return statsFragment;
    }


}
