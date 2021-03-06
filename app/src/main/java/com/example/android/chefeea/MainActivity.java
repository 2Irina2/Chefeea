package com.example.android.chefeea;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.chefeea.Adapters.IconListItemAdapter;
import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.Fragments.HomeFragment;
import com.example.android.chefeea.Fragments.RecipesFragment;
import com.example.android.chefeea.Fragments.ShoppingListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_content) ListView mDrawerContentList;

    IconListItemAdapter mDrawerAdapter;
    List<IconListItem> mDrawerItemsList;
    int currentFragmentNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            currentFragmentNumber = savedInstanceState.getInt("fragmentIndex");
        }

        initializeDrawer();

        setupSharedPreferences();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragmentIndex", currentFragmentNumber);
    }

    /**
     * DRAWER RELATED FUNCTIONS
     *
     * initializeDrawer     creates the layout, sets the Adapter and OnItemClickListener and
     *                      calls selectDrawerItem(0);
     *
     * selectDrawerItem     function to navigate through drawer items / fragments;
     *
     * openDrawer           function called from other fragments to open or close drawer;
     *
     */

    private void initializeDrawer(){

        mDrawerItemsList = new ArrayList<>();

        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_home),
                R.drawable.ic_home_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_recipes),
                R.drawable.ic_import_contacts_black_24dp));
        mDrawerItemsList.add(new IconListItem(getResources().getString(R.string.drawer_content_shopping_list),
                R.drawable.ic_format_list_bulleted_black_24dp));

        mDrawerAdapter = new IconListItemAdapter(this, R.layout.drawer_list_item, mDrawerItemsList);
        mDrawerContentList.setAdapter(mDrawerAdapter);
        mDrawerContentList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout.closeDrawer(Gravity.START);

        selectDrawerItem(currentFragmentNumber);
    }

    public void selectDrawerItem(int positon){

        Fragment fragment = null;

        switch (positon){
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new RecipesFragment();
                break;
            case 2:
                fragment = new ShoppingListFragment();
                break;
            default:
                break;
        }

        currentFragmentNumber = positon;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment)
                .commit();

        getSupportActionBar().setTitle(mDrawerItemsList.get(positon).getItemName());
        mDrawerLayout.closeDrawer(Gravity.START);
        mDrawerContentList.setItemChecked(positon, true);

    }

    public void openDrawer(){
        mDrawerLayout.openDrawer(Gravity.START);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectDrawerItem(i);
        }
    }


    /**
     * SHARED PREFERENCES RELATED FUNCTIONS
     *
     * setupSharedPreferences   reads data from shared preferences
     *
     */

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
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START
        )) {
            mDrawerLayout.closeDrawer(Gravity.START
            );
        }
        else if(currentFragmentNumber != 0){
            selectDrawerItem(0);
            currentFragmentNumber = 0;
        }
        else {
            super.onBackPressed();
        }
    }

}
