package com.boun.swe.wawwe.Utils;

import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.toolbox.ImageRequest;
import com.boun.swe.wawwe.Models.Allergy;
import com.boun.swe.wawwe.Models.AutoComplete;
import com.boun.swe.wawwe.Models.Nutrition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
import java.util.Iterator;
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
    /**
     * Sets UUID.
     *
     * @param UUID bearer Id to be used for authorization.
     */
    public static void setUUID(String UUID) {
        instance.UUID = UUID;
    }
    /**
     * Cancels all ongoing requests in the queue
     *
     * @param tag tag to be matched.
     */
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
    /**
     * Retrieves {@link User user} via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link User user} object.
     * @param failureListener will be called when call fails.
     */
    public static void getUserInfo(String tag,
                                   Response.Listener<User> successListener,
                                   Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/user/%s", App.getUserId()),
                User.class, successListener, failureListener).setTag(tag));
    }
    /**
     * Retrieves {@link User user} for given id via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link User user} object.
     * @param failureListener will be called when call fails.
     */
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
                })
                .create()
                .toJson(user, User.class);

        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/user", User.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));

    }
    /**
     * Edits {@link User user} via POST request to server.
     * Reformats {@link Recipe#createdAt createdAt} field in post body.
     *
     * @param tag used as request tag.
     * @param user {@link User user} with updated values.
     * @param successListener will be called with same {@link User user} object.
     * @param failureListener will be called when call fails.
     */
    public static void updateUser(String tag, User user,
                                  Response.Listener<User> successListener,
                                  Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder()
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
    /**
     * Returns {@link Recipe recipes} for query via GET request to server.
     * Adds advanced search parameters to request.
     *
     * @param tag used as request tag.
     * @param query text to be searched.
     * @param successListener will be called with {@link Recipe recipes} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void advanceSearchRecipe(String tag, String query,
                                           Response.Listener<Recipe[]> successListener,
                                           Response.ErrorListener failureListener) {
        if (isTest) {
            String postBody1 = Commons.loadJSONFromAsset("test_recipe1.json");
            String postBody2 = Commons.loadJSONFromAsset("test_recipe2.json");

            Gson gson = new Gson();

            Recipe testRcp1 = gson.fromJson(postBody1, Recipe.class);
            Recipe testRcp2 = gson.fromJson(postBody2, Recipe.class);

            Recipe[] searchResults = { testRcp1, testRcp2 };
            successListener.onResponse(searchResults);
        } else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                Commons.getUrlForGet(BASE_URL + String.format("/search/advancedSearch/recipe/%s", query),
                        App.getSearchPrefs().getFiltersAsParams()),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Menu menus} for query via GET request to server.
     * Adds advanced search parameters to request.
     *
     * @param tag used as request tag.
     * @param query text to be searched.
     * @param successListener will be called with {@link Menu menus} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void advanceSearchMenu(String tag, String query,
                                           Response.Listener<Menu[]> successListener,
                                           Response.ErrorListener failureListener) {
        if (isTest);
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                Commons.getUrlForGet(BASE_URL + String.format("/search/advancedSearch/menu/%s", query),
                        App.getSearchPrefs().getFiltersAsParams()),
                Menu[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Recipe recipes} for query via GET request to server.
     *
     * @param tag used as request tag.
     * @param query text to be searched.
     * @param successListener will be called with {@link Recipe recipes} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void searchRecipe(String tag, String query,
                                    Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        if (isTest) {
            String postBody1 = Commons.loadJSONFromAsset("test_recipe1.json");
            String postBody2 = Commons.loadJSONFromAsset("test_recipe2.json");

            Gson gson = new Gson();

            Recipe testRcp1 = gson.fromJson(postBody1, Recipe.class);
            Recipe testRcp2 = gson.fromJson(postBody2, Recipe.class);

            Recipe[] searchResults = {testRcp1, testRcp2};
            successListener.onResponse(searchResults);
        } else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/search/recipe/%s", query),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Menu menus} for query via GET request to server.
     *
     * @param tag used as request tag.
     * @param query text to be searched.
     * @param successListener will be called with {@link Menu menu} objects array.
     * @param failureListener will be called when call fails.
     */
    public static  void searchMenus(String tag, String query,
                                    Response.Listener<Menu[]> successListener,
                                    Response.ErrorListener failureListener) {
        if(isTest);
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                    BASE_URL + String.format("/search/menu/%s", query),
                    Menu[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Edits {@link Recipe recipe} via POST request to server.
     * Excludes {@link Recipe#createdAt createdAt} field from post body.
     *
     * @param tag used as request tag.
     * @param recipe {@link Recipe recipe} to be added.
     * @param successListener will be called with same {@link Recipe recipe} object.
     * @param failureListener will be called when call fails.
     */
    public static void editRecipe(String tag, Recipe recipe,
                                  Response.Listener<Recipe> successListener,
                                  Response.ErrorListener failureListener) {
        if (isTest) {
            successListener.onResponse(recipe);
        } else {
            String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getName().equals("createdAt");
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            }).create().toJson(recipe, Recipe.class);
            mQueue.add(new GeneralRequest<>(Request.Method.POST,
                    BASE_URL + "/recipe/update", Recipe.class, successListener, failureListener)
                    .setPostBodyInJSONForm(postBody).setTag(tag));
        }
    }
    /**
     * Returns {@link Recipe recipe} via GET request to server.
     *
     * @param tag used as request tag.
     * @param recipeId {@link Recipe#id id} of recipe for tags.
     * @param successListener will be called with string array.
     * @param failureListener will be called when call fails.
     */
    @Deprecated
    public static void getRecipeTags(String tag, int recipeId,
                                     Response.Listener<String[]> successListener,
                                     Response.ErrorListener failureListener) {
        if (isTest) successListener.onResponse(new String[] { "Egg", "Salty", "Breakfast" });
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/tags/%d", recipeId),
                String[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Recipe recipe} via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with same {@link Recipe recipe} object.
     * @param failureListener will be called when call fails.
     */
    public static void getRecipe(String tag, int recipeId,
                                 Response.Listener<Recipe> successListener,
                                 Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/view/%d", recipeId),
                Recipe.class, successListener, failureListener).setTag(tag));
    }
    /**
     * Creates new {@link Recipe recipe} for user via POST request to server.
     * Excludes {@link Recipe#userId userId}, {@link Recipe#rating rating} and
     * internal class {@link Ingredient#nutritions nutritions},
     * {@link Recipe#id id} fields from post body.
     *
     * @param tag used as request tag.
     * @param recipe {@link Recipe recipe} to be added.
     * @param successListener will be called with same {@link Recipe recipe} object.
     * @param failureListener will be called when call fails.
     */
    public static void createRecipe(String tag, Recipe recipe,
                                    Response.Listener<Recipe> successListener,
                                    Response.ErrorListener failureListener) {
        if(isTest){
            successListener.onResponse(recipe);
        }
        String postBody = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return (f.getDeclaringClass().equals(Recipe.class) && f.getName().equals("id")) ||
                        (f.getDeclaringClass().equals(Ingredient.class) && f.getName().equals("nutritions")) ||
                        f.getName().equals("userId") || f.getName().equals("rating");
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
    /**
     * Returns all user {@link Recipe recipes} via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with same {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getUserRecipes(String tag,
                                      Response.Listener<Recipe[]> successListener,
                                      Response.ErrorListener failureListener) {
        if(isTest) {
            String postBody1 = Commons.loadJSONFromAsset("test_recipe1.json");
            String postBody2 = Commons.loadJSONFromAsset("test_recipe2.json");
            String postBody3 = Commons.loadJSONFromAsset("test_recipe3.json");

            Gson gson = new Gson();

            Recipe testRcp1 = gson.fromJson(postBody1, Recipe.class);
            Recipe testRcp2 = gson.fromJson(postBody2, Recipe.class);
            Recipe testRcp3 = gson.fromJson(postBody3, Recipe.class);

            testRcp1.setUserId(App.getUserId());
            testRcp2.setUserId(App.getUserId());
            testRcp3.setUserId(App.getUserId());

            Recipe[] userRecipes = {testRcp1, testRcp2, testRcp3};
            successListener.onResponse(userRecipes);
        }
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/recipe/user/%s",
                App.getUserId()), Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns all {@link Recipe recipes} via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with same {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getAllRecipes(String tag,
                                     Response.Listener<Recipe[]> successListener,
                                    Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + "/recipe/all",
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Recipe recipes} recommended for given id via GET request to server.
     *
     * @param tag used as request tag.
     * @param recipeId related recipe {@link Recipe#id id}.
     * @param successListener will be called with same {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getRecommendedRecipesForRecipe(String tag, int recipeId,
                                             Response.Listener<Recipe[]> successListener,
                                             Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/recipe/recommend/%d", recipeId),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Recipe recipes} recommended for user via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with same {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getRecommendedRecipesForUser(String tag,
                                                    Response.Listener<Recipe[]> successListener,
                                                    Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + "/recipe/recommend/",
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Returns {@link Allergy allergys} for user via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with same {@link Allergy allergy} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getUserAllergy(String tag,
                                      Response.Listener<Allergy[]> successListener,
                                      Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/allergy/user/%d", App.getUserId()),
                Allergy[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Adds new {@link Allergy allergy} for user via POST request to server.
     * Excludes {@link Menu#userId userId} field from post body.
     *
     * @param tag used as request tag.
     * @param allergy {@link Allergy allergy} to be added.
     * @param successListener will be called with same {@link Allergy allergy} object.
     * @param failureListener will be called when call fails.
     */
    public static void addAllergy(String tag, Allergy allergy,
                                  Response.Listener<Allergy> successListener,
                                  Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getName().equals("userId");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create()
                .toJson(allergy, Allergy.class);

        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/allergy", Allergy.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));

    }
    /**
     * Adds new consumption data for this recipe via POST request to server.
     * Reformats {@link Recipe#createdAt createdAt} field in post body.
     *
     * @param tag used as request tag.
     * @param recipe used as request tag.
     * @param successListener will be called with {@link Nutrition nutrition} object.
     * @param failureListener will be called when call fails.
     */
    public static void addConsumed(String tag, Recipe recipe,
                                   Response.Listener<Recipe> successListener,
                                   Response.ErrorListener failureListener) {
        String postBody = new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src,
                                                 Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        return new JsonPrimitive(src.getTime());
                    }
                })
                .create()
                .toJson(recipe, Recipe.class);

        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/consume", Recipe.class, successListener, failureListener)
                .setPostBodyInJSONForm(postBody).setTag(tag));

    }
    /**
     * Retrieves daily average nutrition values consumed by user via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link Nutrition nutrition} object.
     * @param failureListener will be called when call fails.
     */
    public static void getDailyAverageConsumed(String tag,
                                               Response.Listener<Nutrition> successListener,
                                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/consume/daily/average/%d", App.getUserId()),
                Nutrition.class, successListener, failureListener).setTag(tag));
    }
    /**
     * Retrieves recipes that is consumed by user via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getAllRecipesConsumed(String tag,
                                             Response.Listener<Recipe[]> successListener,
                                             Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/consume/%d", App.getUserId()),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Retrieves ingredient names and ids for given query
     * via GET request to nutritionix server through our server.
     *
     * @param tag used as request tag.
     * @param query query text to retrieve igredients
     * @param successListener will be called with {@link AutoComplete autoComplete} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void searchIngredients(String tag, String query,
                                         Response.Listener<AutoComplete[]> successListener,
                                         Response.ErrorListener failureListener) {
        GeneralRequest<AutoComplete[]> request = new GeneralRequest<AutoComplete[]>(
                Request.Method.GET,
                BASE_URL + String.format("/ingredient/search/%s*", query),
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
    /**
     * This call is deprecated due to wrong frequent server answer http 500,
     * use {@link #searchIngredients(String, String, Response.Listener, Response.ErrorListener) searchIngredients}
     * call instead.
     *
     * @param tag used as request tag.
     * @param query query text to retrieve igredients
     * @param successListener will be called with {@link AutoComplete autoComplete} objects array.
     * @param failureListener will be called when call fails.
     */
    @Deprecated
    public static void autocompleteIngredients(String tag, String query,
                                               Response.Listener<AutoComplete[]> successListener,
                                               Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + String.format("/ingredient/autocomplete/%s", query),
                AutoComplete[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Gets all menus via GET request to nutritionix server through our server.
     *
     * @param tag used as request tag.
     * @param itemId nutritionix item id.
     * @param successListener will be called with {@link Ingredient ingredient} object.
     * @param failureListener will be called when call fails.
     */
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
                    responseModel.setAmount(1);

                    nutrition.setCalories((float) responseObject.optDouble("nf_calories", 0.0));
                    nutrition.setCarbohydrate((float) responseObject.optDouble("nf_total_carbohydrate", 0.0));
                    nutrition.setFats((float) responseObject.optDouble("nf_total_fat", 0.0));
                    nutrition.setProteins((float) responseObject.optDouble("nf_protein", 0.0));
                    nutrition.setSodium((float) responseObject.optDouble("nf_sodium", 0.0));
                    nutrition.setFiber((float) responseObject.optDouble("nf_dietary_fiber", 0.0));
                    nutrition.setCholesterol((float) responseObject.optDouble("nf_cholesterol", 0.0));
                    nutrition.setSugars((float) responseObject.optDouble("nf_sugars", 0.0));
                    nutrition.setIron((float) responseObject.optDouble("nf_iron_dv", 0.0));

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
    /**
     * Gets all menus via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link Menu menu} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getAllMenus(String tag,
                                   Response.Listener<Menu[]> successListener,
                                   Response.ErrorListener failureListener) {
        if(isTest) {
            String postBody1 = Commons.loadJSONFromAsset("test_menu1.json");

            Gson gson = new Gson();

            Menu testMenu = gson.fromJson(postBody1, Menu.class);
            Menu testMenu1 = gson.fromJson(postBody1, Menu.class);
            Menu testMenu2 = gson.fromJson(postBody1, Menu.class);

            successListener.onResponse(new Menu[] { testMenu, testMenu1, testMenu2 });

        } else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + "/menu", Menu[].class,
                successListener, failureListener).setTag(tag));
    }
    /**
     * Gets menus for user via GET request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link Menu menu} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getUserMenus(String tag,
                                    Response.Listener<Menu[]> successListener,
                                    Response.ErrorListener failureListener) {
        if(isTest) {
            String postBody1 = Commons.loadJSONFromAsset("test_menu1.json");
            Gson gson = new Gson();

            Menu testMenu = gson.fromJson(postBody1, Menu.class);
            Menu testMenu1 = gson.fromJson(postBody1, Menu.class);
            Menu testMenu2 = gson.fromJson(postBody1, Menu.class);

            successListener.onResponse(new Menu[] { testMenu, testMenu1, testMenu2 });
        }
        else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                String.format("%s%s%d", BASE_URL, "/menu/user/", App.getUserId()),
                Menu[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Gets menu with {@link Menu#id id} via GET request to server.
     *
     * @param tag used as request tag.
     * @param menuId {@link Menu#id id} of menu to be retrieved.
     * @param successListener will be called with {@link Menu menu} object.
     * @param failureListener will be called when call fails.
     */
    public static void getMenu(String tag, int menuId,
                               Response.Listener<Menu> successListener,
                               Response.ErrorListener failureListener) {
        if(isTest){
            String postBody1 = Commons.loadJSONFromAsset("test_menu1.json");
            Menu testMenu = new Gson().fromJson(postBody1, Menu.class);

            testMenu.setUserId(App.getUserId());
            testMenu.setId(menuId);

            successListener.onResponse(testMenu);
        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET,
                    BASE_URL + String.format("/menu/%d",  menuId), Menu.class,
                    successListener, failureListener).setTag(tag));
        }
    }
    /**
     * Gets recipes fot menu with {@link Menu#id id} via GET request to server.
     *
     * @param tag used as request tag.
     * @param menuId {@link Menu#id id} of menu to be retrieved.
     * @param successListener will be called with {@link Recipe recipe} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getRecipesforMenu(String tag, int menuId,
                                         Response.Listener<Recipe[]> successListener,
                                         Response.ErrorListener failureListener) {
        mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL +String.format("/menu/%d/recipes", menuId),
                Recipe[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Adds menu as a contribution via POST request to server.
     * Excludes {@link Menu#id id} field from post body.
     *
     * @param tag used as request tag.
     * @param menu {@link Menu menu} to be added.
     * @param successListener will be called with {@link Menu menu} object.
     * @param failureListener will be called when call fails.
     */
    public static void createMenu(String tag, Menu menu,
                                  Response.Listener<Menu> successListener,
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

    public static void getAllComments(String tag, String type, int parentId, Response.Listener<Comment[]> successListener,
                                      Response.ErrorListener failureListener) {
        if(isTest){
            Comment c1 = new Comment("Onur Guler", type, parentId, Commons.getString(R.string.test_comment_recipe1));
            Comment c2 = new Comment("Cagla Balcik", type, parentId, Commons.getString(R.string.test_comment_recipe2));
            Comment c3 = new Comment("Mert Tiftikci", type, parentId, Commons.getString(R.string.test_comment_recipe3));
            Comment c4 = new Comment("Gorkem Onder", type, parentId, Commons.getString(R.string.test_comment_recipe4));

            c1.setCreatedAt(new Date(1449020159));
            c2.setCreatedAt(new Date(1440517159));
            c3.setCreatedAt(new Date(1409507059));
            c4.setCreatedAt(new Date(1440505191));
            successListener.onResponse(new Comment[] {c1, c2, c3, c4});
        } else {
            mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL + String.format("/comment/%s/%d",
                    type, parentId), Comment[].class, successListener, failureListener).setTag(tag));
        }
    }
    /**
     * Brings all comments that have written by given user via GET request to server..
     *
     * @param tag used as request tag.
     * @param type one of ["user", "recipe", "menu"]. Items that could be rated.
     * @param parentId id of model that requested.
     * @param userId id of the user that is requested for the comment.
     * @param successListener will be called with {@link Comment comment} objects array.
     * @param failureListener will be called when call fails.
     */
    public static void getAllCommentsForUser(String tag, String type, int parentId, int userId,
                                             Response.Listener<Comment[]> successListener,
                                             Response.ErrorListener failureListener) {
        if(isTest){
            Comment c1 = new Comment(App.getUser().getFullName(), type,
                    App.getUserId(), Commons.getString(R.string.test_comment_recipe1));
        }
        else mQueue.add(new GeneralRequest<>(Request.Method.GET, BASE_URL +
                String.format("/comment/%s/%d/%d", type, parentId, userId),
                Comment[].class, successListener, failureListener).setTag(tag));
    }
    /**
     * Comments on a contribution via POST request to server.
     * Excludes {@link Comment#id id}, {@link Comment#userFullName userFullName},
     * {@link Comment#userId userId}, {@link Comment#createdAt createdAt}
     * fields from post body.
     *
     * @param tag used as request tag.
     * @param comment {@link Comment comment} to be added.
     * @param successListener will be called with same {@link Comment comment} object.
     * @param failureListener will be called when call fails.
     */
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
    /**
     * Deletes given comment via POST request to server.
     * Excludes {@link Comment#userId userId} field from post body.
     *
     * @param tag used as request tag.
     * @param comment {@link Comment comment} to be deleted.
     * @param successListener will be called with same {@link Comment comment} object.
     * @param failureListener will be called when call fails.
     */
    public static void deleteComment(String tag, Comment comment,
                                     Response.Listener<Comment> successListener,
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
    /**
     * Rates a contribution via POST request to server.
     * Excludes {@link Rate#id id} and {@link Rate#userId userId} fields from post body.
     *
     * @param tag used as request tag.
     * @param rate {@link Rate rate} object that will be delivered.
     * @param successListener will be called with same {@link Rate rate} object.
     * @param failureListener will be called when call fails.
     */
    public static void rate(String tag, Rate rate,
                            Response.Listener<Rate> successListener,
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
    /**
     * Brings average rate of described model via GET request to server.
     *
     * @param tag used as request tag.
     * @param type one of ["user", "recipe", "menu"]. Items that could be rated.
     * @param parentId id of model that requested.
     * @param successListener will be called with {@link Rate rate} object.
     * @param failureListener will be called when call fails.
     */
    public static void getAverageRating(String tag, String type, int parentId,
                                        Response.Listener<Rate> successListener,
                                        Response.ErrorListener failureListener) {
        if(isTest){
            float min = 0.0f; float max = 5.0f;
            Random rand = new Random();
            float avg = rand.nextFloat()*(max-min);
            Rate r1 = new Rate(type, parentId, avg);
            successListener.onResponse(r1);
        } else mQueue.add(new GeneralRequest<>(Request.Method.GET,
                BASE_URL + (String.format("/rate/%s/%d", type, parentId)),
                Rate.class, successListener, failureListener).setTag(tag));
    }
    /**
     * Brings rate of user that has rated to described model via GET request to server.
     *
     * @param tag used as request tag.
     * @param type one of ["user", "recipe", "menu"]. Items that could be rated.
     * @param parentId id of model that requested.
     * @param userId id of the user that is requested for the rate.
     * @param successListener will be called with {@link Rate rate} object.
     * @param failureListener will be called when call fails.
     */
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
    /**
     * Does login call to get bearer token that will be used for authorization
     * via POST request to server. Excludes {@link User#email id} and {@link User#password userId}
     * fields from post body. Response is {@link AccessToken accessToken}
     *
     * @param tag used as request tag.
     * @param user Email and Password will be send from {@link User user} object.
     * @param successListener will be called with {@link AccessToken accessToken} object.
     * @param failureListener will be called when call fails.
     */
    public static void login(String tag, User user,
                             Response.Listener<AccessToken> successListener,
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
    /**
     * Should be called onDestroy of application and manual logout call via POST request to server.
     *
     * @param tag used as request tag.
     * @param successListener will be called with {@link User user} object.
     * @param failureListener will be called when call fails.
     */
    public static void logout(String tag,
                              Response.Listener<User> successListener,
                              Response.ErrorListener failureListener) {

        mQueue.add(new GeneralRequest<>(Request.Method.POST,
                BASE_URL + "/session/logout", User.class,
                successListener, failureListener).setTag(tag));
    }

    public static void loadImageFromUrl(String tag, String url,
                                         Response.Listener<Bitmap> successListener,
                                         Response.ErrorListener errorListener) {
        loadImageFromUrl(tag, url, 0, 0, null, successListener, errorListener);
    }

    public static void loadImageFromUrl(String tag, String url, int maxWidth, int maxHeight,
                                        Bitmap.Config config,
                                        Response.Listener<Bitmap> successListener,
                                        Response.ErrorListener errorListener) {
        mQueue.add(new ImageRequest(url, successListener,
                maxWidth, maxHeight, config, errorListener).setTag(tag));
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
        private Map<String, String> params = new HashMap<>();
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

            Log.v("Request", getUrl() + ", method: " +
                    (method == Request.Method.GET ? "GET" : "POST"));
        }

        public GeneralRequest<T> setPostBodyInJSONForm(Object postObject,
                                                       Class<? extends Object> postClass) {
            this.postBody = gson.toJson(postObject, postClass);
            Log.v("Request", postBody);
            return this;
        }
        /**
         * Sets POST body.
         *
         * @param postBody JSON in string format.
         * @return itself for chain calls.
         */
        public GeneralRequest<T> setPostBodyInJSONForm(String postBody) {
            this.postBody = postBody;
            Log.v("Json body", postBody);
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
        public Map<String, String> getParams() {
            return params;
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
