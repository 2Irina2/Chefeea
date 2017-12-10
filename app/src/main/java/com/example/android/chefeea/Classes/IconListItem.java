package com.example.android.chefeea.Classes;

/**
 * Created by irina on 09.12.2017.
 */

public class IconListItem {

    private String itemName;
    private int imgResId = 0;

    public IconListItem(String itemNameParam, int imgResIdParam){
        super();
        itemName = itemNameParam;
        imgResId = imgResIdParam;
    }

    public IconListItem(String itemNameParam){
        super();
        itemName = itemNameParam;
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
