package com.example.android.chefeea.Classes;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.chefeea.R;

/**
 * Created by irina on 14.12.2017.
 */

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    public TextView mIngredientName;
    public TextView mIngredientQuantity;

    public IngredientsViewHolder(View itemView) {
        super(itemView);

        mIngredientName = itemView.findViewById(R.id.shopping_list_item_name);
        mIngredientQuantity = itemView.findViewById(R.id.shopping_list_item_quantity);
    }
}
