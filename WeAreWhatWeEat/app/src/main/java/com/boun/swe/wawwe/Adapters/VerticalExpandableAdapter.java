//package com.boun.swe.wawwe.Adapters;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Build;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
//import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
//import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
//import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
//import com.boun.swe.wawwe.Models.VerticalChild;
//import com.boun.swe.wawwe.Models.VerticalParent;
//import com.boun.swe.wawwe.R;
//
//import java.util.List;
//
///**
// * Created by akintoksan on 08/12/15.
// */
//public class VerticalExpandableAdapter extends ExpandableRecyclerAdapter<VerticalParentViewHolder, VerticalChildViewHolder> {
//
//    private LayoutInflater mInflater;
//
//    /**
//     * Public primary constructor.
//     *
//     * @param parentItemList the list of parent items to be displayed in the RecyclerView
//     */
//    public VerticalExpandableAdapter(Context context, List<? extends ParentListItem> parentItemList) {
//        super(parentItemList);
//        mInflater = LayoutInflater.from(context);
//    }
//
//    /**
//     * OnCreateViewHolder implementation for parent items. The desired ParentViewHolder should
//     * be inflated here
//     *
//     * @param parent for inflating the View
//     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
//     */
//    @Override
//    public VerticalParentViewHolder onCreateParentViewHolder(ViewGroup parent) {
//        View view = mInflater.inflate(R.layout.list_item_parent_vertical, parent, false);
//        return new VerticalParentViewHolder(view);
//    }
//
//    /**
//     * OnCreateViewHolder implementation for child items. The desired ChildViewHolder should
//     * be inflated here
//     *
//     * @param parent for inflating the View
//     * @return the user's custom parent ViewHolder that must extend ParentViewHolder
//     */
//    @Override
//    public VerticalChildViewHolder onCreateChildViewHolder(ViewGroup parent) {
//        View view = mInflater.inflate(R.layout.list_item_child_vertical, parent, false);
//        return new VerticalChildViewHolder(view);
//    }
//
//    /**
//     * OnBindViewHolder implementation for parent items. Any data or view modifications of the
//     * parent view should be performed here.
//     *
//     * @param parentViewHolder the ViewHolder of the parent item created in OnCreateParentViewHolder
//     * @param position the position in the RecyclerView of the item
//     */
//    @Override
//    public void onBindParentViewHolder(VerticalParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
//        VerticalParent verticalParent = (VerticalParent) parentListItem;
//        parentViewHolder.bind(verticalParent.getParentNumber(), verticalParent.getParentText());
//    }
//
//    /**
//     * OnBindViewHolder implementation for child items. Any data or view modifications of the
//     * child view should be performed here.
//     *
//     * @param childViewHolder the ViewHolder of the child item created in OnCreateChildViewHolder
//     * @param position the position in the RecyclerView of the item
//     */
//    @Override
//    public void onBindChildViewHolder(VerticalChildViewHolder childViewHolder, int position, Object childListItem) {
//        VerticalChild verticalChild = (VerticalChild) childListItem;
//        childViewHolder.bind(verticalChild.getChildText());
//    }
//}
//
//public class VerticalParentViewHolder extends ParentViewHolder {
//
//    private static final float INITIAL_POSITION = 0.0f;
//    private static final float ROTATED_POSITION = 180f;
//    private static final float PIVOT_VALUE = 0.5f;
//    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
//    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
//
//    public TextView mNumberTextView;
//    public TextView mDataTextView;
//    public ImageView mArrowExpandImageView;
//
//    /**
//     * Public constructor for the CustomViewHolder.
//     *
//     * @param itemView the view of the parent item. Find/modify views using this.
//     */
//    public VerticalParentViewHolder(View itemView) {
//        super(itemView);
//
//        mNumberTextView = (TextView) itemView.findViewById(R.id.list_item_parent_vertical_number_textView);
//        mDataTextView = (TextView) itemView.findViewById(R.id.list_item_parent_vertical_parent_textView);
//        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.list_item_parent_horizontal_arrow_imageView);
//    }
//
//    public void bind(int parentNumber, String parentText) {
//        mNumberTextView.setText(String.valueOf(parentNumber));
//        mDataTextView.setText(parentText);
//    }
//
//    @SuppressLint("NewApi")
//    @Override
//    public void setExpanded(boolean expanded) {
//        super.setExpanded(expanded);
//        if (!HONEYCOMB_AND_ABOVE) {
//            return;
//        }
//
//        if (expanded) {
//            mArrowExpandImageView.setRotation(ROTATED_POSITION);
//        } else {
//            mArrowExpandImageView.setRotation(INITIAL_POSITION);
//        }
//    }
//
//    @Override
//    public void onExpansionToggled(boolean expanded) {
//        super.onExpansionToggled(expanded);
//        if (!HONEYCOMB_AND_ABOVE) {
//            return;
//        }
//
//        RotateAnimation rotateAnimation = new RotateAnimation(ROTATED_POSITION,
//                INITIAL_POSITION,
//                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE,
//                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE);
//        rotateAnimation.setDuration(DEFAULT_ROTATE_DURATION_MS);
//        rotateAnimation.setFillAfter(true);
//        mArrowExpandImageView.startAnimation(rotateAnimation);
//    }
//}
//
//public class VerticalChildViewHolder extends ChildViewHolder {
//
//    public TextView mDataTextView;
//
//    /**
//     * Public constructor for the custom child ViewHolder
//     *
//     * @param itemView the child ViewHolder's view
//     */
//    public VerticalChildViewHolder(View itemView) {
//        super(itemView);
//
//        mDataTextView = (TextView) itemView.findViewById(R.id.list_item_vertical_child_textView);
//    }
//
//    public void bind(String childText) {
//        mDataTextView.setText(childText);
//    }
//}
