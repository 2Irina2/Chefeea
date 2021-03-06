package com.example.android.chefeea.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.R;

import java.util.List;

/**
 * Created by irina on 09.12.2017.
 */

public class IconListItemAdapter extends ArrayAdapter<IconListItem>  {
    Context adapterContext;
    int layoutResId;
    List<IconListItem> iconListItemList;

    public IconListItemAdapter(@NonNull Context context, int resource, List<IconListItem> list) {
        super(context, resource, list);
        adapterContext = context;
        iconListItemList = list;
        layoutResId = resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListItemHolder listItemHolder;
        View view = convertView;

        LayoutInflater inflater = ((Activity) adapterContext).getLayoutInflater();
        view = inflater.inflate(layoutResId, parent, false);

        listItemHolder = new ListItemHolder();

        listItemHolder.ItemName = view.findViewById(R.id.list_item_text);
        listItemHolder.Icon = view.findViewById(R.id.list_item_icon);

        IconListItem item = iconListItemList.get(position);

        listItemHolder.ItemName.setText(item.getItemName());
        if(item.getImgResId() != 0){
            listItemHolder.Icon.setImageDrawable(getContext().getDrawable(item.getImgResId()));
        }

        return view;
    }

    private static class ListItemHolder{
        TextView ItemName;
        ImageView Icon;
    }
}

