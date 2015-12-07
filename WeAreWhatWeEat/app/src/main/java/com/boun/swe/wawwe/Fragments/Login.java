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

        final EditText emailEditText = (EditText) loginView.findViewById(R.id.userEmail);
        final EditText passwordEditText = (EditText) loginView.findViewById(R.id.password);

        final CheckBox rememberMe = (CheckBox) loginView.findViewById(R.id.checkBox_rememberMe);
        if(getArguments().getBoolean("isRemembers", false))
            rememberMe.setChecked(true);

        // Button on click events have been set.
        loginView.findViewById(R.id.button_signIn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String email = emailEditText.getText().toString();
                        final String password = passwordEditText.getText().toString();

                        if (!isInputValid(new EditText[]{emailEditText, passwordEditText}))
                            return;

                        final User user = new User(email, password);
                        API.login(getTag(), user,
                                new Response.Listener<AccessToken>() {

                                    @Override
                                    public void onResponse(AccessToken response) {
                                        App.setAccessValues(response);
                                        API.getUserInfo(getTag(),
                                                new Response.Listener<User>() {
                                                    @Override
                                                    public void onResponse(User response) {
                                                        response.setPassword(password);
                                                        App.setUser(response);
                                                        exitLoginFragment();
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                });
                                        if (rememberMe.isChecked())
                                            App.setRememberMe(true);
                                        App.setUser(user);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(App.getInstance(), context.getString(R.string.error_loginError),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

        loginView.findViewById(R.id.button_signUp).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context instanceof MainActivity) {
                            MainActivity main = (MainActivity) context;
                            main.makeFragmentTransaction(Signup.getFragment(new Bundle()));
                        }
                        /*final String email = emailEditText.getText().toString();
                        final String password = passwordEditText.getText().toString();

                        if (!isInputValid(new EditText[]{emailEditText, passwordEditText}))
                            return;

                        User user = new User(email, password);
                        API.addUser(getTag(), user,
                                new Response.Listener<User>() {

                                    @Override
                                    public void onResponse(final User user) {
                                        API.login(getTag(), new User(email, password),
                                                new Response.Listener<AccessToken>() {

                                                    @Override
                                                    public void onResponse(AccessToken response) {
                                                        App.setAccessValues(response);
                                                        if (rememberMe.isChecked()) {
                                                            App.setRememberMe(true);
                                                        }
                                                        App.setUser(user);
                                                        exitLoginFragment();
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(App.getInstance(),
                                                                context.getString(R.string.error_loginAfterSignUpError),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(getTag(), error.toString());
                                        Toast.makeText(App.getInstance(), context.getString(R.string.error_signUpError),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });*/
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

    public static Login getFragment(Bundle bundle) {
        Login loginFragment = new Login();
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    private boolean isInputValid(EditText[] textFields) {
        boolean isValid = true;
        LevelListDrawable drawable;
        for (EditText textField: textFields) {
            String text = textField.getText().toString();

            drawable = (LevelListDrawable)
                    textField.getBackground();
            if (text.isEmpty()) {
                drawable.setLevel(2);
                if (isValid)
                    isValid = false;
            }
            else drawable.setLevel(1);
        }
        return isValid;
    }

    private void exitLoginFragment() {
        if (context instanceof MainActivity) {
            MainActivity main = (MainActivity) context;
//            main.getSupportFragmentManager().beginTransaction()
//                    .remove(Login.this).commit();
//            main.findViewById(R.id.container).invalidate();
            main.removeFragment(this);
            main.makeFragmentTransaction(Feeds.getFragment(new Bundle()));
        }
    }
}
