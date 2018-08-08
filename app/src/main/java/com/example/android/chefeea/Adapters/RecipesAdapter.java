package com.example.android.chefeea.Adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.chefeea.Database.RecipeEntry;
import com.example.android.chefeea.MainActivity;
import com.example.android.chefeea.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private int mLayoutResourceId;
    private List<RecipeEntry> mRecipes;
    public OnItemClickListener mClickListener;
    public OnItemLongClickListener mLongClickListener;
    private Context mContext;

    public RecipesAdapter(List<RecipeEntry> recipes, int layoutResourceId) {
        mRecipes = recipes;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recipeViewHolder = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutResourceId, parent, false);
        mContext = parent.getContext();
        return new RecipesViewHolder(recipeViewHolder);
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        RecipeEntry recipeEntry = mRecipes.get(position);
        String name = recipeEntry.getEntryTitle();
        String imageUrl = recipeEntry.getEntryImgUrl();

        holder.mRecipeTitle.setText(name);
        Picasso.get().load(imageUrl).into(holder.mRecipeImage);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public RecipeEntry getItem(int position){
        return mRecipes.get(position);
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mRecipeImage;
        private TextView mRecipeTitle;

        private RecipesViewHolder(final View itemView) {
            super(itemView);

            mRecipeImage = itemView.findViewById(R.id.recipe_list_item_image);
            mRecipeTitle = itemView.findViewById(R.id.recipe_list_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override public void onClick(View v) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((MainActivity)mContext,
                            mRecipeImage,
                            ViewCompat.getTransitionName(mRecipeImage)).toBundle();
                    mClickListener.onItemClick(v, getAdapterPosition(), bundle);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                   mLongClickListener.onItemLongClick(view, getAdapterPosition());
                   return true;
                }
            });
        }

    }

    public void setClickListener(OnItemClickListener itemClickListener, OnItemLongClickListener itemLongClickListener) {
        this.mClickListener = itemClickListener;
        this.mLongClickListener = itemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Bundle bundle);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
