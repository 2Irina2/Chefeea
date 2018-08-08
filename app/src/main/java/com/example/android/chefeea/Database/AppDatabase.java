package com.example.android.chefeea.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.example.android.chefeea.Classes.IngredientListTypeConverter;

/*
* Inspiration:
* https://medium.com/google-developers/understanding-migrations-with-room-f01e04b07929
* */

@Database(entities = {ShoppingListEntry.class, RecipeEntry.class}, version = 2, exportSchema = false)
@TypeConverters(IngredientListTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "appdatabase";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }

        Log.d(LOG_TAG, "Getting the datapase instance");
        return sInstance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS 'recipes' (" +
                    "'entryId' INTEGER PRIMARY KEY NOT NULL , " +
                    "'entryTitle' TEXT , " +
                    "'entryImgUrl'TEXT, " +
                    "'entryPrepTime' INTEGER NOT NULL, " +
                    "'entryIngredients' TEXT , " +
                    "'entryUrl' TEXT " +
                    ")");
        }
    };

    public abstract ShoppingListEntryDao shoppingListEntryDao();
    public abstract RecipeEntryDao recipeEntryDao();

}
