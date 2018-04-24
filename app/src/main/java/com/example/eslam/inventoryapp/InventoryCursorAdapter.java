package com.example.eslam.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

/**
 * Created by Eslam on 4/22/2018.
 */

public class InventoryCursorAdapter extends CursorAdapter  {
    private int Quantitny;


    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        String columnIndexName = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_STOCK_NAME));
        int columnIndexPrice = cursor.getInt(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_STOCK_PRICE));
        int columnIndexQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_STOCK_QUANTITY));
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView price = (TextView) view.findViewById(R.id.item_price);
        final TextView quantity = (TextView) view.findViewById(R.id.item_quantity);
        name.setText(columnIndexName);
        price.setText(String.valueOf(columnIndexPrice));
        quantity.setText(String.valueOf(columnIndexQuantity));
        Quantitny=columnIndexQuantity;
        ImageView sell=(ImageView)view.findViewById(R.id.sell_btn);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quantitny>0)
                {
                Quantitny--;
                quantity.setText(String.valueOf(Quantitny));
                ContentValues values=new ContentValues();
                values.put(StockEntry.COLUMN_STOCK_QUANTITY, Quantitny);
                int rowUpdated = context.getContentResolver().update(StockEntry.CONTENT_URI, values, null, null);
                if (rowUpdated != 0) {
                    Toast.makeText(context, "sale ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error With selling", Toast.LENGTH_SHORT).show();
                }}
                else {
                    Toast.makeText(context, "No Item in Stock", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
