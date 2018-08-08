package com.example.android.chefeea.Utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.chefeea.Database.RecipeEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by irina on 20.01.2018.
 */

public class NetworkUtils {

    final static String RANDOM_RECIPE_BASE_URL = "https://api.edamam.com/search";
    final static String PARAM_QUERY = "q";
    final static String PARAM_APP_ID = "app_id";
    final static String PARAM_APP_KEY = "app_key";


    public static URL buildUrl(String queryParameter){
        Uri builtUri = Uri.parse(RANDOM_RECIPE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, queryParameter)
                .appendQueryParameter(PARAM_APP_ID, "INSERT APP ID HERE")
                .appendQueryParameter(PARAM_APP_KEY, "INSERT APP KEY HERE")
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
            Log.e("url = ", builtUri.toString());
        } catch (MalformedURLException malformedURLException){
            Log.e(NetworkUtils.class.getName(), "Uri to URL failure: " + builtUri.toString());
        }

        return url;
    }

    public static String getResponseFromHTTPUrl(URL url) throws IOException{

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.e(NetworkUtils.class.getName(), Integer.toString(urlConnection.getResponseCode()) + urlConnection.getResponseMessage());

        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<RecipeEntry> parseJSONString(String jsonString) throws JSONException {
        JSONObject response = new JSONObject(jsonString);

        ArrayList<RecipeEntry> recipes = new ArrayList<>();

        if(response.has("hits") || response.getJSONArray("hits").length() == 0){
            JSONArray jsonHits = response.getJSONArray("hits");
            for(int i = 0; i < jsonHits.length(); i++){
                if(jsonHits.getJSONObject(i).has("recipe")){
                    JSONObject jsonRecipe = jsonHits.getJSONObject(i).getJSONObject("recipe");
                    String jsonName = "N/A";
                    if(jsonRecipe.has("label")){
                        jsonName = jsonRecipe.getString("label");
                    }
                    String jsonImage = "N/A";
                    if(jsonRecipe.has("image")){
                        jsonImage = jsonRecipe.getString("image");
                    }
                    int jsonTime = 0;
                    if(jsonRecipe.has("totalTime")){
                        jsonTime = jsonRecipe.getInt("totalTime");
                    }
                    ArrayList<String> ingredients = new ArrayList<>();
                    if(jsonRecipe.has("ingredientLines")){
                        JSONArray jsonIngredients = jsonRecipe.getJSONArray("ingredientLines");
                        for(int j = 0; j < jsonIngredients.length(); j++){
                            ingredients.add(jsonIngredients.getString(j));
                        }
                    }
                    String jsonUrl = "N/A";
                    if(jsonRecipe.has("url")){
                        jsonUrl = jsonRecipe.getString("url");
                    }

                    RecipeEntry recipe = new RecipeEntry(jsonName, jsonImage, jsonTime, ingredients, jsonUrl);
                    recipes.add(recipe);
                }
            }
        } else {
            Log.e(NetworkUtils.class.getName(), "No results found.");
        }

        return recipes;
    }
}














