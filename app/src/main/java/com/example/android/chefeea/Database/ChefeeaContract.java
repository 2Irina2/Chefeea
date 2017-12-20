package com.example.android.chefeea.Database;

import android.provider.BaseColumns;

/**
 * Created by irina on 14.12.2017.
 */

public class ChefeeaContract {


    public static final class ShoppingListEntry implements BaseColumns{

        public static final String TABLE_NAME = "shoppinglist";

        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_QUANTITY = "quantity";
    }
}
