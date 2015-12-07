//package com.boun.swe.wawwe.Adapters;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
//import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
//import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
//import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
//import com.boun.swe.wawwe.R;
//
//import java.util.List;
//
///**
// * Created by akintoksan on 06/12/15.
// */
//public class MenuExpandableAdapter extends ExpandableRecyclerAdapter
//        <MenuExpandableAdapter.MenuParentViewHolder, MenuExpandableAdapter.MenuChildViewHolder> {
//    /**
//     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
//     * <p/>
//     * Changes to {@link #mParentItemList} should be made through add/remove methods in
//     * {@link ExpandableRecyclerAdapter}
//     *
//     * @param parentItemList List of all {@link ParentListItem} objects to be
//     *                       displayed in the RecyclerView that this
//     *                       adapter is linked to
//     */
//    Context context;
//    LayoutInflater mInflater;
//
//
//    public MenuExpandableAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
//        super(parentItemList);
//        mInflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public MenuParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
//        View view = mInflater.inflate(R.layout.layout_profile_parent, parentViewGroup, false);
//        return new MenuParentViewHolder(view);
//    }
//
//    @Override
//    public MenuChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
//        View view = mInflater.inflate(R.layout.layout_profile_child, childViewGroup, false);
//        return new MenuChildViewHolder(view);
//    }
//
//    @Override
//    public void onBindParentViewHolder(final MenuParentViewHolder parentViewHolder, int position, final ParentListItem parentListItem) {
//        Menu menu = (Menu) parentListItem;
////        parentViewHolder.mCrimeTitleTextView.setText(menu.getTitle());
//        parentViewHolder.mParentDropDownArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                parentViewHolder.setExpanded(true);
//            }
//        });
//    }
//
//    @Override
//    public void onBindChildViewHolder(MenuChildViewHolder childViewHolder, int position, Object childListItem) {
//        MenuChild menuChild = (MenuChild) childListItem;
//        //childViewHolder.mCrimeDateText.setText();
//        //childViewHolder.mCrimeSolvedCheckBox.setChecked();
//    }
//
//    public static class MenuChildViewHolder extends ChildViewHolder {
//
//        public TextView recipeText;
//
//        public MenuChildViewHolder(View itemView) {
//            super(itemView);
//            recipeText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);
//        }
//    }
//
//    public static class MenuParentViewHolder extends ParentViewHolder {
//
//        public TextView mCrimeTitleTextView;
//        public ImageButton mParentDropDownArrow;
//
//        public MenuParentViewHolder(View itemView) {
//            super(itemView);
//
//            //mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.);
//            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
//        }
//    }
//
//}
//
//package com.boun.swe.wawwe.Fragments;
//
//        import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
//
//      import java.util.List;
//
/**
 * Created by akintoksan on 06/12/15.
 */
//public class Menu implements ParentListItem {
//
//  /* Create an instance variable for your list of children */
//  private List<MenuChild> mChildrenList;
//
//  public String getTitle(){
//      return null;
//  }
//  public List<MenuChild> getChildObjectList() {
//      return mChildrenList;
//  }
//  public void setChildObjectList(List<MenuChild> list) {
//      mChildrenList = list;
//  }
//  @Override
//  public List<?> getChildItemList() {
//      return null;
//  }
//  @Override
//  public boolean isInitiallyExpanded() {
//      return false;
//  }
//}

//package com.boun.swe.wawwe.Fragments;
//
//      import java.util.Date;
//
///**
//* Created by akintoksan on 06/12/15.
//*/
//
//public class MenuChild {
//
//  private Date mDate;
//  private boolean mSolved;
//
//  public MenuChild() {
//  }
//
//  /**
//   * Getter and setter methods
//   */
//}
//package com.boun.swe.wawwe.Fragments;
//
//        import java.util.Date;
//
///**
// * Created by akintoksan on 06/12/15.
// */
//
//public class MenuChild {
//
//  private Date mDate;
//  private boolean mSolved;
//
//  public MenuChild() {
//  }
//
//  /**
//   * Getter and setter methods
//   */
//}
