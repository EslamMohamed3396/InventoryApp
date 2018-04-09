package com.example.eslam.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eslam.inventoryapp.data.DbStockHelper;
import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private ListView list;
    private TextView txt_empty_stock;
    private ArrayList<ItemDetails> itemDetails;
    private DbStockHelper dbStockHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToAddingActivity = new Intent(MainActivity.this, AddingActivity.class);
                startActivity(GoToAddingActivity);
            }
        });
        ReadeData();
    }

    @Override
    protected void onStart() {
        ReadeData();
        super.onStart();
    }

    private Cursor ReadeData() {
        dbStockHelper = new DbStockHelper(this);

        SQLiteDatabase db = dbStockHelper.getReadableDatabase();

        txt_empty_stock = (TextView) findViewById(R.id.empty_stock);

        String[] Projection = {StockEntry._ID,
                StockEntry.COLUMN_STOCK_NAME,
                StockEntry.COLUMN_STOCK_PRICE,
                StockEntry.COLUMN_STOCK_QUANTITY,
                StockEntry.COLUMN_STOCK_SUPPLIER_NAME,
                StockEntry.COLUMN_STOCK_SUPPLIER_PHONE};
        Cursor query = db.query(StockEntry.TABLE_NAME, Projection,
                null,
                null,
                null,
                null,
                null);
        int ColumnIndexId = query.getColumnIndex(StockEntry.COLUMN_STOCK_ID);
        int ColumnIndexName = query.getColumnIndex(StockEntry.COLUMN_STOCK_NAME);
        int ColumnIndexPrice = query.getColumnIndex(StockEntry.COLUMN_STOCK_PRICE);
        int ColumnIndexQuantity = query.getColumnIndex(StockEntry.COLUMN_STOCK_QUANTITY);
        int ColumnIndexSuppName = query.getColumnIndex(StockEntry.COLUMN_STOCK_SUPPLIER_NAME);
        int ColumnIndexSuppPhone = query.getColumnIndex(StockEntry.COLUMN_STOCK_SUPPLIER_PHONE);
        try {
            list = (ListView) findViewById(R.id.listview);
            list.setEmptyView(txt_empty_stock);
            itemDetails = new ArrayList<>();

            while (query.moveToNext()) {
                int Id = query.getInt(ColumnIndexId);
                String Name = query.getString(ColumnIndexName);
                int Price = query.getInt(ColumnIndexPrice);
                int Quantity = query.getInt(ColumnIndexQuantity);
                String SuppName = query.getString(ColumnIndexSuppName);
                int SuppPhone = query.getInt(ColumnIndexSuppPhone);

                itemDetails.add(new ItemDetails(Id , Name, Price, Quantity, SuppName, SuppPhone));
            }
            ListAdabter listAdabter = new ListAdabter(this, itemDetails);
            list.setAdapter(listAdabter);
        } catch (Exception ex) {
            Log.v("mainactivity", "Error In Read Method : " + ex);
        } finally {
            query.close();
        }

        return query;
    }
}