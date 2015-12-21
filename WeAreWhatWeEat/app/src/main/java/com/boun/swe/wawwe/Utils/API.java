package com.boun.swe.wawwe.Utils;

import android.util.Log;

import com.boun.swe.wawwe.Models.AutoComplete;
import com.boun.swe.wawwe.Models.Nutrition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

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
import com.boun.swe.wawwe.Models.Comment;
import com.boun.swe.wawwe.Models.Ingredient;
import com.boun.swe.wawwe.Models.Menu;
import com.boun.swe.wawwe.Models.Rate;
import com.boun.swe.wawwe.Models.Recipe;
import com.boun.swe.wawwe.Models.User;
import com.boun.swe.wawwe.R;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    private static boolean underDev = true;
    private static boolean isTest = false;

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
                Object requestTag = request.getTag();
                return requestTag != null && requestTag.equals(tag);
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

    public static void getUserInfo(String tag, int userId,
                                   Response.Listener<User> successListener,
                                   Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/user/%s",
                userId), User.class, successListener, failureListener).setTag(tag));
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
                return f.getName().equals("id");
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        })
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src,
                                                 Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        return new JsonPrimitive(src.getTime());
                    }
                }).create().toJson(user, User.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/user", User.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));

    }

    public static void updateUser(String tag, User user, Response.Listener<User> successListener,
                               Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().equals("id");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src,
                                                 Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        return new JsonPrimitive(src.getTime());
                    }
                }).create().toJson(user, User.class);
        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/user/update", User.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));
    }
    //Not Tested and No Api yet
    public static void searchRecipe(String tag, String srchTxt, Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        //TODO build the post body or make the method GET with url /recipe/search/{srchTxt}
        if (isTest) {
            String file1 = "test_recipe1.json";
            String file2 = "test_recipe2.json";
            String postBody1 = loadJSONFromAsset(file1);
            String postBody2 = loadJSONFromAsset(file2);

            Gson gson1 = new Gson();
            Gson gson2 = new Gson();
            JsonReader reader1 = new JsonReader(new StringReader(postBody1));
            JsonReader reader2 = new JsonReader(new StringReader(postBody2));
            reader1.setLenient(true);
            reader2.setLenient(true);

            Recipe testRcp1 = gson1.fromJson(postBody1, Recipe.class);
            Recipe testRcp2 = gson2.fromJson(postBody2, Recipe.class);

            Recipe[] searchResults = {testRcp1, testRcp2};
            successListener.onResponse(searchResults);

        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET,
                    BASE_URL + String.format("/search/recipe/%s", srchTxt),
                    Recipe[].class, successListener, failureListener).setTag(tag));

        }
    }

    public static  void searchMenus(String tag, String srchTxt, Response.Listener<Menu[]> successListener,
                                    Response.ErrorListener failureListener) {
        if(isTest){

        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET,
                    BASE_URL + String.format("/search/menu/%s", srchTxt),
                    Menu[].class, successListener, failureListener).setTag(tag));
        }
    }

    //Not Tested and No Api yet
    public static void editRecipe(String tag, Recipe recipe, int recipeId, Response.Listener<Recipe> successListener,
                                  Response.ErrorListener failureListener) {

        if (isTest) {
            successListener.onResponse(recipe);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("id") || f.getName().equals("userId");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(recipe, Recipe.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + String.format("/recipe/edit/%d", recipeId), Recipe.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }

    //Not Tested and No Api yet
    public static void getRecipeTags(String tag, int recipeId, Response.Listener<String[]> successListener,
                                  Response.ErrorListener failureListener) {
        if (isTest) successListener.onResponse(new String[] { "Egg", "Salty", "Breakfast" });
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/tags/%d", recipeId),
                String[].class, successListener, failureListener).setTag(tag));
    }

    public static void getRecipe(String tag, int recipeID, Response.Listener<Recipe> successListener,
                                      Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/recipe/view/%d",
                recipeID), Recipe.class, successListener, failureListener).setTag(tag));
    }

    public static void createRecipe(String tag, Recipe recipe, Response.Listener<Recipe> successListener,
                                     Response.ErrorListener failureListener) {
        if(isTest){
            successListener.onResponse(recipe);
        }
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
        if(isTest){
            String file1 = "test_recipe1.json";
            String file2 = "test_recipe2.json";
            String file3 = "test_recipe3.json";

            String postBody1 = loadJSONFromAsset(file1);
            String postBody2 = loadJSONFromAsset(file2);
            String postBody3 = loadJSONFromAsset(file3);

            Gson gson1 = new Gson();
            Gson gson2 = new Gson();
            Gson gson3 = new Gson();
            JsonReader reader1 = new JsonReader(new StringReader(postBody1));
            JsonReader reader2 = new JsonReader(new StringReader(postBody2));
            JsonReader reader3 = new JsonReader(new StringReader(postBody3));

            reader1.setLenient(true);
            reader2.setLenient(true);
            reader3.setLenient(true);

            Recipe testRcp1 = gson1.fromJson(postBody1, Recipe.class);
            Recipe testRcp2 = gson2.fromJson(postBody2, Recipe.class);
            Recipe testRcp3 = gson3.fromJson(postBody3, Recipe.class);

            testRcp1.setUserId(App.getUserId());
            testRcp2.setUserId(App.getUserId());
            testRcp3.setUserId(App.getUserId());

            Recipe[] userRecipes = {testRcp1, testRcp2, testRcp3};
            successListener.onResponse(userRecipes);
        }
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/recipe/user/%s",
                App.getUserId()), Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void getAllRecipes(String tag, Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + "/recipe/all",
                Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void getRecommendedRecipes(String tag, int rId,
                                             Response.Listener<Recipe[]> successListener,
                                             Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/recommend/%d", rId),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void searchIngredients(String tag, String query, Response.Listener<AutoComplete[]> successListener,
                                         Response.ErrorListener failureListener) {
        GeneralRequest<AutoComplete[]> request = new GeneralRequest<AutoComplete[]>(
                Request.Method.GET,
                BASE_URL + String.format("/ingredient/search/%s", query),
                AutoComplete[].class, successListener, failureListener) {
            @Override
            protected Response<AutoComplete[]> parseNetworkResponse(NetworkResponse response) {
                String json = new String(response.data);
                Log.v("Response", json);
                AutoComplete[] responseModels = null;
                try {
                    JSONObject responseObject = new JSONObject(json);
                    JSONArray hits = responseObject.getJSONArray("hits");
                    responseModels = new AutoComplete[hits.length()];
                    for (int i = 0;i < hits.length();i++) {
                        JSONObject hit = hits.getJSONObject(i);
                        JSONObject fields = hit.getJSONObject("fields");

                        AutoComplete responseModel = new AutoComplete();
                        responseModel.setId(fields.optString("item_id", ""));
                        responseModel.setText(fields.optString("item_name", ""));

                        responseModels[i] = responseModel;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(responseModels,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        mQueue.add(request.setTag(tag));
    }

    public static void autocompleteIngredients(String tag, String query,
                                               Response.Listener<AutoComplete[]> successListener,
                                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/ingredient/autocomplete/%s", query),
                AutoComplete[].class, successListener, failureListener).setTag(tag));
    }

    public static void getIngredientItem(String tag, String itemId,
                                         Response.Listener<Ingredient> successListener,
                                         Response.ErrorListener failureListener) {
        GeneralRequest<Ingredient> request = new GeneralRequest<Ingredient>(Request.Method.GET,
                BASE_URL + String.format("/ingredient/item/%s", itemId),
                Ingredient.class, successListener, failureListener) {
            @Override
            protected Response<Ingredient> parseNetworkResponse(NetworkResponse response) {
                String json = new String(response.data);
                Log.v("Response", json);
                Ingredient responseModel = new Ingredient();
                try {
                    Nutrition nutrition = new Nutrition();
                    JSONObject responseObject = new JSONObject(json);

                    responseModel.setIngredientId(responseObject.optString("item_id", ""));
                    responseModel.setName(responseObject.optString("item_name", ""));

                    nutrition.setCalories((float) responseObject.getDouble("nf_calories"));
                    nutrition.setCarbohydrate((float) responseObject.getDouble("nf_total_carbohydrate"));
                    nutrition.setFats((float) responseObject.getDouble("nf_total_fat"));
                    nutrition.setProteins((float) responseObject.getDouble("nf_protein"));
                    nutrition.setSodium((float) responseObject.getDouble("nf_sodium"));
                    nutrition.setFiber((float) responseObject.getDouble("nf_dietary_fiber"));
                    nutrition.setCholesterol((float) responseObject.getDouble("nf_cholesterol"));
                    nutrition.setSugars((float) responseObject.getDouble("nf_sugars"));
                    nutrition.setIron((float) responseObject.getDouble("nf_iron_dv"));

                    responseModel.setNutritions(nutrition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return Response.success(responseModel,
                        HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        mQueue.add(request.setTag(tag));
    }

    public static void getUserMenus(String tag, Response.Listener<Menu[]> successListener,
                                    Response.ErrorListener failureListener) {
        if(isTest){
            String file1 = "test_menu1.json";

            String postBody1 = loadJSONFromAsset(file1);

            Gson gson1 = new Gson();

            JsonReader reader1 = new JsonReader(new StringReader(postBody1));

            reader1.setLenient(true);

            Menu testMenu = gson1.fromJson(postBody1, Menu.class);
            Menu testMenu1 = gson1.fromJson(postBody1, Menu.class);
            Menu testMenu2 = gson1.fromJson(postBody1, Menu.class);

            successListener.onResponse(new Menu[] {testMenu, testMenu1, testMenu2});
        } else mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL +"/menu", Menu[].class,
                successListener, failureListener).setTag(tag));
    }

    public static void getMenu(String tag, int menuID, Response.Listener<Menu> successListener,
                                      Response.ErrorListener failureListener) {
        if(isTest){
            String file1 = "test_menu1.json";

            String postBody1 = loadJSONFromAsset(file1);

            Gson gson1 = new Gson();

            JsonReader reader1 = new JsonReader(new StringReader(postBody1));

            reader1.setLenient(true);

            Menu testMenu = gson1.fromJson(postBody1, Menu.class);

            testMenu.setUserId(App.getUserId());
            testMenu.setId(menuID);

            successListener.onResponse(testMenu);
        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/menu/%d",
                    menuID), Menu.class, successListener, failureListener).setTag(tag));
        }
    }

    public static void getRecipesforMenu(String tag, int menuID, Response.Listener<Recipe[]> successListener,
                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/menu/%d/recipes",
                menuID), Recipe[].class, successListener, failureListener).setTag(tag));
    }

    public static void createMenu(String tag, Menu menu, Response.Listener<Menu> successListener,
                             Response.ErrorListener failureListener) {
        if (isTest) {
            successListener.onResponse(menu);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("id");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(menu, Menu.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + "/menu", Menu.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }

    public static void getAllComments(String tag, String type, int parentID, Response.Listener<Comment[]> successListener,
                                      Response.ErrorListener failureListener) {
        if(isTest){
            Comment c1 = new Comment("Onur Guler", type, parentID, Commons.getString(R.string.test_comment_recipe1));
            Comment c2 = new Comment("Cagla Balcik", type, parentID, Commons.getString(R.string.test_comment_recipe2));
            Comment c3 = new Comment("Mert Tiftikci", type, parentID, Commons.getString(R.string.test_comment_recipe3));
            Comment c4 = new Comment("Gorkem Onder", type, parentID, Commons.getString(R.string.test_comment_recipe4));

            c1.setCreatedAt(new Date(1449020159));
            c2.setCreatedAt(new Date(1440517159));
            c3.setCreatedAt(new Date(1409507059));
            c4.setCreatedAt(new Date(1440505191));
            successListener.onResponse(new Comment[] {c1, c2, c3, c4});
        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/comment/%s/%d",
                    type, parentID), Comment[].class, successListener, failureListener).setTag(tag));
        }
    }

    public static void getAllCommentsForUser(String tag, String type, int parentID, int userID,
                                             Response.Listener<Comment[]> successListener,
                                             Response.ErrorListener failureListener) {
        if(isTest){
            Comment c1 = new Comment(App.getUser().getFullName(), type, App.getUserId(), Commons.getString(R.string.test_comment_recipe1));
        }
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/comment/%s/%d/%d",
                type, parentID, userID), Comment[].class, successListener, failureListener).setTag(tag));
    }

    public static void comment(String tag, Comment comment,
                               Response.Listener<Comment> successListener,
                               Response.ErrorListener failureListener) {
        if(isTest) {
            comment.setCreatedAt(new Date(1449505191));
            successListener.onResponse(comment);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return (f.getName().equals("id") ||
                            f.getName().equals("userFullName") ||
                            f.getName().equals("userId") ||
                            f.getName().equals("createdAt"));
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(comment, Comment.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + "/comment", Comment.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }

    public static void deleteComment(String tag, Comment comment, Response.Listener<Comment> successListener,
                                     Response.ErrorListener failureListener) {
        if(isTest) {
            successListener.onResponse(comment);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("userId");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(comment, Comment.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + "/comment/delete", Comment.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }

    public static void rate(String tag, Rate rate, Response.Listener<Rate> successListener,
                            Response.ErrorListener failureListener) {
        if(isTest){
            successListener.onResponse(rate);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return (f.getName().equals("id") || f.getName().equals("userId"));
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(rate, Rate.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + "/rate", Rate.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }

    public static void getAverageRating(String tag, String type, int parentId, Response.Listener<Rate> successListener,
                                         Response.ErrorListener failureListener) {
        if(isTest){
            float min = 0.0f; float max = 5.0f;
            Random rand = new Random();
            float avg = rand.nextFloat()*(max-min);
            Rate r1 = new Rate(type, parentId, avg);
            successListener.onResponse(r1);
        } else{
            mQueue.add(new GeneralRequest<>(Request.Method.GET,
                    BASE_URL + (String.format("/rate/%s/%d", type, parentId)),
                    Rate.class, successListener, failureListener).setTag(tag));
        }
    }

    public static void getRatingByUser(String tag, String type, int parentId, int userId,
                                        Response.Listener<Rate> successListener,
                                        Response.ErrorListener failureListener) {
        if(isTest){
            float min = 0.0f; float max = 5.0f;
            Random rand = new Random();
            float avg = rand.nextFloat()*(max-min);
            Rate r1 = new Rate(type, parentId, avg);
            successListener.onResponse(r1);
        }
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + (String.format("/rate/%s/%d/%d", type, parentId, userId)),
                Rate.class, successListener, failureListener).setTag(tag));
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

    public static String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = App.getInstance().getApplicationContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
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

        private Gson gson;
        private Class<T> responseClazz;
        private Map<String, String> headers = new HashMap<>();
        private Response.Listener<T> listener;

        private String postBody;

        public GeneralRequest(int method, String url, Class<T> responseClazz,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.responseClazz = responseClazz;
            this.listener = listener;

            gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json,
                                        Type typeOfT,
                                        JsonDeserializationContext context) throws JsonParseException {
                    // Date comes as nano second...
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            }).create();

            Log.v("Request", url + ", method: " +
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
