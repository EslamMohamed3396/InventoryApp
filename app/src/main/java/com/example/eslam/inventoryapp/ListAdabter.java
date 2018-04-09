package com.example.eslam.inventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eslam on 4/8/2018.
 */

public class ListAdabter extends ArrayAdapter<ItemDetails> {
    public ListAdabter(Context context, ArrayList<ItemDetails> item) {
        super(context, 0, item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDetails itemDetails = getItem(position);
        View root = convertView;
        if (root == null) {
            root = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView itemId = (TextView) root.findViewById(R.id.item_id);
        itemId.setText(String.valueOf(itemDetails.getmItemId()));

        TextView itemName = (TextView) root.findViewById(R.id.item_name);
        itemName.setText(itemDetails.getmItemName());

        TextView itemPrice = (TextView) root.findViewById(R.id.item_price);
        itemPrice.setText(String.valueOf(itemDetails.getmItemPrice()) + " $ ");

        TextView itemQuantity = (TextView) root.findViewById(R.id.item_quantity);
        itemQuantity.setText(String.valueOf(itemDetails.getmItemQuantity()));

        TextView SuppName = (TextView) root.findViewById(R.id.supp_name);
        SuppName.setText(itemDetails.getmItemSupplierName());

        TextView SuppPhone = (TextView) root.findViewById(R.id.supp_phone);
        SuppPhone.setText(String.valueOf(itemDetails.getmItemSupplierPhone()));

        return root;
    }
}
