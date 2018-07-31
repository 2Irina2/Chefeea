package com.example.android.chefeea.Utils;

import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;

import com.example.android.chefeea.Classes.Recipe;
import com.example.android.chefeea.Fragments.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
                .appendQueryParameter(PARAM_APP_ID, "69cdd674")
                .appendQueryParameter(PARAM_APP_KEY, "05be882605627020f8a25ffb10040e7d")
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

    public static ArrayList<Recipe> parseJSONString(String jsonString) throws JSONException {
        JSONObject response = new JSONObject(jsonString);

        ArrayList<Recipe> recipes = new ArrayList<>();

        JSONArray jsonHits = response.getJSONArray("hits");
        for(int i = 0; i < jsonHits.length(); i++){
            JSONObject jsonRecipe = jsonHits.getJSONObject(i).getJSONObject("recipe");
            String jsonName = jsonRecipe.getString("label");
            String jsonImage = jsonRecipe.getString("image");
            int jsonTime = jsonRecipe.getInt("totalTime");
            JSONArray jsonIngredients = jsonRecipe.getJSONArray("ingredientLines");
            String jsonUrl = jsonRecipe.getString("url");
            JSONArray jsonHealthLabels = jsonRecipe.getJSONArray("healthLabels");

            ArrayList<String> ingredients = new ArrayList<>();
            for(int j = 0; j < jsonIngredients.length(); j++){
                ingredients.add(jsonIngredients.getString(j));
            }
            ArrayList<String> healthLabels = new ArrayList<>();
            for(int j = 0; j < jsonHealthLabels.length(); j++){
                healthLabels.add(jsonHealthLabels.getString(j));
            }

            Recipe recipe = new Recipe(jsonName, jsonImage, jsonTime, ingredients, jsonUrl, healthLabels);
            recipes.add(recipe);
        }

        return recipes;
    }
}














