package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boun.swe.wawwe.Fragments.RecipeDetail;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.ViewHolders.MenuViewHolder;
import com.boun.swe.wawwe.ViewHolders.RecipeViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by onurguler on 08/12/15.
 */
public class NewFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Object> items = new ArrayList<>();
    Context context;

    private final int RECIPE = 0, MENU = 1;

    public NewFeedAdapter(Context context) {
        this.context = context;
    }

    public void setData(Recipe[] recipes) {
        if (items.size() != 0) {
            items.clear();
            notifyDataSetChanged();
        }
        addItems(recipes);
    }

    public void addItems(Recipe[] recipes) {
        int start = items.size();
        items.addAll(Arrays.asList(recipes));
        notifyItemRangeChanged(start, recipes.length);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case RECIPE:
                View v1 = inflater.inflate(R.layout.item_recipe, viewGroup, false);
                viewHolder = new RecipeViewHolder(v1);
                break;
            case MENU:
                View v2 = inflater.inflate(R.layout.item_menu, viewGroup, false);
                viewHolder = new MenuViewHolder(v2) {
                };
                break;
            default:
                //These lines are probably wrong :)
                View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                viewHolder = new FeedAdapter.UserViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case RECIPE:
                RecipeViewHolder rvh = (RecipeViewHolder) viewHolder;
                configureRecipeViewHolder(rvh, position);
                rvh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context instanceof MainActivity) {
                            MainActivity main = (MainActivity) context;
                            main.makeFragmentTransaction(RecipeDetail.getFragment((Recipe)items.get(position)));
                        }
                    }
                });
                break;
            case MENU:
                MenuViewHolder mvh = (MenuViewHolder) viewHolder;
                configureMenuViewHolder(mvh, position);
                break;
            default:
                //Again, these lines does not make sense :)
                FeedAdapter.UserViewHolder vh = (FeedAdapter.UserViewHolder) viewHolder;
                configureUserViewHolder(vh, position);
                break;
        }
    }

    private void configureUserViewHolder(FeedAdapter.UserViewHolder vh, int position) {
        // I dont even know what to write here :)
    }

    private void configureRecipeViewHolder(RecipeViewHolder rvh, int position) {
        Recipe recipe = (Recipe) items.get(position);
        if (recipe != null) {
            rvh.getItemRecipeName().setText(recipe.getName());
        }
    }

    private void configureMenuViewHolder(MenuViewHolder mvh, int position) {
        Menu menu = (Menu) items.get(position);
        if(menu != null){
            mvh.getMenuName().setText(menu.getName());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Recipe) {
            return RECIPE;
        } else if (items.get(position) instanceof Menu) {
            return MENU;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
