package com.example.eslam.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eslam.inventoryapp.data.DbStockHelper;
import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

public class AddingActivity extends AppCompatActivity {

    private static String LOG_TAG = AddingActivity.class.getSimpleName();

    private EditText mName;

    private EditText mPrice;

    private EditText mQuantity;

    private EditText mSuppName;

    private EditText mSuppPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        mName = (EditText) findViewById(R.id.p_name);
        mPrice = (EditText) findViewById(R.id.p_price);
        mQuantity = (EditText) findViewById(R.id.p_quantity);
        mSuppName = (EditText) findViewById(R.id.supplier_name);
        mSuppPhone = (EditText) findViewById(R.id.supplier_phone);
    }

    private void insertData() {
        try {

            DbStockHelper dbStockHelper = new DbStockHelper(this);

            SQLiteDatabase db = dbStockHelper.getWritableDatabase();

            ContentValues values = new ContentValues();

            String Name = mName.getText().toString().trim();
            int Price = Integer.parseInt(mPrice.getText().toString().trim());
            int Quantity = Integer.parseInt(mQuantity.getText().toString().trim());
            String SuppName = mSuppName.getText().toString().trim();
            int SuppPhone = Integer.parseInt(mSuppPhone.getText().toString().trim());

            values.put(StockEntry.COLUMN_STOCK_NAME, Name);
            values.put(StockEntry.COLUMN_STOCK_PRICE, Price);
            values.put(StockEntry.COLUMN_STOCK_QUANTITY, Quantity);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_NAME, SuppName);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_PHONE, SuppPhone);

            long newRowid = db.insert(StockEntry.TABLE_NAME, null, values);
            if (newRowid != -1) {
                Toast.makeText(this, "Item Is Added " + newRowid, Toast.LENGTH_SHORT).show();
                Log.v("addingactivity", "newRowId " + newRowid);
            } else {
                Toast.makeText(this, "Error With Saving Item", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception ex) {
            Log.e(LOG_TAG, "Error In Insert Method " + ex);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.done) {
            insertData();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}