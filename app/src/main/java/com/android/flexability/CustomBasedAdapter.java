package com.android.flexability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBasedAdapter extends BaseAdapter {

    Context context;
    String nameList[];
    String amkaList[];
    String timeList[];
    int type;
    LayoutInflater inflater;

    public CustomBasedAdapter(Context ctx, String nameList[], String amkaList[], String timeList[], int type){
        this.context = ctx;
        this.nameList = nameList;
        this.amkaList = amkaList;
        this.timeList = timeList;
        this.type = type;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return amkaList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0 && type == 1){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_green, null);
        }
        else if(position == 1 && type == 1){
            convertView = inflater.inflate(R.layout.activity_custom_list_view_orange, null);
        }
        else{
            convertView = inflater.inflate(R.layout.activity_custom_list_view_black, null);
        }
        TextView txtView = (TextView)convertView.findViewById(R.id.textView9);
        txtView.setText(nameList[position]);
        txtView = (TextView)convertView.findViewById(R.id.textView11);
        txtView.setText(amkaList[position]);
        txtView = (TextView)convertView.findViewById(R.id.textView7);
        txtView.setText(timeList[position]);

        return convertView;
    }
}
