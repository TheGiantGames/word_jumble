package com.thegiantgames.wordjumble;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class grid_adaptor extends BaseAdapter {

    private Context  mContext;
    GridView gridView ;
    ArrayList<String> marrayList;
    public grid_adaptor(Context context , ArrayList<String>  arrayList){
    mContext = context;
    marrayList = arrayList;
    }


    @Override
    public int getCount() {
        return marrayList.size() ;
    }

    @Override
    public Object getItem(int position) {
        return marrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        TextView textView;

        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_element_layout , null);
        }

        textView = (TextView) convertView.findViewById(R.id.gridElementTextview);
        textView.setText(marrayList.get(position));


        return convertView;
    }
}
