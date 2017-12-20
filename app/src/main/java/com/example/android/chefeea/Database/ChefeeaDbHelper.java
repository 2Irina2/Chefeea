package com.example.android.chefeea.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.chefeea.Database.ChefeeaContract.ShoppingListEntry;

/**
 * Created by irina on 14.12.2017.
 */

public class ChefeeaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "chefeea.db";

    private static final int DATABASE_VERSION = 1;

    public ChefeeaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE " +
                ShoppingListEntry.TABLE_NAME + " (" +
                ShoppingListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ShoppingListEntry.COLUMN_INGREDIENT + " TEXT NOT NULL, " +
                ShoppingListEntry.COLUMN_QUANTITY + " INTEGER NOT NULL" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_SHOPPING_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ShoppingListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
