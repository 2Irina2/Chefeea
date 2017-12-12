package com.example.android.chefeea.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.chefeea.Classes.Recipe;
import com.example.android.chefeea.MainActivity;
import com.example.android.chefeea.R;
import com.example.android.chefeea.RecipeActivity;
import com.example.android.chefeea.SettingsActivity;

/**
 * Created by irina on 12.12.2017.
 */

public class HomeFragment extends Fragment{

    private ImageView mMenuToggler;
    private ImageView mSettingsToggler;
    private Button mSurpriseRecipeButton;
    private Button mIngredientsRecipeButton;

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mMenuToggler = view.findViewById(R.id.drawer_button);
        mSettingsToggler = view.findViewById(R.id.settings_button);
        mSurpriseRecipeButton = view.findViewById(R.id.surprise_button);
        mIngredientsRecipeButton = view.findViewById(R.id.ingredients_button);

        String[] ingredients1 = new String[]{"banana", "capsicum", "nutella"};
        String[] preparation1 = new String[]{"1. asdfg", "2. qwertyu", "3. zxcvb"};
        String[] ingredients2 = new String[]{"tomato", "tomato", "tomato", "tomato", "tomato"};
        String[] preparation2 = new String[]{"1. mnbvcx", "2. lkjhgf", "3. poiuyt"};
        final Recipe recipeSurprise = new Recipe("Surprise", R.drawable.anaconda_windows_6,
                "30'", ingredients1, preparation1);
        final Recipe recipeIngredients = new Recipe("Ingredients", R.drawable.anaconda_windows_6,
                "300'", ingredients2, preparation2);

        mMenuToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).openDrawer();
            }
        });

        mSettingsToggler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(getContext(), SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

        mSurpriseRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent surpriseIntent = new Intent(getContext(), RecipeActivity.class);
                surpriseIntent.putExtra(getResources().getString(R.string.recipe_intent_key),
                        recipeSurprise);
                startActivity(surpriseIntent);
            }
        });

        mIngredientsRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ingredientsIntent = new Intent(getContext(), RecipeActivity.class);
                ingredientsIntent.putExtra(getResources().getString(R.string.recipe_intent_key),
                        recipeIngredients);
                startActivity(ingredientsIntent);
            }
        });


        return view;
    }
}
