package com.example.android.chefeea.Classes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by irina on 09.12.2017.
 */

public class Recipe implements Parcelable{

    private String mTitle;
    private int mImgResourceId;
    private String mPrepTime;
    private String[] mIngredients;
    private String[] mPreparation;

    public Recipe(String title, int imgId, String prepTime, String[] ingredients, String[] preparation){
        mTitle = title;
        mImgResourceId = imgId;
        mPrepTime = prepTime;
        mIngredients = ingredients;
        mPreparation = preparation;
    }

    public String getRecipeTitle(){
        return mTitle;
    }

    public int getRecipeImgResourceId(){
        return mImgResourceId;
    }

    public String getRecipePrepTime(){
        return mPrepTime;
    }

    public String[] getRecipeIngredients(){
        return mIngredients;
    }

    public String[] getRecipePreparation(){
        return mPreparation;
    }


    protected Recipe(Parcel in) {
        mTitle = in.readString();
        mImgResourceId = in.readInt();
        mPrepTime = in.readString();
        mIngredients = in.createStringArray();
        mPreparation = in.createStringArray();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeInt(mImgResourceId);
        parcel.writeString(mPrepTime);
        parcel.writeStringArray(mIngredients);
        parcel.writeStringArray(mPreparation);
    }
}
