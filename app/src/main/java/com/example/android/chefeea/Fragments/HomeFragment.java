package com.example.android.chefeea.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.chefeea.Classes.Recipe;
import com.example.android.chefeea.MainActivity;
import com.example.android.chefeea.R;
import com.example.android.chefeea.RecipeActivity;
import com.example.android.chefeea.SettingsActivity;
import com.example.android.chefeea.Utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by irina on 12.12.2017.
 */

public class HomeFragment extends Fragment{

    private AdView mAdView;
    private ImageView mMenuToggler;
    private ImageView mSettingsToggler;
    private Button mSurpriseRecipeButton;

    public final static char[] randomQueryParameters = new char[]{'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public HomeFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mMenuToggler = view.findViewById(R.id.drawer_button);
        mSettingsToggler = view.findViewById(R.id.settings_button);
        mSurpriseRecipeButton = view.findViewById(R.id.surprise_button);

        mMenuToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        mSettingsToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
        final String preference = preferences.getString(
                getResources().getString(R.string.food_preference_key),
                getResources().getString(R.string.food_preference_default_value));

        mSurpriseRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL queryUrl;
                if(preference.equals("VEG")){
                    queryUrl = NetworkUtils.buildUrl("vegetarian");
                } else {
                    queryUrl = NetworkUtils.buildUrl(String.valueOf(randomQueryParameters[(int) (Math.random() * randomQueryParameters.length)]));
                }

                new RecipeQueryTask().execute(queryUrl);
            }
        });

        MobileAds.initialize(getContext(),"ca-app-pub-3940256099942544/6300978111");
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        return view;
    }


    public class RecipeQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String recipeSearchResults = null;
            try{
                recipeSearchResults = NetworkUtils.getResponseFromHTTPUrl(searchUrl);
            } catch (IOException e){
                Log.e(MainActivity.class.getName(), "Error getting response from HTTP URL");
            }

            return recipeSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                try {
                    ArrayList<Recipe> recipes = NetworkUtils.parseJSONString(s);
                    Intent surpriseIntent = new Intent(getContext(), RecipeActivity.class);
                    int randomRecipe = (int) (Math.random() * recipes.size());
                    surpriseIntent.putExtra(getResources().getString(R.string.recipe_intent_key),
                            recipes.get(randomRecipe));
                    startActivity(surpriseIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
