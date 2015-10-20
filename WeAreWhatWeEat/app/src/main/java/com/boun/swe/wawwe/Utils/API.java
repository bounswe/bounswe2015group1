package com.boun.swe.wawwe.Utils;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.boun.swe.wawwe.App;
import com.boun.swe.wawwe.Models.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static String BASE_URL = "http://ec2-52-89-168-70.us-west-2.compute.amazonaws.com:8080";
    // Designed to have only one request queue for now
    private static RequestQueue mQueue;
    private static API instance;

    public static void init() {
        if (instance == null) {
            instance = new API();
            instance.mQueue = Volley.newRequestQueue(App.getInstance());
        }
    }

    /**
     * This function will request for all users in the server.
     * Uses GET request.
     *
     * @param successListener delivers users as array from server
     * @param failureListener delivers error code if there are any
     */
    public static void getUsers(Response.Listener<User[]> successListener,
                                Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<User[]>(Request.Method.GET, BASE_URL + "/api/user",
                User[].class, null, successListener, failureListener));
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
    public static void addUser(User user, Response.Listener<User> successListener,
                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<User>(Request.Method.POST, BASE_URL + "/api/user",
                user, User.class, null, successListener, failureListener));
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

        private final Gson gson = new Gson();
        private final Class<T> clazz;
        private final Map<String, String> headers;
        private final Response.Listener<T> listener;

        private String postBody;

        public GeneralRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                           Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.clazz = clazz;
            this.headers = headers;
            this.listener = listener;
        }

        public GeneralRequest(int method, String url, T postObject, Class<T> clazz, Map<String, String> headers,
                              Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.clazz = clazz;
            this.headers = headers;
            this.listener = listener;
            this.postBody = gson.toJson(postObject, clazz);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return headers != null ? headers : super.getHeaders();
        }

        @Override
        protected void deliverResponse(T response) {
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
                return Response.success(
                        gson.fromJson(json, clazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }
        }
    }

}
