package com.example.android.chefeea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.chefeea.Adapters.IconListItemAdapter;
import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.Classes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerContentList;
    private ImageView mMenuToggler;
    private ImageView mSettingsToggler;
    private Button mSurpriseRecipeButton;
    private Button mIngredientsRecipeButton;

    private TextView debugPreferenceTextView;

    IconListItemAdapter mDrawerAdapter;
    List<IconListItem> mDrawerItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerContentList = findViewById(R.id.drawer_content);
        mMenuToggler = findViewById(R.id.drawer_button);
        mSettingsToggler = findViewById(R.id.settings_button);
        mSurpriseRecipeButton = findViewById(R.id.surprise_button);
        mIngredientsRecipeButton = findViewById(R.id.ingredients_button);

        String[] ingredients1 = new String[]{"banana", "capsicum", "nutella"};
        String[] preparation1 = new String[]{"1. asdfg", "2. qwertyu", "3. zxcvb"};
        String[] ingredients2 = new String[]{"tomato", "tomato", "tomato", "tomato", "tomato"};
        String[] preparation2 = new String[]{"1. mnbvcx", "2. lkjhgf", "3. poiuyt"};
        final Recipe recipeSurprise = new Recipe("Surprise", R.drawable.anaconda_windows_6,
                "30'", ingredients1, preparation1);
        final Recipe recipeIngredients = new Recipe("Ingredients", R.drawable.anaconda_windows_6,
                "300'", ingredients2, preparation2);

        initializeDrawer();

        setupSharedPreferences();

        mSettingsToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        mSurpriseRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent surpriseIntent = new Intent(getApplicationContext(), RecipeActivity.class);
                surpriseIntent.putExtra(getResources().getString(R.string.recipe_intent_key),
                        recipeSurprise);
                startActivity(surpriseIntent);
            }
        });

        mIngredientsRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ingredientsIntent = new Intent(getApplicationContext(), RecipeActivity.class);
                ingredientsIntent.putExtra(getResources().getString(R.string.recipe_intent_key),
                        recipeIngredients);
                startActivity(ingredientsIntent);
            }
        });

    }

    private void initializeDrawer(){

        mDrawerItemsList = new ArrayList<IconListItem>();

        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_home),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_fridge),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_recipes),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_shopping_list),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_timer),
                R.drawable.ic_settings_black_24dp));

        mDrawerAdapter = new IconListItemAdapter(this, R.layout.drawer_list_item, mDrawerItemsList);
        mDrawerContentList.setAdapter(mDrawerAdapter);


        mDrawerLayout.closeDrawer(Gravity.START);
        mMenuToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START
                );
            }
        });
    }

    private void setupSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String debugText =  preferences.getString(
                getResources().getString(R.string.language_preference_key),
                getResources().getString(R.string.language_preference_default_value));

        debugText = debugText + " " +  preferences.getString(
                getResources().getString(R.string.food_preference_key),
                getResources().getString(R.string.food_preference_default_value));


        //TODO(1): Add allergy preference
        //TODO(2): Add color theme preference
        //TODO(3): Update fridge once a recipe is cooked preference
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START
        )) {
            mDrawerLayout.closeDrawer(Gravity.START
            );
        } else {
            super.onBackPressed();
        }
    }
}
