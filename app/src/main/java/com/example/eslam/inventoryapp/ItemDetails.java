package com.example.eslam.inventoryapp;

/**
 * Created by Eslam on 4/8/2018.
 */

public class ItemDetails {
    private int mItemId;
    private String mItemName;
    private int mItemPrice;
    private int mItemQuantity;
    private String mItemSupplierName;
    private int mItemSupplierPhone;

    public ItemDetails(int mItemId, String mItemName, int mItemPrice, int mItemQuantity, String mItemSupplierName, int mItemSupplierPhone) {
        this.mItemId = mItemId;
        this.mItemName = mItemName;
        this.mItemPrice = mItemPrice;
        this.mItemQuantity = mItemQuantity;
        this.mItemSupplierName = mItemSupplierName;
        this.mItemSupplierPhone = mItemSupplierPhone;
    }

    public int getmItemId() {
        return mItemId;
    }

    public String getmItemName() {
        return mItemName;
    }

    public int getmItemPrice() {
        return mItemPrice;
    }

    public int getmItemQuantity() {
        return mItemQuantity;
    }

    public String getmItemSupplierName() {
        return mItemSupplierName;
    }

    public int getmItemSupplierPhone() {
        return mItemSupplierPhone;
    }
}
