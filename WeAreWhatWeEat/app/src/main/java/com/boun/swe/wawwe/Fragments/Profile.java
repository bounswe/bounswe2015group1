package com.boun.swe.wawwe.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.boun.swe.wawwe.Adapters.VerticalExpandableAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.CustomViews.VerticalChild;
import com.boun.swe.wawwe.CustomViews.VerticalParent;
import com.boun.swe.wawwe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mert on 09/11/15.
 */
public class Profile extends BaseFragment implements ExpandableRecyclerAdapter.ExpandCollapseListener {

    ImageView avatar;
    TextView fullName;
    TextView location;
    TextView dateOfBirth;
    private static final int NUM_TEST_DATA_ITEMS = 20;

    private VerticalExpandableAdapter mExpandableAdapter;

    RecyclerView myFeeds;

    public Profile() {
        TAG = App.getInstance().getString(R.string.title_menu_profile);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.layout_fragment_profile,
                container, false);

        avatar = (ImageView) profileView.findViewById(R.id.profile_image);
        fullName = (TextView) profileView.findViewById(R.id.profile_fullName);
        location = (TextView) profileView.findViewById(R.id.profile_location);
        dateOfBirth = (TextView) profileView.findViewById(R.id.profile_dateOfBirth);

        myFeeds = (RecyclerView) profileView.findViewById(R.id.myFeed);
        // Create a new adapter with 20 test data items
        mExpandableAdapter = new VerticalExpandableAdapter(context, setUpTestData(NUM_TEST_DATA_ITEMS));

        // Attach this activity to the Adapter as the ExpandCollapseListener
        mExpandableAdapter.setExpandCollapseListener(this);

        // Set the RecyclerView's adapter to the ExpandableAdapter we just created
        myFeeds.setAdapter(mExpandableAdapter);
        // Set the layout manager to a LinearLayout manager for vertical list
        myFeeds.setLayoutManager(new LinearLayoutManager(context));


      /*  myFeeds.setItemAnimator(new DefaultItemAnimator());
        myFeeds.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        myFeeds.setAdapter(adapter);

        API.getAllRecipes(getTag(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null)
                            adapter.setData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO could not load...
                    }
                });*/

        User user = App.getUser();
        setUserInfo(user);

        profileView.findViewById(R.id.action_recipe_create)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context instanceof MainActivity) {
                            MainActivity main = (MainActivity) context;
                            main.makeFragmentTransaction(RecipeCreator.getFragment(null));
                        }
                    }
                });

        profileView.findViewById(R.id.action_menu_create)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context instanceof MainActivity) {
                            MainActivity main = (MainActivity) context;
                            main.makeFragmentTransaction(MenuCreator.getFragment(null));
                        }
                    }
                });

        return profileView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;
                    main.makeFragmentTransaction(ProfileEdit.getFragment(new Bundle()));
                }
                return true;
            case R.id.menu_profile_add:
                if (context instanceof MainActivity) {
                    MainActivity main = (MainActivity) context;

                    main.makeFragmentTransaction(RecipeCreator.getFragment(null));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUserInfo(User user) {
        if (user == null) return;
        if (user.getFullName() != null)
            fullName.setText(user.getFullName());
        if (user.getLocation() != null)
            location.setText(user.getLocation());
        if (user.getDateOfBirth() != null)
            dateOfBirth.setText(user.getDateOfBirth());
    }

    public static Profile getFragment(Bundle bundle) {
        Profile profileFragment = new Profile();
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public void onListItemExpanded(int position) {
        Toast.makeText(context, "Expanded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListItemCollapsed(int position) {
        Toast.makeText(context, "Collapsed", Toast.LENGTH_SHORT).show();
    }
    private List<VerticalParent> setUpTestData(int numItems) {
        List<VerticalParent> verticalParentList = new ArrayList<>();

        for (int i = 0; i < numItems; i++) {
            List<VerticalChild> childItemList = new ArrayList<>();

            VerticalChild verticalChild = new VerticalChild();
            verticalChild.setChildText("FirstChild");
            childItemList.add(verticalChild);

            // Evens get 2 children, odds get 1
            if (i % 2 == 0) {
                VerticalChild verticalChild2 = new VerticalChild();
                verticalChild2.setChildText("SecondChild");
                childItemList.add(verticalChild2);
            }

            VerticalParent verticalParent = new VerticalParent();
            verticalParent.setChildItemList(childItemList);
            verticalParent.setParentNumber(i);
            verticalParent.setParentText("Parent");
            if (i == 0) {
                verticalParent.setInitiallyExpanded(false);
            }
            verticalParentList.add(verticalParent);
        }

        return verticalParentList;
    }
}
