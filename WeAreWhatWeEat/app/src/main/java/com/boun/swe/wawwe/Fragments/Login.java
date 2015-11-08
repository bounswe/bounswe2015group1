package com.boun.swe.wawwe.Fragments;

import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.AccessToken;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

/**
 * Created by Mert on 15/10/15.
 */
public class Login extends BaseFragment {

    public Login() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.layout_fragment_login,
                container, false);

        final EditText userNameTextView = (EditText) loginView.findViewById(R.id.userEmail);
        final EditText passwordTextView = (EditText) loginView.findViewById(R.id.password);

        final CheckBox rememberMe = (CheckBox) loginView.findViewById(R.id.checkBox_rememberMe);

        // Button on click events have been set.
        loginView.findViewById(R.id.button_signIn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(App.getInstance(), "=On Construction=",
                                Toast.LENGTH_SHORT).show();
                        if (true) return;

                        String userName = userNameTextView.getText().toString();
                        String passwords = passwordTextView.getText().toString();

                        if (userName.equals("") || passwords.equals("")) {
                            Toast.makeText(App.getInstance(), "Some fields are missing",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final User user = new User(userName, passwords);
                        API.login(getTag(), user,
                                new Response.Listener<AccessToken>() {

                                    @Override
                                    public void onResponse(AccessToken response) {
                                        if (rememberMe.isChecked()) {
                                            App.setRememberMe(true);
                                            App.setUser(user);
                                        }
                                        if (context instanceof MainActivity) {
                                            MainActivity main = (MainActivity) context;
                                            main.makeFragmentTransaction(RecipeCreator.getFragment());
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(App.getInstance(), "Could not login!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

        loginView.findViewById(R.id.button_signUp).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = userNameTextView.getText().toString();
                        String passwords = passwordTextView.getText().toString();

                        boolean userNameEmpty = userName.equals("");
                        boolean passwordEmpty = passwords.equals("");

                        if (userNameEmpty || passwordEmpty) {
                            LevelListDrawable drawable = (LevelListDrawable)
                                    userNameTextView.getBackground();
                            if (userNameEmpty) {
                                drawable.setLevel(2);
                                return;
                            }
                            else drawable.setLevel(1);

                            drawable = (LevelListDrawable) passwordTextView.
                                    getBackground();
                            if (passwordEmpty) {
                                drawable.setLevel(2);
                                return;
                            }
                            else drawable.setLevel(1);
                        }

                        API.addUser(getTag(), new User(userName, passwords),
                                new Response.Listener<User>() {

                                    @Override
                                    public void onResponse(User response) {
                                        // User added...
                                        if (rememberMe.isChecked()) {
                                            App.setRememberMe(true);
                                            App.setUser(response);
                                        }

                                        if (context instanceof MainActivity) {
                                            MainActivity main = (MainActivity) context;
                                            main.getSupportFragmentManager().beginTransaction()
                                                    .remove(Login.this).commit();
                                            main.makeFragmentTransaction(RecipeCreator.getFragment(), false);
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(getTag(), error.toString());
                                        Toast.makeText(App.getInstance(), "Could not sign up!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

        return loginView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
            main.getSupportActionBar().show();
        }
    }

    public static Login getFragment() {
        Login loginFragment = new Login();
        loginFragment.setArguments(new Bundle());
        return loginFragment;
    }
}
