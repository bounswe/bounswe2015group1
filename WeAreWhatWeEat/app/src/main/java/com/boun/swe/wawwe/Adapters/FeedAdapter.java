package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boun.swe.wawwe.Fragments.RecipeDetail;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mert on 17/10/15.
 *
 * Classic recycler view adapter implementation
 * with generic methods are included.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.UserViewHolder> {

    Context context;
    ArrayList<Recipe> data = new ArrayList<>();

    public FeedAdapter(Context context) {
        this.context = context;
    }

    public void setData(Recipe[] recipes) {
        if (data.size() != 0) {
            data.clear();
            notifyDataSetChanged();
        }
        addItems(recipes);
    }

    public void addItems(Recipe[] recipes) {
        int start = data.size();
        data.addAll(Arrays.asList(recipes));
        notifyItemRangeChanged(start, recipes.length);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(userItemView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Recipe recipe = data.get(position);

        holder.recipeName.setText(recipe.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    Bundle recipeBundle = new Bundle();
                    recipeBundle.putParcelable("recipe", recipe);
                    main.makeFragmentTransaction(RecipeDetail
                            .getFragment(recipeBundle));
                }
            }
        });
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public ImageView recipePic;
        public TextView recipeName;

        public UserViewHolder(View itemView) {
            super(itemView);

            recipePic = (ImageView) itemView.findViewById(R.id.recipePic_small);
            recipeName = (TextView) itemView.findViewById(R.id.userEmail);
        }
    }
}
