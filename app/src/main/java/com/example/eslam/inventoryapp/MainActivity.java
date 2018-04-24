package com.example.eslam.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eslam.inventoryapp.data.InventoryContract.StockEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private FloatingActionButton fab;
    private ListView list;
    private TextView txt_empty_stock;
    private InventoryCursorAdapter mCursorAdapter;
    private final static int LOADER_NUMBER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_empty_stock = (TextView) findViewById(R.id.empty_stock);

        list = (ListView) findViewById(R.id.listview);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GoToAddingActivity = new Intent(MainActivity.this, AddingActivity.class);
                startActivity(GoToAddingActivity);
            }
        });

        list.setEmptyView(txt_empty_stock);

        mCursorAdapter = new InventoryCursorAdapter(this, null);

        list.setAdapter(mCursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent GoToaddingActivity = new Intent(MainActivity.this, AddingActivity.class);
                Uri uri = ContentUris.withAppendedId(StockEntry.CONTENT_URI, id);
                GoToaddingActivity.setData(uri);
                startActivity(GoToaddingActivity);

            }
        });

        getLoaderManager().initLoader(LOADER_NUMBER, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projection = {StockEntry._ID,
                StockEntry.COLUMN_STOCK_NAME,
                StockEntry.COLUMN_STOCK_PRICE,
                StockEntry.COLUMN_STOCK_QUANTITY};
        return new CursorLoader(this,
                StockEntry.CONTENT_URI,
                Projection, null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}