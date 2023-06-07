package com.android.flexability;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhysioCardAdapter extends BaseAdapter {
    ArrayList<Physio> physios;
    LayoutInflater inflater;
    Context context;

    public PhysioCardAdapter(Context ctx, String response) {
        super();
        this.physios = parseJson(response);
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }

    // Function that will parse the json string and populate the physios arraylist
    public static ArrayList<Physio> parseJson(String response) {
        ArrayList<Physio> physios = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray physiosArray = jsonObject.getJSONArray("physios");

            for (int i = 0; i < physiosArray.length(); i++) {
                JSONObject physioObject = physiosArray.getJSONObject(i);

                int id = Integer.parseInt(physioObject.getString("id"));
                String name = physioObject.getString("name");
                String address = physioObject.getString("address");
                String phone = physioObject.getString("phone_number");
                String ssn = physioObject.getString("afm");
                Physio physio = new Physio(id, name, address, phone, ssn);

                physios.add(physio);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return physios;
    }

    @Override
    public int getCount() {
        return physios.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.physio_card_layout, null);
        }
        Physio physio = physios.get(position);
        setCardData(physio, convertView, this.context);
        return convertView;
    }

    public static void setCardData(Physio physio, View convertView, Context context) {
        // Set values to the card's text fields
        TextView name = convertView.findViewById(R.id.txtViewMassage);
        name.setText(physio.getName());
        TextView address = convertView.findViewById(R.id.txtViewAddress);
        address.setText(physio.getAddress());
        TextView phone = convertView.findViewById(R.id.txtViewPhone);
        phone.setText(physio.getPhone());
        TextView ssn = convertView.findViewById(R.id.txtViewSSN);
        ssn.setText(physio.getSSN());

        // Set the card's icons
        ImageView addressView = convertView.findViewById(R.id.imgAddress);
        addressView.setImageResource(R.drawable.map_pin);
        ImageView phoneView = convertView.findViewById(R.id.imgPhone);
        phoneView.setImageResource(R.drawable.phone);
        ImageView ssnView = convertView.findViewById(R.id.imgSSN);
        ssnView.setImageResource(R.drawable.receipt);
    }
}
