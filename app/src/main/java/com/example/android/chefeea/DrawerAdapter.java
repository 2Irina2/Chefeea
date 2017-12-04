package com.example.android.chefeea;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by irina on 03.12.2017.
 */

public class DrawerAdapter extends ArrayAdapter<DrawerItem> {


    Context adapterContext;
    int layoutResId;
    List<DrawerItem> drawerItemList;

    public DrawerAdapter(@NonNull Context context, int resource, List<DrawerItem> list) {
        super(context, resource, list);
        adapterContext = context;
        drawerItemList = list;
        layoutResId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DrawerItemHolder drawerItemHolder;
        View view = convertView;

        LayoutInflater inflater = ((Activity) adapterContext).getLayoutInflater();
        drawerItemHolder = new DrawerItemHolder();

        view = inflater.inflate(layoutResId, parent, false);

        drawerItemHolder.Icon = (ImageView) view.findViewById(R.id.drawer_item_icon);
        drawerItemHolder.ItemName = (TextView) view.findViewById(R.id.drawer_item_text);

        DrawerItem item = drawerItemList.get(position);

        drawerItemHolder.Icon.setImageDrawable(view.getResources().getDrawable(item.getImgResId()));
        drawerItemHolder.ItemName.setText(item.getItemName());

        return view;
    }

    private static class DrawerItemHolder{
        TextView ItemName;
        ImageView Icon;
    }
}
