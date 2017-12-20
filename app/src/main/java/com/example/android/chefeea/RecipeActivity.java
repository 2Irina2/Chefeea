package com.example.android.chefeea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.chefeea.Adapters.IconListItemAdapter;
import com.example.android.chefeea.Classes.IconListItem;
import com.example.android.chefeea.Classes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Recipe recipe = getIntent().getParcelableExtra("recipe");

        TextView titleTextView = findViewById(R.id.recipe_title);
        ImageView dishImageView = findViewById(R.id.recipe_photo);
        TextView prepTimeTextView = findViewById(R.id.recipe_time);
        ListView ingredientsListView = findViewById(R.id.recipe_ingredients_list);
        ListView preparationListView = findViewById(R.id.recipe_preparation_list);

        if(recipe != null){
            String title = recipe.getRecipeTitle();
            int imageId = recipe.getRecipeImgResourceId();
            String prepTime = recipe.getRecipePrepTime();
            String[] ingredients = recipe.getRecipeIngredients();
            String[] preparation = recipe.getRecipePreparation();

            titleTextView.setText(title);
            dishImageView.setImageResource(imageId);
            prepTimeTextView.setText(prepTime);

            List<IconListItem> ingredientsList = new ArrayList<IconListItem>();
            for(int i = 0; i < ingredients.length; i++){
                if(i % 2 == 1){
                    ingredientsList.add(new IconListItem(ingredients[i]));
                }
                else{
                    ingredientsList.add(new IconListItem(ingredients[i], R.drawable.ic_menu_black_24dp));
                }
            }
            IconListItemAdapter ingredientsAdapter = new IconListItemAdapter(this,
                    R.layout.recipe_ingredients_list_item, ingredientsList);
            ingredientsListView.setAdapter(ingredientsAdapter);

            ArrayAdapter<String> preparationAdapter = new ArrayAdapter<String>(this,
                    R.layout.recipe_preparation_list_item, preparation);
            preparationListView.setAdapter(preparationAdapter);


        }


    }
}
