package com.example.android.chefeea;

/**
 * Created by irina on 03.12.2017.
 */

public class DrawerItem {

    String itemName;
    int imgResId;

    public DrawerItem(String itemNameParam, int imgResIdParam){
        super();
        itemName = itemNameParam;
        imgResId = imgResIdParam;
    }

    public String getItemName(){
        return itemName;
    }

    public int getImgResId(){
        return imgResId;
    }

    public void setItemName(String ItemName){
        itemName = ItemName;
    }

    public void setImgResId(int ImgResId){
        imgResId = ImgResId;
    }



}
