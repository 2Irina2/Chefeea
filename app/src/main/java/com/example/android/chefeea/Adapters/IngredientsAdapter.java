package com.example.android.chefeea.Adapters;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.chefeea.Classes.IngredientsViewHolder;
import com.example.android.chefeea.Database.ChefeeaContract;

/**
 * Created by irina on 14.12.2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

    private Cursor mCursor;
    private int mLayoutResourceId;

    public IngredientsAdapter(Cursor cursor, int layoutResourceId){
        mCursor = cursor;
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
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String ingredient = mCursor.getString(mCursor.getColumnIndex(
                ChefeeaContract.ShoppingListEntry.COLUMN_INGREDIENT));
        int quantity = mCursor.getInt(mCursor.getColumnIndex(
                ChefeeaContract.ShoppingListEntry.COLUMN_QUANTITY));
        String strQuantity = String.valueOf(quantity);

        long id = mCursor.getLong(mCursor.getColumnIndex(ChefeeaContract.ShoppingListEntry._ID));

        holder.mIngredientQuantity.setText(strQuantity);
        holder.mIngredientName.setText(ingredient);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){

        if(mCursor != null){
            mCursor.close();
        }

        mCursor = newCursor;

        if(newCursor != null){
            this.notifyDataSetChanged();
        }
    }
}
