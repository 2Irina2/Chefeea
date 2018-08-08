package com.example.android.chefeea.Classes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.chefeea.Database.AppDatabase;
import com.example.android.chefeea.Database.RecipeEntry;
import com.example.android.chefeea.Database.ShoppingListEntry;

import java.util.List;

public class RecipesViewModel extends AndroidViewModel {

    private LiveData<List<RecipeEntry>> entries;

    public RecipesViewModel(@NonNull Application application) {
        super(application);
        entries = AppDatabase.getInstance(getApplication()).recipeEntryDao().loadAllRecipes();
    }

    public LiveData<List<RecipeEntry>> getEntries() {
        return entries;
    }
}
