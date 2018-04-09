package com.example.eslam.inventoryapp.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

/**
 * Created by Eslam on 4/8/2018.
 */

public class DbStockHelper extends SQLiteOpenHelper {
    private static String LOG_TAG = DbStockHelper.class.getSimpleName();
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "inventory.db";

    public DbStockHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + StockEntry.TABLE_NAME + " ("
                    + StockEntry.COLUMN_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + StockEntry.COLUMN_STOCK_NAME + " TEXT NOT NULL, "
                    + StockEntry.COLUMN_STOCK_PRICE + " INTEGER NOT NULL, "
                    + StockEntry.COLUMN_STOCK_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                    + StockEntry.COLUMN_STOCK_SUPPLIER_NAME + " TEXT NOT NULL,"
                    + StockEntry.COLUMN_STOCK_SUPPLIER_PHONE + " INTEGER NOT NULL);";

            db.execSQL(SQL_CREATE_PETS_TABLE);
        } catch (SQLException ex) {
            Log.e(LOG_TAG, "Error In Query " + ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
