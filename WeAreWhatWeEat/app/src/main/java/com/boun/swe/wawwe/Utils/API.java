package com.boun.swe.wawwe.Utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.Models.AccessToken;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mert on 16/10/15.
 *
 * This class is the bridge between server and application
 */
public class API {

    // Our base url, every extension to this will represent its own request
    private static String BASE_URL = "http://ec2-52-89-168-70.us-west-2.compute.amazonaws.com:8080/api";
    // Designed to have only one request queue for now
    private static RequestQueue mQueue;
    private static API instance;

    private static String UUID;

    public static void init() {
        if (instance == null) {
            instance = new API();
            mQueue = Volley.newRequestQueue(App.getInstance());
        }
    }

    public static void setUUID(String UUID) {
        instance.UUID = UUID;
    }

    public static void cancelRequestByTag(final String tag) {
        mQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request.getTag().equals(tag);
            }
        });
    }

    /**
     * This function will request for all users in the server.
     * Uses GET request.
     *
     * @param successListener delivers users as array from server
     * @param failureListener delivers error code if there are any
     */
    public static void getUsers(String tag, Response.Listener<User[]> successListener,
                                Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + "/user",
                User[].class, successListener, failureListener).setTag(tag));
    }

    public static void getUserInfo(String tag, Response.Listener<User> successListener,
                                Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/user/%s",
                App.getUserId()), User.class, successListener, failureListener).setTag(tag));
    }

    /**
     * This function will send new user to server to and if successful
     * will receive JSON object for this user which will be translated
     * to {@link User} object and return to the caller.
     * Uses POST request.
     *
     * @param user User object that will be added to server
     * @param successListener delivers users as array from server
     * @param failureListener delivers error code if there are any
     */
    public static void addUser(String tag, User user, Response.Listener<User> successListener,
                                Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return !(f.getName().equals("email") || f.getName().equals("password"));
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(user, User.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/user", User.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }

    public static void updateUser(String tag, User user, Response.Listener<User> successListener,
                               Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("id");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(user, User.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/user/update", User.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }
    //Not Tested and No Api yet
    public static void searchRecipe(String tag, String srchTxt, Response.Listener<Recipe> successListener,
                                    Response.ErrorListener failureListener) {
        //TODO build the post body or make the method GET with url /recipe/search/{srchTxt}
        //String postBody = null;
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/search/%s",srchTxt),
                Recipe.class, successListener, failureListener).setTag(tag));
    }
    //Not Tested and No Api yet
    public static void editRecipe(String tag, Recipe recipe, int recipeId, Response.Listener<Recipe> successListener,
                                  Response.ErrorListener failureListener){
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("id") || f.getName().equals("userId");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) { return false; }
        }).create().toJson(recipe, Recipe.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + String.format("/recipe/edit/%d",recipeId), Recipe.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }

    public static void createRecipe(String tag, Recipe recipe, Response.Listener<Recipe> successListener,
                                     Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return (f.getDeclaringClass().equals(Recipe.class) && f.getName().equals("id"))
                        || f.getName().equals("userId");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(recipe, Recipe.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/recipe", Recipe.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }

    public static void getUserRecipes(String tag, Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/recipe/user/%s",
                App.getUserId()), Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void getAllRecipes(String tag, Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + "/recipe/all",
                Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void login(String tag, User user, Response.Listener<AccessToken> successListener,
                               Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return !(f.getName().equals("email") || f.getName().equals("password"));
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create().toJson(user, User.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/session/login", AccessToken.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }

    public static void logout(String tag, Response.Listener<User> successListener,
                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/session/logout", User.class,
                successListener, failureListener).setTag(tag));
    }

    /**
     * This is a generic request class which have designed from volley and
     * GSON components. This basically makes GET and POST requests and transforms
     * response JSONs into application model.
     *
     * @param <T> Model of requested type
     */
    private static class GeneralRequest<T> extends Request<T> {

        private static final String PROTOCOL_CHARSET = "utf-8";
        private static final String PROTOCOL_CONTENT_TYPE =
                String.format("application/json; charset=%s", PROTOCOL_CHARSET);

        private Gson gson = new Gson();
        private Class<T> responseClazz;
        private Map<String, String> headers = new HashMap<>();
        private Response.Listener<T> listener;

        private String postBody;

        public GeneralRequest(int method, String url, Class<T> responseClazz,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.responseClazz = responseClazz;
            this.listener = listener;

            Log.v((String) getTag(), url + ", method: " +
                    (method == Request.Method.GET ? "GET" : "POST"));
        }

        public GeneralRequest<T> setPostBodyInJSONForm(Object postObject,
                                                       Class<? extends Object> postClass) {
            this.postBody = gson.toJson(postObject, postClass);
            Log.v("Request", postBody);
            return this;
        }

        public GeneralRequest<T> setPostBodyInJSONForm(String postBody) {
            this.postBody = postBody;
            Log.v("Request", postBody);
            return this;
        }

        public GeneralRequest<T> setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> headers = this.headers != null ?
                    this.headers : super.getHeaders();
            if (UUID != null)
                headers.put("Authorization", "Bearer " + UUID);
            return headers;
        }

        @Override
        protected void deliverResponse(T response) {
            Log.v("Request", new Gson().toJson(response));
            if (listener != null)
                listener.onResponse(response);
        }


        @Override
        public byte[] getBody() throws AuthFailureError {
            try {
                return postBody == null ? null : postBody.getBytes(PROTOCOL_CHARSET);
            } catch (UnsupportedEncodingException uee) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                        postBody, PROTOCOL_CHARSET);
                return null;
            }
        }

        @Override
        public String getBodyContentType() {
            return postBody == null ? null : PROTOCOL_CONTENT_TYPE;
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {

                String json = new String(
                        response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                Log.v("Response", json);
                return Response.success(
                        responseClazz.equals(Void.class) ? null :
                                gson.fromJson(json, responseClazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }
        }
    }

}
