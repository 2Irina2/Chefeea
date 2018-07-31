package com.example.android.chefeea;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.chefeea.Adapters.IconListItemAdapter;
import com.example.android.chefeea.Classes.AnalyticsApplication;
import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.Classes.Recipe;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = RecipeActivity.class.getName();
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Recipe recipe = getIntent().getParcelableExtra("recipe");

        ImageView dishImageView = findViewById(R.id.recipe_photo);
        TextView prepTimeTextView = findViewById(R.id.recipe_time);
        ListView ingredientsListView = findViewById(R.id.recipe_ingredients_list);
        TextView detailsTextView = findViewById(R.id.details_url);

        if (recipe != null) {
            String title = recipe.getRecipeTitle();
            String imageUrl = recipe.getRecipeUrl();
            int prepTime = recipe.getRecipePrepTime();
            List<String> ingredients = recipe.getRecipeIngredients();
            getSupportActionBar().setTitle(title);
            Picasso.get().load(imageUrl).into(dishImageView);
            if(prepTime != 0){
                prepTimeTextView.setText(String.valueOf(prepTime) + " '");
            } else {
                prepTimeTextView.setVisibility(View.GONE);
                findViewById(R.id.recipe_time_icon).setVisibility(View.GONE);
            }
            detailsTextView.setText(recipe.getExtraUrl());
            List<IconListItem> ingredientsList = new ArrayList<IconListItem>();
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientsList.add(new IconListItem(ingredients.get(i)));
            }
            IconListItemAdapter ingredientsAdapter = new IconListItemAdapter(this,
                    R.layout.recipe_ingredients_list_item, ingredientsList);
            ingredientsListView.setAdapter(ingredientsAdapter);
        }

        detailsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(recipe.getExtraUrl()));
                startActivity(i);
            }
        });
        AnalyticsApplication analyticsApplication = (AnalyticsApplication) getApplication();
        mTracker = analyticsApplication.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        Intent widgetIntent = new Intent(this, RecipeWidgetProvider.class);
        widgetIntent.setAction(RecipeWidgetProvider.ACTION_UPDATE_INGREDIENTS);
        widgetIntent.putExtra("recipe", recipe);
        int ids[] = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        sendBroadcast(widgetIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = "recipe";
        Log.i(TAG, "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
