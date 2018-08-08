package com.example.android.chefeea.Database;

import android.os.Parcel;
import android.os.Parcelable;
import android.arch.persistence.room.*;

import java.util.List;

/**
 * Created by irina on 09.12.2017.
 */
@Entity(tableName = "recipes")
public class RecipeEntry implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entryId")
    private int entryId;
    @ColumnInfo(name = "entryTitle")
    private String entryTitle;
    @ColumnInfo(name = "entryImgUrl")
    private String entryImgUrl;
    @ColumnInfo(name = "entryPrepTime")
    private int entryPrepTime;
    @ColumnInfo(name = "entryIngredients")
    private List<String> entryIngredients;
    @ColumnInfo(name = "entryUrl")
    private String entryUrl;

    public RecipeEntry(int entryId, String entryTitle, String entryImgUrl, int entryPrepTime, List<String> entryIngredients, String entryUrl){
        this.entryId = entryId;
        this.entryTitle = entryTitle;
        this.entryImgUrl = entryImgUrl;
        this.entryPrepTime = entryPrepTime;
        this.entryIngredients = entryIngredients;
        this.entryUrl = entryUrl;
    }

    @Ignore
    public RecipeEntry(String entryTitle, String entryImgUrl, int entryPrepTime, List<String> entryIngredients, String entryUrl){
        this.entryTitle = entryTitle;
        this.entryImgUrl = entryImgUrl;
        this.entryPrepTime = entryPrepTime;
        this.entryIngredients = entryIngredients;
        this.entryUrl = entryUrl;
    }

    protected RecipeEntry(Parcel in) {
        entryTitle = in.readString();
        entryImgUrl = in.readString();
        entryPrepTime = in.readInt();
        entryIngredients = in.createStringArrayList();
        entryUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entryTitle);
        dest.writeString(entryImgUrl);
        dest.writeInt(entryPrepTime);
        dest.writeStringList(entryIngredients);
        dest.writeString(entryUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeEntry> CREATOR = new Creator<RecipeEntry>() {
        @Override
        public RecipeEntry createFromParcel(Parcel in) {
            return new RecipeEntry(in);
        }

        @Override
        public RecipeEntry[] newArray(int size) {
            return new RecipeEntry[size];
        }
    };

    public int getEntryId(){
        return entryId;
    }

    public String getEntryTitle(){
        return entryTitle;
    }

    public String getEntryImgUrl(){
        return entryImgUrl;
    }

    public int getEntryPrepTime(){
        return entryPrepTime;
    }

    public List<String> getEntryIngredients(){
        return entryIngredients;
    }

    public String getEntryUrl(){
        return entryUrl;
    }
}
