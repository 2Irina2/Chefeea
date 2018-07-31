package com.example.android.chefeea.Classes;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSeekBar;

import com.example.android.chefeea.Database.AppDatabase;
import com.example.android.chefeea.Database.ShoppingListEntry;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<ShoppingListEntry>> entries;

    public MainViewModel(@NonNull Application application) {
        super(application);
        entries = AppDatabase.getInstance(getApplication()).shoppingListEntryDao().loadAllShoppingListItems();
    }

    public LiveData<List<ShoppingListEntry>> getEntries() {
        return entries;
    }
}
