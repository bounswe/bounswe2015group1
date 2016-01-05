package com.boun.swe.wawwe.Adapters;

import android.content.Context;
import android.support.v7.util.SortedList;
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
    SortedList<Comment> data;

    public CommentAdapter(Context context) {
        this.context = context;
        data = new SortedList<>(Comment.class, new SortedList.Callback<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return o1.compareTo(o2);
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Comment oldItem, Comment newItem) {
                return oldItem.getBody().equals(newItem.getBody());
            }

            @Override
            public boolean areItemsTheSame(Comment item1, Comment item2) {
                return item1.getId() == item2.getId();
            }
        });
    }

    public void setData(Comment[] comments) {
        if (data.size() != 0)
            data.clear();
        addItems(comments);
    }

    public void addItems(Comment[] comments) {
        if(comments.length > 0) {
            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = (int) Commons.dpToPx(200);
            recyclerView.setLayoutParams(params);
        }

        data.beginBatchedUpdates();
        for (Comment item : comments) {
            data.add(item);
        }
        data.endBatchedUpdates();
        recyclerView.smoothScrollToPosition(getItemCount());
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

            holder.createdAt.setText(Commons.prettifyDate(comment
                    .getCreatedAt())[0]);
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
