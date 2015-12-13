package com.boun.swe.wawwe.Fragments;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.FeedAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Circle;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.common.Position;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Direction;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

/**
 * Created by Mert on 09/11/15.
 */
public class Profile extends BaseFragment {

    RecyclerView myFeeds;
    FloatingActionsMenu multipleActions;

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

        ImageView avatar = (ImageView) profileView.findViewById(R.id.profile_image);

        myFeeds = (RecyclerView) profileView.findViewById(R.id.myFeed);
        myFeeds.setItemAnimator(new DefaultItemAnimator());
        myFeeds.setLayoutManager(new LinearLayoutManager(context));
        final FeedAdapter adapter = new FeedAdapter(context);
        myFeeds.setAdapter(adapter);

        multipleActions = (FloatingActionsMenu) profileView.findViewById(R.id.multiple_actions);

        API.getAllMenusforUser(getTag(),
                new Response.Listener<com.boun.swe.wawwe.Models.Menu[]>() {
                    @Override
                    public void onResponse(com.boun.swe.wawwe.Models.Menu[] response) {
                        if (response != null)
                            adapter.addItems(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        API.getAllRecipes(getTag(),
                new Response.Listener<Recipe[]>() {
                    @Override
                    public void onResponse(Recipe[] response) {
                        if (response != null)
                            adapter.addItems(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO could not load...
                    }
                });

        TextSurface userTag = (TextSurface) profileView.findViewById(R.id.userTag);
        User user = App.getUser();
        setUserInfo(user, userTag);

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

        // TODO open this when isRestaurant is added...
//        if (user.isRestaurant())
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
//        else
//            multipleActions.removeButton((FloatingActionButton)
//                    profileView.findViewById(R.id.action_menu_create));

        return profileView;
    }

    @Override
    public void onPause() {
        super.onPause();

        multipleActions.collapse();
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

    private void setUserInfo(User user, TextSurface userTag) {
        int textSize = 25;

        Text PHFullName = Commons.generateText(R.string.title_fullName, textSize, R.color.colorAccent);
        Text PHLocation = Commons.generateText(R.string.title_location, textSize, R.color.colorAccent);
        Text PHDateOfBirth = Commons.generateText(R.string.title_dateOfBirth, textSize, R.color.colorAccent);
        PHFullName.setPosition(new Position(Align.TOP_OF | Align.CENTER_OF, PHLocation));
        PHDateOfBirth.setPosition(new Position(Align.BOTTOM_OF | Align.CENTER_OF, PHLocation));

        userTag.play(new Parallel(
                Delay.duration(1000),
                ShapeReveal.create(PHLocation, 1000, Circle.show(Side.CENTER, Direction.OUT), false),
                ShapeReveal.create(PHFullName, 1000, Circle.show(Side.CENTER, Direction.OUT), false),
                ShapeReveal.create(PHDateOfBirth, 1000, Circle.show(Side.CENTER, Direction.OUT), false)
            ));

        if (user == null) return;
        if (user.getFullName() != null) {
            Text fullName = Commons.generateText(user.getFullName(), textSize, R.color.colorAccent);
            fullName.setPosition(new Position(Align.CENTER_OF, PHFullName));
            userTag.play(new Sequential(
                    Delay.duration(1000),
                    Rotate3D.hideFromSide(PHFullName, 750, Pivot.TOP),
                    ShapeReveal.create(fullName, 1000, Circle.show(Side.CENTER, Direction.OUT), false)
            ));
        }
        if (user.getLocation() != null) {
            Text location = Commons.generateText(user.getLocation(), textSize, R.color.colorAccent);
            location.setPosition(new Position(Align.CENTER_OF, PHLocation));
            userTag.play(new Sequential(
                    Delay.duration(1000),
                    Rotate3D.hideFromSide(PHLocation, 750, Pivot.TOP),
                    ShapeReveal.create(location, 1000, Circle.show(Side.CENTER, Direction.OUT), false)
            ));
        }
        if (user.getDateOfBirth() != null) {
            Text dateOfBirth = Commons.generateText(user.getDateOfBirth(), textSize, R.color.colorAccent);
            dateOfBirth.setPosition(new Position(Align.CENTER_OF, PHDateOfBirth));
            userTag.play(new Sequential(
                    Delay.duration(1000),
                    Rotate3D.hideFromSide(PHDateOfBirth, 750, Pivot.TOP),
                    ShapeReveal.create(dateOfBirth, 1000, Circle.show(Side.CENTER, Direction.OUT), false)
            ));
        }
    }

    public static Profile getFragment(Bundle bundle) {
        Profile profileFragment = new Profile();
        profileFragment.setArguments(bundle);
        return profileFragment;
    }
}
