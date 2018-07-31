package com.example.android.chefeea.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ShoppingListEntryDao {

    @Query("SELECT * FROM shoppinglist ORDER BY entryId")
    LiveData<List<ShoppingListEntry>> loadAllShoppingListItems();

    @Insert
    void insertEntry(ShoppingListEntry entry);

    @Delete
    void deleteEntry(ShoppingListEntry entry);

}
