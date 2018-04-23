package com.example.eslam.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

public class AddingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static String LOG_TAG = AddingActivity.class.getSimpleName();

    private EditText mName;

    private EditText mPrice;

    private EditText mQuantity;

    private EditText mSuppName;

    private EditText mSuppPhone;

    private Uri mUri;

    private ContentValues values;

    private int Quantity;

    private final static int LOADER_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);
        mName = (EditText) findViewById(R.id.p_name);
        mPrice = (EditText) findViewById(R.id.p_price);
        mQuantity = (EditText) findViewById(R.id.p_quantity);
        mSuppName = (EditText) findViewById(R.id.supplier_name);
        mSuppPhone = (EditText) findViewById(R.id.supplier_phone);
        Button sell = (Button) findViewById(R.id.sell_btn);
        Button order = (Button) findViewById(R.id.order_btn);
        Intent retriveData = getIntent();
        mUri = retriveData.getData();
        if (mUri == null) {
            setTitle(R.string.title_add);
            sell.setVisibility(View.GONE);
            order.setVisibility(View.GONE);

        } else {
            setTitle(R.string.title_edit);
            getLoaderManager().initLoader(LOADER_NUMBER, null, this);
        }
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SuppPhone = mSuppPhone.getText().toString().trim();
                dialPhoneNumber(SuppPhone);
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sellProduct() {
        Quantity = Integer.parseInt(mQuantity.getText().toString().trim());
        values = new ContentValues();
        if (Quantity > 0) {
            Quantity--;
            values.put(StockEntry.COLUMN_STOCK_QUANTITY, Quantity);
            int rowUpdated = getContentResolver().update(mUri, values, null, null);
            if (rowUpdated != 0) {
                Toast.makeText(this, "sale ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error With selling", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "No Item In Stock", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveData() {
        try {
            String Name = mName.getText().toString().trim();
            int Price = Integer.parseInt(mPrice.getText().toString().trim());
            Quantity = Integer.parseInt(mQuantity.getText().toString().trim());
            String SuppName = mSuppName.getText().toString().trim();
            int SuppPhone = Integer.parseInt(mSuppPhone.getText().toString().trim());
            if (mUri == null &&
                    TextUtils.isEmpty(Name) &&
                    TextUtils.isEmpty(String.valueOf(Price)) &&
                    TextUtils.isEmpty(String.valueOf(Quantity)) &&
                    TextUtils.isEmpty(SuppName) &&
                    TextUtils.isEmpty(String.valueOf(SuppPhone))) {
                return;
            }
            if (Price <= 0) {
                Toast.makeText(this, "Please Set The Correct Price", Toast.LENGTH_SHORT).show();
                return;
            }
            values = new ContentValues();

            values.put(StockEntry.COLUMN_STOCK_NAME, Name);
            values.put(StockEntry.COLUMN_STOCK_PRICE, Price);
            values.put(StockEntry.COLUMN_STOCK_QUANTITY, Quantity);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_NAME, SuppName);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_PHONE, SuppPhone);
            if (mUri == null) {
                Uri newRowid = getContentResolver().insert(StockEntry.CONTENT_URI, values);
                if (newRowid != null) {
                    Toast.makeText(this, "Item Saved ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error With Saving Item", Toast.LENGTH_SHORT).show();

                }
            } else {
                int rowUpdated = getContentResolver().update(mUri, values, null, null);
                if (rowUpdated != 0) {
                    Toast.makeText(this, "Item Changed ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error With Changing Item", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Error In SaveData Method " + ex);
        }
    }

    private void deleteProduct() {
        if (mUri == null) {
            return;
        }
        int rowDeleted = getContentResolver().delete(mUri, null, null);
        if (rowDeleted != 0) {
            Toast.makeText(this, "Item is Deleted ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error With Deleted Item", Toast.LENGTH_SHORT).show();
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
        switch (id) {
            case R.id.done:
                SaveData();
                finish();
                return true;
            case R.id.delete:
                deleteProduct();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projection = {StockEntry._ID,
                StockEntry.COLUMN_STOCK_NAME,
                StockEntry.COLUMN_STOCK_PRICE,
                StockEntry.COLUMN_STOCK_QUANTITY,
                StockEntry.COLUMN_STOCK_SUPPLIER_NAME,
                StockEntry.COLUMN_STOCK_SUPPLIER_PHONE};
        return new CursorLoader(this, mUri, Projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            return;
        }
        if (data.moveToFirst()) {

            int nameColumnIndex = data.getColumnIndex(StockEntry.COLUMN_STOCK_NAME);
            int priceColumnIndex = data.getColumnIndex(StockEntry.COLUMN_STOCK_PRICE);
            int quantityColumnIndex = data.getColumnIndex(StockEntry.COLUMN_STOCK_QUANTITY);
            int suppColumnIndex = data.getColumnIndex(StockEntry.COLUMN_STOCK_SUPPLIER_NAME);
            int phoneColumnIndex = data.getColumnIndex(StockEntry.COLUMN_STOCK_SUPPLIER_PHONE);

            String name = data.getString(nameColumnIndex);
            int price = data.getInt(priceColumnIndex);
            int quantity = data.getInt(quantityColumnIndex);
            String suppName = data.getString(suppColumnIndex);
            int phone = data.getInt(phoneColumnIndex);


            mName.setText(name);
            mPrice.setText(String.valueOf(price));
            mQuantity.setText(String.valueOf(quantity));
            mSuppName.setText(suppName);
            mSuppPhone.setText(String.valueOf(phone));
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mName.setText("");
        mPrice.setText("");
        mQuantity.setText("");
        mSuppName.setText("");
        mSuppPhone.setText("");
    }
}