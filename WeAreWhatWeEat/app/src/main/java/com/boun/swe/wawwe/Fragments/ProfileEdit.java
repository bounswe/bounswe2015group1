package com.boun.swe.wawwe.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Mert on 09/11/15.
 */
public class ProfileEdit extends BaseFragment {

    EditText email;
    EditText password;
    EditText fullName;
    EditText location;
    EditText dateOfBirth;

    public ProfileEdit() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.layout_fragment_profile_edit,
                container, false);

        User user = App.getUser();

        ImageView avatar = (ImageView) profileView.findViewById(R.id.profileEdit_image);
        email = (EditText) profileView.findViewById(R.id.profileEdit_email);
        password = (EditText) profileView.findViewById(R.id.profileEdit_password);
        fullName = (EditText) profileView.findViewById(R.id.profileEdit_fullName);
        location = (EditText) profileView.findViewById(R.id.profileEdit_location);
        dateOfBirth = (EditText) profileView.findViewById(R.id.profileEdit_dateOfBirth);

        setUserInfo(user);

        return profileView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            main.getSupportActionBar()
                .setTitle(R.string.title_menu_profileEdit);
//            main.setDisplayHomeAsUp();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
        menu.findItem(R.id.menu_profile_editDone).setIcon(R.mipmap.ic_done_black_24dp);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            menu.findItem(R.id.menu_profile_add).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile_editDone:
                if (fullName.isEnabled()) {
                    final User user = App.getUser();

                    String mail = email.getText().toString();
                    final String pass = password.getText().toString();
                    String name = fullName.getText().toString();
                    String loc = location.getText().toString();
                    String date = dateOfBirth.getText().toString();

                    if (!mail.isEmpty())
                        user.setEmail(mail);
                    if (!pass.isEmpty())
                        user.setPassword(pass);
                    if (!name.isEmpty())
                        user.setFullName(name);
                    if (!loc.isEmpty())
                        user.setLocation(loc);
                    if (!date.isEmpty())
                        user.setDateOfBirth(Date.valueOf(date));

                    API.updateUser(getTag(), user,
                            new Response.Listener<User>() {
                                @Override
                                public void onResponse(User response) {
                                    response.setPassword(pass);
                                    App.setUser(response);
                                    if (context instanceof MainActivity) {
                                        MainActivity main = (MainActivity) context;
                                        main.onBackPressed();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, context.getString(R.string.error_updateUser),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUserInfo(User user) {
        if (user == null) return;

        if (user.getEmail() != null)
            email.setText(user.getEmail());
        if (user.getPassword() != null)
            password.setText(user.getPassword());
        if (user.getFullName() != null)
            fullName.setText(user.getFullName());
        if (user.getLocation() != null)
            location.setText(user.getLocation());
        if (user.getDateOfBirth() != null)
            dateOfBirth.setText(new SimpleDateFormat("dd MM yyyy")
                    .format(user.getDateOfBirth()));
    }

    public static ProfileEdit getFragment(Bundle bundle) {
        ProfileEdit profileEditFragment = new ProfileEdit();
        profileEditFragment.setArguments(bundle);
        return profileEditFragment;
    }
}
