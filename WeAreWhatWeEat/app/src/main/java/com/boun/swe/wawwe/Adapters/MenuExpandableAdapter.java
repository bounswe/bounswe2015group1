package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.boun.swe.wawwe.CustomViews.MenuChildViewHolder;
import com.boun.swe.wawwe.CustomViews.MenuParentViewHolder;
import com.boun.swe.wawwe.Fragments.Menu;
import com.boun.swe.wawwe.Fragments.MenuChild;
import com.boun.swe.wawwe.R;

import java.util.List;

/**
 * Created by akintoksan on 06/12/15.
 */
public class MenuExpandableAdapter extends ExpandableRecyclerAdapter<MenuParentViewHolder, MenuChildViewHolder> {
    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p/>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     */
    Context context;
    LayoutInflater mInflater;


    public MenuExpandableAdapter(@NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MenuParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View view = mInflater.inflate(R.layout.layout_profile_item_parent, parentViewGroup, false);
        return new MenuParentViewHolder(view);
    }

    @Override
    public MenuChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View view = mInflater.inflate(R.layout.layout_profile_item_child, childViewGroup, false);
        return new MenuChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(MenuParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        Menu menu = (Menu) parentListItem;
        parentViewHolder.mCrimeTitleTextView.setText(menu.getTitle());
    }

    @Override
    public void onBindChildViewHolder(MenuChildViewHolder childViewHolder, int position, Object childListItem) {
        MenuChild menuChild = (MenuChild) childListItem;
        //childViewHolder.mCrimeDateText.setText();
        //childViewHolder.mCrimeSolvedCheckBox.setChecked();
    }
}
