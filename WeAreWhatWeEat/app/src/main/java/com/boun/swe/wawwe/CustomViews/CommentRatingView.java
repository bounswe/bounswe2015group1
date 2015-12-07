package com.boun.swe.wawwe.CustomViews;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boun.swe.wawwe.Adapters.CommentAdapter;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.Fragments.BaseFragment;
import com.boun.swe.wawwe.Models.Comment;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Rate;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.boun.swe.wawwe.Utils.API;
import com.boun.swe.wawwe.Utils.Commons;

/**
 * Created by Mert on 07/12/15.
 */
public class CommentRatingView {

    public static class Builder {

        private Builder builder;

        private Context context;
        private BaseFragment fragment;

        private String type;
        private int parentId;

        public Builder(Context context, BaseFragment fragment) {
            this.context = context;
            this.builder = this;
            this.fragment = fragment;
        }

        public Builder setParent(Object parent) {
            if (parent instanceof User) {
                type = "user";
                parentId = ((User) parent).getId();
            }
            else if (parent instanceof Recipe) {
                type = "recipe";
                parentId = ((Recipe) parent).getId();
            }
            else if (parent instanceof Menu) {
                type = "menu";
                parentId = ((Menu) parent).getId();
            }
            return builder;
        }

        public View create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View commentRatingView = inflater.inflate(R.layout.layout_view_comment_rating, null, false);

            // Prepare RecyclerView
            RecyclerView comments = (RecyclerView) commentRatingView.findViewById(R.id.comments);
            comments.setLayoutManager(new LinearLayoutManager(context));
            comments.setItemAnimator(new DefaultItemAnimator());
            final CommentAdapter adapter = new CommentAdapter(context);
            comments.setAdapter(adapter);

            // Populate RecyclerView
            API.getAllComments(fragment.getTag(), type, parentId,
                    new Response.Listener<Comment[]>() {
                        @Override
                        public void onResponse(Comment[] response) {
                            adapter.setData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            // Make a Comment
            final EditText commentText = (EditText) commentRatingView.findViewById(R.id.input_comment);
            commentRatingView.findViewById(R.id.button_makeComment)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Comment comment = new Comment(App.getUser().getFullName(),
                                    type, parentId, commentText.getText().toString());
                            API.comment(fragment.getTag(), comment,
                                    new Response.Listener<Comment>() {
                                        @Override
                                        public void onResponse(Comment response) {
                                            adapter.addItems(new Comment[]{response});
                                            commentText.setText("");
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(context, Commons.getString(R.string.error_makeComment),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });

            final RatingBar ratingBarAverage = (RatingBar) commentRatingView.findViewById(R.id.ratingBarAverage);
            API.getAverageRating(fragment.getTag(), type, parentId,
                    new Response.Listener<Rate>() {
                        @Override
                        public void onResponse(Rate response) {
                            ratingBarAverage.setRating(response.getRating());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            final RatingBar ratingBar = (RatingBar) commentRatingView.findViewById(R.id.ratingBar);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    API.rate(fragment.getTag(), new Rate(type, parentId, rating),
                            new Response.Listener<Rate>() {
                                @Override
                                public void onResponse(Rate response) {
                                    API.getAverageRating(fragment.getTag(), type, parentId,
                                            new Response.Listener<Rate>() {
                                                @Override
                                                public void onResponse(Rate response) {
                                                    ratingBarAverage.setRating(response.getRating());
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, Commons.getString(R.string.error_doRate),
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

            return commentRatingView;
        }
    }
}
