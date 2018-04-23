package com.example.eslam.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.eslam.inventoryapp.data.InventoryContract;

/**
 * Created by Eslam on 4/22/2018.
 */

public class InventoryCursorAdapter extends CursorAdapter {
    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String columnIndexName = cursor.getString(cursor.getColumnIndexOrThrow(InventoryContract.StockEntry.COLUMN_STOCK_NAME));
        int columnIndexPrice = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.StockEntry.COLUMN_STOCK_PRICE));
        int columnIndexQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(InventoryContract.StockEntry.COLUMN_STOCK_QUANTITY));
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView price = (TextView) view.findViewById(R.id.item_price);
        TextView quantity = (TextView) view.findViewById(R.id.item_quantity);
        name.setText(columnIndexName);
        price.setText(String.valueOf(columnIndexPrice));
        quantity.setText(String.valueOf(columnIndexQuantity));


    }
}
