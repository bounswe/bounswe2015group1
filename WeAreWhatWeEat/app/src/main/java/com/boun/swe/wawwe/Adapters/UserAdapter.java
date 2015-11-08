package com.boun.swe.wawwe.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mert on 17/10/15.
 *
 * Classic recycler view adapter implementation
 * with generic methods are included.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    ArrayList<User> data = new ArrayList<>();

    public void setData(User[] users) {
        if (data.size() != 0) {
            data.clear();
            notifyDataSetChanged();
        }
        addItems(users);
    }

    public void addItems(User[] users) {
        int start = data.size();
        data.addAll(Arrays.asList(users));
        notifyItemRangeChanged(start, users.length);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(userItemView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = data.get(position);

        holder.userName.setText(user.getFullName());
        holder.password.setText(user.getPassword());
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public TextView password;

        public UserViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.userEmail);
            password = (TextView) itemView.findViewById(R.id.password);
        }
    }
}
