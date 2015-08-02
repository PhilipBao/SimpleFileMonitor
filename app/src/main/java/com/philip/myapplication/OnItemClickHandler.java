package com.philip.myapplication;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


public class OnItemClickHandler implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context = view.getContext();

        TextView textViewItem = ((TextView) view.findViewById(R.id.filePathText));
        String event = ((TextView) view.findViewById(R.id.eventLogText)).getText().toString();

        // get the clicked item name
        String listItemText = textViewItem.getText().toString();

        // get the clicked item ID
        String listItemId = textViewItem.getTag().toString();

        // just toast it
        Toast.makeText(context, "Item: " + listItemText + ", Item ID: " + listItemId, Toast.LENGTH_SHORT).show();

    }
}
