package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boun.swe.wawwe.Models.Comment;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.Commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mert on 07/12/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    Context context;
    RecyclerView recyclerView;
    ArrayList<Comment> data = new ArrayList<Comment>();

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(Comment[] comments) {
        if (data.size() != 0) {
            data.clear();
            notifyDataSetChanged();
        }
        addItems(comments);
    }

    public void addItems(Comment[] comments) {
        int start = data.size();
        data.addAll(Arrays.asList(comments));
        notifyItemRangeChanged(start, comments.length);
        recyclerView.smoothScrollToPosition(getItemCount());

        if(comments.length > 0) {
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = (int) Commons.dpToPx(200);
            recyclerView.setLayoutParams(params);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentHolder(userItemView);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        Comment comment = data.get(position);

        if (holder != null) {
            holder.userName.setText(comment.getUserFullName());
            holder.body.setText(comment.getBody());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(comment.getCreatedAt());
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);

            holder.createdAt.setText(String.format("%02d.%02d", hours, minutes));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CommentHolder extends RecyclerView.ViewHolder {

        ImageView userPic;
        TextView userName;
        TextView createdAt;
        TextView body;

        public CommentHolder(View itemView) {
            super(itemView);

            userPic = (ImageView) itemView.findViewById(R.id.comment_userPic);
            userName = (TextView) itemView.findViewById(R.id.comment_userName);
            createdAt = (TextView) itemView.findViewById(R.id.comment_time);
            body = (TextView) itemView.findViewById(R.id.comment_body);
        }
    }
}
