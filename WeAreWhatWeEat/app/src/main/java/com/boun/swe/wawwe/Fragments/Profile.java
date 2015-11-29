package com.boun.swe.wawwe.Fragments;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 09/11/15.
 */
public class Profile extends BaseFragment {

    ImageView avatar;
    TextView fullName;
    TextView location;
    TextView dateOfBirth;

    RecyclerView myFeeds;

    public Profile() { }

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
        myFeeds.setItemAnimator(new DefaultItemAnimator());
        myFeeds.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        myFeeds.setAdapter(adapter);

        API.getAllRecipes(Profile.class.getSimpleName(),
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
                });

        User user = App.getUser();
        setUserInfo(user);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View addButton = profileView.findViewById(R.id.profile_button_feed);
            addButton.setOutlineProvider(new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int diameter = getResources().getDimensionPixelSize(R.dimen.diameter);
                    outline.setOval(0, 0, diameter, diameter);
                }
            });
            addButton.setClipToOutline(true);

            addButton.findViewById(R.id.profile_button_feed)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (context instanceof MainActivity) {
                                MainActivity main = (MainActivity) context;
                                main.makeFragmentTransaction(RecipeCreator.getFragment(new Bundle()));
                            }
                        }
                    });
        }

        return profileView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity)
            ((MainActivity) context).getSupportActionBar()
                    .setTitle(R.string.title_menu_profile);
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

                    main.makeFragmentTransaction(RecipeCreator.getFragment(new Bundle()));
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
}
