package com.example.android.chefeea.Adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.chefeea.Classes.IngredientsViewHolder;
import com.example.android.chefeea.Database.ShoppingListEntry;

import java.util.List;

/**
 * Created by irina on 14.12.2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    private int mLayoutResourceId;
    private List<ShoppingListEntry> mIngredients;

    public IngredientsAdapter(List<ShoppingListEntry> ingredients, int layoutResourceId){
        mIngredients = ingredients;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ingredientsViewHolder = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);

        return new IngredientsViewHolder(ingredientsViewHolder);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        ShoppingListEntry shoppingListEntry = mIngredients.get(position);
        String name = shoppingListEntry.getEntryName();
        int quantity = shoppingListEntry.getQuantity();
        int id = shoppingListEntry.getEntryId();

        holder.mIngredientName.setText(name);
        holder.mIngredientQuantity.setText(Integer.toString(quantity));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


    public void setIngredients(List<ShoppingListEntry> ingredientsParam){
        mIngredients = ingredientsParam;
    }

    public List<ShoppingListEntry> getIngredients(){
        return mIngredients;
    }
}
