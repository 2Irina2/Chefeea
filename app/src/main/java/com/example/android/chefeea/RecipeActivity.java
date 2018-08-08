package com.example.android.chefeea;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.chefeea.Adapters.IconListItemAdapter;
import com.example.android.chefeea.Classes.AnalyticsApplication;
import com.example.android.chefeea.Classes.AppExecutors;
import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.Database.AppDatabase;
import com.example.android.chefeea.Database.RecipeEntry;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private static final String TAG = RecipeActivity.class.getName();
    private Tracker mTracker;
    private AppDatabase mDb;

    @BindView(R.id.recipe_photo) ImageView dishImageView;
    @BindView(R.id.recipe_time) TextView prepTimeTextView;
    @BindView(R.id.recipe_ingredients_list) ListView ingredientsListView;
    @BindView(R.id.details_url) TextView detailsTextView;
    @BindView(R.id.recipe_time_icon) ImageView recipeTimeIcon;
    @BindView(R.id.recipe_button) Button recipeFinishedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecipeEntry recipe = getIntent().getParcelableExtra("recipe");
        final boolean showButton = getIntent().getBooleanExtra("showButton", true);

        if(showButton == false){
            recipeFinishedButton.setVisibility(View.GONE);
        }
        mDb = AppDatabase.getInstance(getApplicationContext());

        if (recipe != null) {
            String title = recipe.getEntryTitle();
            String imageUrl = recipe.getEntryImgUrl();
            int prepTime = recipe.getEntryPrepTime();
            List<String> ingredients = recipe.getEntryIngredients();
            getSupportActionBar().setTitle(title);
            Picasso.get().load(imageUrl).into(dishImageView);
            if(prepTime != 0){
                prepTimeTextView.setText(String.valueOf(prepTime) + " '");
            } else {
                prepTimeTextView.setVisibility(View.GONE);
                recipeTimeIcon.setVisibility(View.GONE);
            }
            detailsTextView.setText(recipe.getEntryUrl());
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
                i.setData(Uri.parse(recipe.getEntryUrl()));
                startActivity(i);
            }
        });

        recipeFinishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.recipeEntryDao().insertEntry(recipe);
                    }
                });
                Toast.makeText(getApplicationContext(), "Yay! Recipe saved.", Toast.LENGTH_SHORT).show();
            }
        });
        AnalyticsApplication analyticsApplication = (AnalyticsApplication) getApplication();
        mTracker = analyticsApplication.getDefaultTracker();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        ScrollView sv = findViewById(R.id.scrl);
        sv.smoothScrollTo(0, 0);

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
