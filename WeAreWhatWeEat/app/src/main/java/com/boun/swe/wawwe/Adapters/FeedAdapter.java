package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Fragments.BaseFragment;
import com.boun.swe.wawwe.Fragments.MenuDetail;
import com.boun.swe.wawwe.Fragments.RecipeDetail;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.BaseModel;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.ViewHolders.MenuViewHolder;
import com.boun.swe.wawwe.ViewHolders.RecipeViewHolder;
import com.boun.swe.wawwe.ViewHolders.SubRecipeViewHolder;
import com.boun.swe.wawwe.ViewHolders.UserViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by onurguler on 08/12/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * TODO sorted list updates same item with different content only if they fall into same position
     * therefore if one applies direct upgrade in list, duplicate entries created...
     */

    /**
     * TODO Previous implementation of expandable list does not applies for sortedList
     * new finding place for new entries below its parent is not easy...
     */
    SortedList<BaseModel> items;
    Context context;

    private final int RECIPE = 0, MENU = 1, USER = 2, SUB_RECIPE = 4;

    public FeedAdapter(Context context) {
        this.context = context;
        items = new SortedList<>(BaseModel.class, new SortedList.Callback<BaseModel>() {
            @Override
            public int compare(BaseModel o1, BaseModel o2) {
                return o1.compareTo(o2);
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(BaseModel oldItem, BaseModel newItem) {
                return newItem.isRecommended() == oldItem.isRecommended();
            }

            @Override
            public boolean areItemsTheSame(BaseModel item1, BaseModel item2) {
                return item1.getId() == item2.getId() &&
                        item1.getClass().equals(item2.getClass());
            }
        });
    }

    public void setData(BaseModel[] data) {
        if (items.size() != 0) {
            items.clear();
            notifyDataSetChanged();
        }
        addItems(data);
    }

    public void addItems(BaseModel[] data) {
        items.beginBatchedUpdates();
        items.addAll(Arrays.asList(data));
        items.endBatchedUpdates();
    }

    public void clear() {
        items.beginBatchedUpdates();
        items.clear();
        items.endBatchedUpdates();
    }

    @Override
    public int getItemViewType(int position) {
        Object data = items.get(position);
        if (data instanceof Recipe) {
            if (((Recipe) data).getParentItem()!= null)
                return SUB_RECIPE;
            else return RECIPE;
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

        View rowView;
        switch (viewType) {
            case RECIPE:
                rowView = inflater.inflate(R.layout.item_recipe, viewGroup, false);
                viewHolder = new RecipeViewHolder(rowView);
                break;
            case MENU:
                rowView = inflater.inflate(R.layout.item_menu, viewGroup, false);
                viewHolder = new MenuViewHolder(rowView);
                break;
            case SUB_RECIPE:
                rowView = inflater.inflate(R.layout.subitem_recipe, viewGroup, false);
                viewHolder = new SubRecipeViewHolder(rowView);
                break;
            default:
                //These lines are probably wrong :)
                rowView = inflater.inflate(R.layout.item_user, viewGroup, false);
                viewHolder = new UserViewHolder(rowView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        switch (viewHolder.getItemViewType()) {
            case SUB_RECIPE:
                SubRecipeViewHolder srvh = (SubRecipeViewHolder) viewHolder;
                configureSubRecipeViewHolder(srvh, position);
                break;
            case RECIPE:
                RecipeViewHolder rvh = (RecipeViewHolder) viewHolder;
                configureRecipeViewHolder(rvh, position);
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
                makeFragmentTransaction(RecipeDetail.getFragment(recipe));
            }
        });
        if (recipe.isRecommended()) {
            holder.recommended.setVisibility(View.VISIBLE);
        }
        else {
            holder.recommended.setVisibility(View.GONE);
        }
    }

    private void configureSubRecipeViewHolder(SubRecipeViewHolder holder, int position) {
        final Recipe recipe = (Recipe) items.get(position);

        holder.recipeName.setText(recipe.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                API.getRecipe(null, recipe.getId(),
                    new Response.Listener<Recipe>() {
                        @Override
                        public void onResponse(Recipe response) {
                            if (response != null)
                                makeFragmentTransaction(RecipeDetail.getFragment(response));
                            else
                                Toast.makeText(context, context.getString(R.string.error_requestRecipe),
                                        Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, context.getString(R.string.error_requestRecipe),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
    }

    private void configureMenuViewHolder(final MenuViewHolder holder, int position) {
        final Menu menu = (Menu) items.get(position);

        if (menu != null) {
            holder.menuName.setText(menu.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menu.isExpanded())
                        makeFragmentTransaction(MenuDetail.getFragment(menu));
                    else {
                        menu.setIsExpanded(true);

                        List<BaseModel> recipes = new ArrayList<>();

                        Iterator<String> names = menu.getRecipeNames().iterator();
                        Iterator<Integer> ids = menu.getRecipeIds().iterator();
                        while (names.hasNext() && ids.hasNext()) {
                            Recipe recipe = new Recipe(ids.next(), names.next());
                            recipe.setSubItem(menu);
                            recipe.setRating(menu.getRating());
                            recipes.add(recipe);
                        }

                        items.addAll(recipes);
                    }
                }
            });
        }
    }

    private void makeFragmentTransaction(BaseFragment fragment) {
        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            main.makeFragmentTransaction(fragment);
        }
    }
}
