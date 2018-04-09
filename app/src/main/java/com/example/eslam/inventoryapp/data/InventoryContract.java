package com.example.eslam.inventoryapp.data;

import android.provider.BaseColumns;

/**
 * Created by Eslam on 4/8/2018.
 */

public final class InventoryContract {
    private InventoryContract() {
    }

    public abstract static class StockEntry implements BaseColumns {
        public final static String TABLE_NAME = "stock";
        public final static String COLUMN_STOCK_ID = BaseColumns._ID;
        public final static String COLUMN_STOCK_NAME = "Name";
        public final static String COLUMN_STOCK_PRICE = "Price";
        public final static String COLUMN_STOCK_QUANTITY = "Quantity";
        public final static String COLUMN_STOCK_SUPPLIER_NAME = "SupplierName";
        public final static String COLUMN_STOCK_SUPPLIER_PHONE = "SupplierPhone";
    }
}
