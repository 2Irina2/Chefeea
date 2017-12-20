package com.example.android.chefeea.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.chefeea.Adapters.IngredientsAdapter;
import com.example.android.chefeea.R;
import com.example.android.chefeea.Database.ChefeeaContract;
import com.example.android.chefeea.Database.ChefeeaDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irina on 12.12.2017.
 */

public class ShoppingListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private IngredientsAdapter mRecyclerViewAdapter;

    private SQLiteDatabase mDb;

    public ShoppingListFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        ChefeeaDbHelper dbHelper = new ChefeeaDbHelper(getContext());
        mDb = dbHelper.getWritableDatabase();
        Cursor shoppingListCursor = getAllIngredients();

        wireUpRecyclerView(view, shoppingListCursor);


        FloatingActionButton mAddButton = view.findViewById(R.id.shopping_list_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpDialog();
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                long id = (long) viewHolder.itemView.getTag();
                removeFromShoppingList(id);
                mRecyclerViewAdapter.swapCursor(getAllIngredients());
            }
        }).attachToRecyclerView(mRecyclerView);


        return view;
    }

    private void wireUpRecyclerView(View view, Cursor cursor) {

        mRecyclerView = view.findViewById(R.id.shopping_list_recyclerview);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerViewAdapter = new IngredientsAdapter(cursor, R.layout.shopping_list_item);

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

                        long insertionResult = addToShoppingList(ingredient, quantity);

                        mRecyclerViewAdapter.swapCursor(getAllIngredients());

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

    private Cursor getAllIngredients() {
        return mDb.query(
                ChefeeaContract.ShoppingListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ChefeeaContract.ShoppingListEntry._ID
        );
    }

    public long addToShoppingList(String ingredient, int quantity) {

        ContentValues cv = new ContentValues();
        cv.put(ChefeeaContract.ShoppingListEntry.COLUMN_INGREDIENT, ingredient);
        cv.put(ChefeeaContract.ShoppingListEntry.COLUMN_QUANTITY, quantity);
        return mDb.insert(ChefeeaContract.ShoppingListEntry.TABLE_NAME, null, cv);
    }

    private boolean removeFromShoppingList(long id) {
        return mDb.delete(ChefeeaContract.ShoppingListEntry.TABLE_NAME,
                ChefeeaContract.ShoppingListEntry._ID + " =" + id,
                null) > 0;
    }
}