package com.example.android.chefeea;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ImageView mMenuToggler;
    private ImageView mSettingsToggler;
    private ListView mDrawerContentList;

    private TextView debugPreferenceTextView;

    DrawerAdapter mDrawerAdapter;
    List<DrawerItem> mDrawerItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMenuToggler = findViewById(R.id.drawer_button);
        mSettingsToggler = findViewById(R.id.settings_button);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerContentList = findViewById(R.id.drawer_content);

        debugPreferenceTextView = findViewById(R.id.textView);

        initializeDrawer();

        setupSharedPreferences();

        mSettingsToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

    }

    private void initializeDrawer(){

        mDrawerItemsList = new ArrayList<DrawerItem>();

        mDrawerItemsList.add(new DrawerItem(getResources().getString(R.string.drawer_content_fridge),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new DrawerItem(getResources().getString(R.string.drawer_content_recipes),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new DrawerItem(getResources().getString(R.string.drawer_content_shopping_list),
                R.drawable.ic_settings_black_24dp));
        mDrawerItemsList.add(new DrawerItem(getResources().getString(R.string.drawer_content_timer),
                R.drawable.ic_settings_black_24dp));

        mDrawerAdapter = new DrawerAdapter(this, R.layout.drawer_list_item, mDrawerItemsList);
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

        debugPreferenceTextView.setText(debugText);

        //TODO(1): Add allergy preference
        //TODO(2): Add color theme preference
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
