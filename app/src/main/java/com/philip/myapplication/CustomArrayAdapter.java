package com.philip.myapplication;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.philip.myapplication.UI.LogListItem;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<LogListItem> {

    Context mContext;
    ArrayList <LogListItem> data = null;

    public CustomArrayAdapter(Context mContext, ArrayList<LogListItem> data) {
        super(mContext, -1, data);
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // inflate the layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_row_item, parent, false);


        // object item based on the position
        LogListItem objectItem = data.get(position);

        // get the TextView and then set the row's values
        TextView fileName = (TextView) rowView.findViewById(R.id.filePathText);
        TextView eventLog = (TextView) rowView.findViewById(R.id.eventLogText);
        fileName.setTag(objectItem.ID);
        fileName.setText(objectItem.getFile());
        eventLog.setText(objectItem.getEvent());

        return rowView;
    }
}