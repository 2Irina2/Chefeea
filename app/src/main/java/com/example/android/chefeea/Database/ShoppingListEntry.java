package com.example.android.chefeea.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shoppinglist")
public class ShoppingListEntry {

    @PrimaryKey(autoGenerate = true)
    private int entryId;
    private String entryName;
    private int quantity;

    @Ignore
    public ShoppingListEntry(String entryName, int quantity){
        this.entryName = entryName;
        this.quantity = quantity;
    }

    public ShoppingListEntry(int entryId, String entryName, int quantity){
        this.entryId = entryId;
        this.entryName = entryName;
        this.quantity = quantity;
    }

    public int getEntryId(){
        return entryId;
    }

    public String getEntryName(){
        return entryName;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setEntryId(int entryIdParam){
        entryId = entryIdParam;
    }

    public void setEntryName(String entryNameParam){
        entryName = entryNameParam;
    }

    public void setQuantity(int quantityParam){
        quantity = quantityParam;
    }
}
