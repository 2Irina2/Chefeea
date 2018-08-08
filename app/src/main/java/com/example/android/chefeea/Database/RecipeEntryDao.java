package com.example.android.chefeea.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeEntryDao {

    @Query("SELECT * FROM recipes ORDER BY entryId")
    LiveData<List<RecipeEntry>> loadAllRecipes();

    @Insert
    void insertEntry(RecipeEntry entry);

    @Delete
    void deleteEntry(RecipeEntry entry);

}
