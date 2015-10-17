package com.boun.swe.wawwe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.UserAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.MainActivity;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;

import org.json.JSONObject;

/**
 * Created by Mert on 15/10/15.
 */
public class Login extends Fragment {

    MainActivity main;
    RecyclerView usersList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        main = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.layout_fragment_login,
                container, false);

        final EditText userNameTextView = (EditText) loginView.findViewById(R.id.userName);
        final EditText passwordTextView = (EditText) loginView.findViewById(R.id.password);

        // Configured list for displaying users.
        usersList = (RecyclerView) loginView.findViewById(R.id.users);
        usersList.setLayoutManager(new LinearLayoutManager(main));
        usersList.setItemAnimator(new DefaultItemAnimator());
        final UserAdapter adapter = new UserAdapter();
        usersList.setAdapter(adapter);

        // Button on click events have been set.
        loginView.findViewById(R.id.button_signUp).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = userNameTextView.getText().toString();
                        String passwords = passwordTextView.getText().toString();

                        if (userName.equals("") || passwords.equals("")) {
                            Toast.makeText(App.getInstance(), "Some fields are missing",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        API.addUser(new User(userName, passwords),
                                new Response.Listener<User>() {

                                    @Override
                                    public void onResponse(User response) {
                                        adapter.addItems(new User[]{response});
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(App.getInstance(), "User cannot be added",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
        );
        loginView.findViewById(R.id.button_loadUsers).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        API.getUsers(new Response.Listener<User[]>() {
                                         @Override
                                         public void onResponse(User[] response) {
                                             adapter.setData(response);
                                         }
                                     },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(App.getInstance(), "Users cannot be received",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
        );

        return loginView;
    }
}
