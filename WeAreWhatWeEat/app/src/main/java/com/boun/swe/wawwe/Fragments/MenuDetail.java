package com.boun.swe.wawwe.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.CustomViews.CommentRatingView;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.contants.Side;
import su.levenetc.android.textsurface.utils.Utils;

/**
 * Created by onurguler on 08/12/15.
 */
public class MenuDetail extends LeafFragment {

    private Menu menu;

    private RecyclerView recView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = App.getInstance().getString(R.string.title_menu_menuDetail);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View menuDetailView = inflater.inflate(R.layout.layout_fragment_menu_detail,
                container, false);

        menu = getArguments().getParcelable("menu");

        TextView descriptionTextView = (TextView) menuDetailView.findViewById(R.id.mDetail_description);
        descriptionTextView.setText(menu.getDescription());

        // Set headers
        int[] headerIds = new int[]{
                R.id.mDetail_title_menuName,
                R.id.mDetail_title_description };
        String[] headerTexts = new String[] {
                menu.getName(),
                Commons.getString(R.string.label_menuDesc)
        };
        for (int i = 0; i < headerIds.length; i++) {
            TextSurface header = (TextSurface) menuDetailView.findViewById(headerIds[i]);
            Text text = Commons.generateHeader(headerTexts[i]);
            header.play(new Sequential(
                    Delay.duration(i * 100),
                    new Parallel(
                            Slide.showFrom(Side.LEFT, text, 500),
                            ChangeColor.to(text, 750, context.getResources()
                                    .getColor(R.color.colorAccent))
                    )));
        }

        API.getUserInfo(getTag(), menu.getUserId(),
                new Response.Listener<User>() {
                    @Override
                    public void onResponse(User response) {
                        int[] headerIds = new int[]{
                                R.id.mDetail_createdBy,
                                R.id.mDetail_period };
                        String[] headerTexts = new String[] {
                                response.getFullName(),
                                Commons.getString(R.string.label_menuPeriodF, menu.getPeriod())
                        };
                        for (int i = 0; i < headerIds.length; i++) {
                            TextSurface header = (TextSurface) menuDetailView.findViewById(headerIds[i]);
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

        recView = (RecyclerView) menuDetailView.findViewById(R.id.recipe_item_holder);
        recView.setItemAnimator(new DefaultItemAnimator());
        recView.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        recView.setAdapter(adapter);

        API.getRecipesforMenu(getTag(), menu.getId(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null) {
                            int factor = response.length < 3 ? response.length : 3;
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                    recView.getLayoutParams();
                            params.height = (int) Utils.dpToPx(factor * 80);
                            recView.setLayoutParams(params);

                            menuDetailView.invalidate();

                            adapter.addItems(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(App.getInstance(), "Could not get recipes of menu",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        LinearLayout holder = (LinearLayout) menuDetailView
                .findViewById(R.id.menuDetail_holder);
        View commentView = new CommentRatingView.Builder(context, this)
                .setParent(menu)
                .create();
        holder.addView(commentView);

        return menuDetailView;
    }

    public static MenuDetail getFragment(Menu menu) {
        MenuDetail menuDetailFragment = new MenuDetail();

        Bundle bundle = new Bundle();
        bundle.putParcelable("menu", menu);

        menuDetailFragment.setArguments(bundle);
        return menuDetailFragment;
    }
}
