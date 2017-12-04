package com.example.android.chefeea;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ImageView mMenuToggler;
    private ListView mDrawerContentList;

    DrawerAdapter mDrawerAdapter;
    List<DrawerItem> mDrawerItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerItemsList = new ArrayList<DrawerItem>();
        mMenuToggler = findViewById(R.id.drawer_button);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerContentList = findViewById(R.id.drawer_content);

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


        mDrawerLayout.closeDrawer(Gravity.LEFT);
        mMenuToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START
                );
            }
        });

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
