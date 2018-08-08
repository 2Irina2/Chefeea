package com.example.android.chefeea.Fragments;


import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.chefeea.Adapters.IngredientsAdapter;
import com.example.android.chefeea.Classes.AppExecutors;
import com.example.android.chefeea.Classes.ShoppingListViewModel;
import com.example.android.chefeea.Database.AppDatabase;
import com.example.android.chefeea.Database.ShoppingListEntry;
import com.example.android.chefeea.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by irina on 12.12.2017.
 */

public class ShoppingListFragment extends Fragment {

    @BindView(R.id.shopping_list_recyclerview) RecyclerView mRecyclerView;
    @BindView(R.id.shopping_list_button) FloatingActionButton mAddButton;

    private AppDatabase mDb;
    private IngredientsAdapter mRecyclerViewAdapter;

    public ShoppingListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ButterKnife.bind(this, view);
        mDb = AppDatabase.getInstance(getContext());

        ShoppingListViewModel viewModel = ViewModelProviders.of(getActivity()).get(ShoppingListViewModel.class);
        viewModel.getEntries().observe(this, new Observer<List<ShoppingListEntry>>() {
            @Override
            public void onChanged(@Nullable List<ShoppingListEntry> shoppingListEntries) {
                wireUpRecyclerView(view, shoppingListEntries);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog();
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<ShoppingListEntry> tasks = mRecyclerViewAdapter.getIngredients();
                        mDb.shoppingListEntryDao().deleteEntry(tasks.get(position));
                        ShoppingListViewModel viewModel = ViewModelProviders.of(getActivity()).get(ShoppingListViewModel.class);
                        viewModel.getEntries().observe(getActivity(), new Observer<List<ShoppingListEntry>>() {
                            @Override
                            public void onChanged(@Nullable List<ShoppingListEntry> shoppingListEntries) {
                                mRecyclerViewAdapter = new IngredientsAdapter(shoppingListEntries
                                        , R.layout.shopping_list_item);
                            }
                        });
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);

        return view;
    }

    private void wireUpRecyclerView(View view, List<ShoppingListEntry> ingredients) {

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerViewAdapter = new IngredientsAdapter(ingredients, R.layout.shopping_list_item);

        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void popUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View dialogView = getLayoutInflater().inflate(R.layout.shopping_list_dialog, null);
        builder.setView(dialogView)
                .setTitle(getResources().getString(R.string.shopping_list_dialog_title))
                .setPositiveButton(getResources().getString(R.string.shopping_list_dialog_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText ingredientNameEditText = dialogView
                                .findViewById(R.id.shopping_list_dialog_name);
                        EditText ingredientQuantityEditText = dialogView
                                .findViewById(R.id.shopping_list_dialog_quantity);
                        if (ingredientNameEditText.length() == 0) {
                            return;
                        }

                        int quantity = 1;
                        String ingredient = null;
                        try {
                            ingredient = ingredientNameEditText.getText().toString();
                            quantity = Integer.parseInt(ingredientQuantityEditText.getText().toString());
                        } catch (Exception exception) {
                            Log.e(ShoppingListFragment.class.getName(), getResources().getString(R.string.shopping_list_dialog_exception_data));
                        }

                        addToShoppingList(ingredient, quantity);

                        Toast toast = Toast.makeText(getContext(),
                                getResources().getString(R.string.shopping_list_dialog_positie_toast),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.shopping_list_dialog_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast toast = Toast.makeText(getContext(),
                                getResources().getString(R.string.shopping_list_dialog_negative_toast),
                                Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
        builder.create().show();

    }

    public void addToShoppingList(String ingredient, int quantity) {
        final ShoppingListEntry shoppingListEntry = new ShoppingListEntry(ingredient, quantity);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.shoppingListEntryDao().insertEntry(shoppingListEntry);
            }
        });
    }
}