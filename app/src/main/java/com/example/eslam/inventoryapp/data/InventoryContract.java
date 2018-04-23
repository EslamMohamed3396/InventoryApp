package com.example.eslam.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Eslam on 4/8/2018.
 */

public final class InventoryContract {
    private InventoryContract() {
    }

    public static final String CONTENT_AUTHORTY = "com.example.android";
    public static final String PATH_STOCK = "stock";
    public static final Uri CONTENT_BASE_URI = Uri.parse("content://" + CONTENT_AUTHORTY);

    public abstract static class StockEntry implements BaseColumns {
        public final static String TABLE_NAME = "stock";

        public final static Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_BASE_URI, PATH_STOCK);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORTY + "/" + PATH_STOCK;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORTY + "/" + PATH_STOCK;

        public final static String COLUMN_STOCK_ID = BaseColumns._ID;
        public final static String COLUMN_STOCK_NAME = "Name";
        public final static String COLUMN_STOCK_PRICE = "Price";
        public final static String COLUMN_STOCK_QUANTITY = "Quantity";
        public final static String COLUMN_STOCK_SUPPLIER_NAME = "SupplierName";
        public final static String COLUMN_STOCK_SUPPLIER_PHONE = "SupplierPhone";
    }
}
