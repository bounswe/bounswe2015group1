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
import com.boun.swe.wawwe.Models.Menu;
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
        int[] headerIds = new int[] { R.id.title_stats, R.id.stats_title_nutritions, R.id.title_consumed_recipes };
        int[] headerTextIds = new int[] { R.string.label_stats, R.string.label_nutritients, R.string.label_consumed};
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

        TextSurface nutritionHolderLeft = (TextSurface) statsView.findViewById(R.id.nutritionsLeft);
        TextSurface nutritionHolderMiddle = (TextSurface) statsView.findViewById(R.id.nutritionsMiddle);
        TextSurface nutritionHolderRight = (TextSurface) statsView.findViewById(R.id.nutritionsRight);



        return statsView;
    }

    public static Stats getFragment(Bundle bundle) {
        Stats statsFragment = new Stats();
        statsFragment.setArguments(bundle);
        return statsFragment;
    }


}
