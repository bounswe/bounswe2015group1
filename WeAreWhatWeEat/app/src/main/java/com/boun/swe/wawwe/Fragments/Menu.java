package com.boun.swe.wawwe.Fragments;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by akintoksan on 06/12/15.
 */
public class Menu implements ParentListItem {

    /* Create an instance variable for your list of children */
    private List<Object> mChildrenList;

    public String getTitle(){
        return null;
    }
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }
    @Override
    public List<?> getChildItemList() {
        return null;
    }
    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}