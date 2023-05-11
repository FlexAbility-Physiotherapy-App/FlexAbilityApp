package com.android.flexability;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomBasedAdapter extends BaseAdapter {

    Context context;
    String nameList[];
    String amkaList[];
    String timeList[];
    int type; //type 1 creates all 3 colors cards, while type 2 creates only black cards
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

        View row;
        if(position == 0 && type == 1) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_green, parent, false);

            Button btn = (Button) row.findViewById(R.id.button2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CurrentAppointmentScreenActivity.class);
                    context.startActivity(intent);
                }
            });
        }
        else if(position == 1 && type == 1){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_orange, parent, false);
        }
        else{
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.activity_custom_list_view_black, parent, false);
        }

        TextView txtView = (TextView)row.findViewById(R.id.textView9);
        txtView.setText(nameList[position]);
        txtView = (TextView)row.findViewById(R.id.textView11);
        txtView.setText(amkaList[position]);
        txtView = (TextView)row.findViewById(R.id.textView7);
        txtView.setText(timeList[position]);

        return row;
    }
}
