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
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.ViewHolders.MenuViewHolder;
import com.boun.swe.wawwe.ViewHolders.RecipeViewHolder;
import com.boun.swe.wawwe.ViewHolders.UserViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by onurguler on 08/12/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Object> items = new ArrayList<>();
    Context context;

    private final int RECIPE = 0, MENU = 1, USER = 2;

    public FeedAdapter(Context context) {
        this.context = context;
    }

    public void setData(Object[] data) {
        if (items.size() != 0) {
            items.clear();
            notifyDataSetChanged();
        }
        addItems(data);
    }

    public void addItems(Object[] data) {
        int start = items.size();
        items.addAll(Arrays.asList(data));
        notifyItemRangeChanged(start, data.length);
    }

    @Override
    public int getItemViewType(int position) {
        Object data = items.get(position);
        if (data instanceof Recipe) {
            return RECIPE;
        } else if (data instanceof Menu) {
            return MENU;
        } else if (data instanceof User) {
            return USER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
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
                View v = inflater.inflate(R.layout.item_user, viewGroup, false);
                viewHolder = new UserViewHolder(v);
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
                            main.makeFragmentTransaction(RecipeDetail.getFragment((Recipe) items.get(position)));
                        }
                    }
                });
                break;
            case MENU:
                MenuViewHolder mvh = (MenuViewHolder) viewHolder;
                configureMenuViewHolder(mvh, position);
                break;
            case USER:
                //Again, these lines does not make sense :)
                UserViewHolder uvh = (UserViewHolder) viewHolder;
                configureUserViewHolder(uvh, position);
                break;
        }
    }

    private void configureUserViewHolder(UserViewHolder holder, int position) {
        final User user = (User) items.get(position);

        if(user != null) {

        }
    }

    private void configureRecipeViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = (Recipe) items.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    main.makeFragmentTransaction(RecipeDetail.getFragment(recipe));
                }
            }
        });
    }

    private void configureMenuViewHolder(MenuViewHolder holder, int position) {
        final Menu menu = (Menu) items.get(position);

        if(menu != null) {
            holder.menuName.setText(menu.getName());
        }
    }
}
