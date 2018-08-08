package com.example.android.chefeea.Fragments;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.chefeea.Adapters.RecipesAdapter;
import com.example.android.chefeea.Classes.AppExecutors;
import com.example.android.chefeea.Classes.RecipesViewModel;
import com.example.android.chefeea.Classes.ShoppingListViewModel;
import com.example.android.chefeea.Database.AppDatabase;
import com.example.android.chefeea.Database.RecipeEntry;
import com.example.android.chefeea.Database.ShoppingListEntry;
import com.example.android.chefeea.MainActivity;
import com.example.android.chefeea.R;
import com.example.android.chefeea.RecipeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irina on 12.12.2017.
 */

public class RecipesFragment extends Fragment {

    private RecipesAdapter recyclerViewAdapter;
    private AppDatabase mDb;

    @BindView(R.id.recipes_recyclerview)
    RecyclerView recyclerView;

    public RecipesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);
        mDb = AppDatabase.getInstance(getContext());

        RecipesViewModel viewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        viewModel.getEntries().observe(this, new Observer<List<RecipeEntry>>() {
            @Override
            public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                wireUpRecyclerview(view, recipeEntries);
            }
        });

        return view;
    }

    private void wireUpRecyclerview(View view, List<RecipeEntry> entries) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), calculateNumberOfColumns(getContext())));
        recyclerViewAdapter = new RecipesAdapter(entries, R.layout.recipe_list_item);
        recyclerViewAdapter.setClickListener(new RecipesAdapter.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(View view, int position, Bundle bundle) {
                                                     RecipeEntry recipe = recyclerViewAdapter.getItem(position);
                                                     Log.e(RecipesFragment.class.getName(), "item was clicked: " + Integer.toString(position));
                                                     Intent startRecipeActivity = new Intent(getContext(), RecipeActivity.class);
                                                     startRecipeActivity.putExtra("recipe", recipe);
                                                     startRecipeActivity.putExtra("showButton", false);
                                                     startActivity(startRecipeActivity, bundle);
                                                 }
                                             },
                new RecipesAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, final int position) {
                        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setTitle("Detele")
                                .setMessage("Do you really want to delete this recipe?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDb.recipeEntryDao().deleteEntry(recyclerViewAdapter.getItem(position));
                                                RecipesViewModel viewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
                                                viewModel.getEntries().observe(getActivity(), new Observer<List<RecipeEntry>>() {
                                                    @Override
                                                    public void onChanged(@Nullable List<RecipeEntry> recipeEntries) {
                                                        recyclerViewAdapter = new RecipesAdapter(recipeEntries, R.layout.recipe_list_item);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                });
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    public static int calculateNumberOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 150;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }
}
