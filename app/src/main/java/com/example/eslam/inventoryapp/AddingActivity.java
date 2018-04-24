package com.example.eslam.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
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

    private String QuantityString;

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
        Button order = (Button) findViewById(R.id.order_btn);
        Button add = (Button) findViewById(R.id.add_btn);
        Button decrease = (Button) findViewById(R.id.decrease_btn);
        Intent retriveData = getIntent();
        mUri = retriveData.getData();
        if (mUri == null) {
            setTitle(R.string.title_add);
            order.setVisibility(View.GONE);
            add.setVisibility(View.GONE);

        } else {
            setTitle(R.string.title_edit);
            getLoaderManager().initLoader(LOADER_NUMBER, null, this);
        }

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SuppPhone = mSuppPhone.getText().toString().trim();
                dialPhoneNumber(SuppPhone);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct();
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

    private void addProduct() {
        int quantity = 0;
        QuantityString = mQuantity.getText().toString().trim();
        if (!TextUtils.isEmpty(QuantityString)) {
            quantity = Integer.parseInt(QuantityString);
        }
        values = new ContentValues();
        quantity++;
        values.put(StockEntry.COLUMN_STOCK_QUANTITY, quantity);
        int rowUpdated = getContentResolver().update(mUri, values, null, null);
        if (rowUpdated != 0) {
            Toast.makeText(this, "increase The Quantity", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error With increasing", Toast.LENGTH_SHORT).show();
        }
    }

    private void sellProduct() {
        int quantity = 0;
        QuantityString = mQuantity.getText().toString().trim();
        if (!TextUtils.isEmpty(QuantityString)) {
            quantity = Integer.parseInt(QuantityString);
        }
        values = new ContentValues();
        if (quantity > 0) {
            quantity--;
            values.put(StockEntry.COLUMN_STOCK_QUANTITY, quantity);
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

    private void saveData() {
        try {
            String Name = mName.getText().toString().trim();
            String priceString = mPrice.getText().toString().trim();
            QuantityString = mQuantity.getText().toString().trim();
            String SuppName = mSuppName.getText().toString().trim();
            String suppPhoneString = mSuppPhone.getText().toString().trim();
            if (mUri == null &&
                    TextUtils.isEmpty(Name) ||
                    TextUtils.isEmpty(String.valueOf(priceString)) ||
                    TextUtils.isEmpty(String.valueOf(QuantityString)) ||
                    TextUtils.isEmpty(SuppName) ||
                    TextUtils.isEmpty(String.valueOf(suppPhoneString))) {
                Toast.makeText(this, "You Should Fill All Text ", Toast.LENGTH_SHORT).show();
                return;
            }
            int quantity = 0;
            if (!TextUtils.isEmpty(QuantityString)) {
                quantity = Integer.parseInt(QuantityString);
            }
            int price = 0;
            if (!TextUtils.isEmpty(priceString)) {
                price = Integer.parseInt(priceString);

            }
            int suppPhone = 0;
            if (!TextUtils.isEmpty(suppPhoneString)) {
                suppPhone = Integer.parseInt(suppPhoneString);

            }
            values = new ContentValues();
            values.put(StockEntry.COLUMN_STOCK_NAME, Name);
            values.put(StockEntry.COLUMN_STOCK_PRICE, price);
            values.put(StockEntry.COLUMN_STOCK_QUANTITY, quantity);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_NAME, SuppName);
            values.put(StockEntry.COLUMN_STOCK_SUPPLIER_PHONE, suppPhone);
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

    private void showConfirmedDelete() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.delete_dialog_msg);
        dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteProduct();
            }
        });
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
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
        finish();
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
                saveData();
                finish();
                return true;
            case R.id.delete:
                showConfirmedDelete();
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