package com.example.android.chefeea.Classes;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by irina on 09.12.2017.
 */

public class Recipe implements Parcelable{

    private String mTitle;
    private String mImgUrl;
    private int mPrepTime;
    private List<String> mIngredients;
    private String mUrl;
    private List<String> mHealthLabels;


    public Recipe(String title, String imgId, int prepTime, List<String> ingredients, String url, List<String> labels){
        mTitle = title;
        mImgUrl = imgId;
        mPrepTime = prepTime;
        mIngredients = ingredients;
        mUrl = url;
        mHealthLabels = labels;
    }

    protected Recipe(Parcel in) {
        mTitle = in.readString();
        mImgUrl = in.readString();
        mPrepTime = in.readInt();
        mIngredients = in.createStringArrayList();
        mUrl = in.readString();
        mHealthLabels = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mImgUrl);
        dest.writeInt(mPrepTime);
        dest.writeStringList(mIngredients);
        dest.writeString(mUrl);
        dest.writeStringList(mHealthLabels);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getRecipeTitle(){
        return mTitle;
    }

    public String getRecipeUrl(){
        return mImgUrl;
    }

    public int getRecipePrepTime(){
        return mPrepTime;
    }

    public List<String> getRecipeIngredients(){
        return mIngredients;
    }

    public String getExtraUrl(){
        return mUrl;
    }

    public List<String> getRecipeHealthLabels() { return mHealthLabels;}
}
