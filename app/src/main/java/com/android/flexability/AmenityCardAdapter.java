package com.android.flexability;

import android.content.Context;
import android.graphics.Color;
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

public class AmenityCardAdapter extends BaseAdapter {
    ArrayList<Amenity> amenities;
    LayoutInflater inflater;
    Context context;

    public AmenityCardAdapter(Context ctx, String response) {
        super();
        this.amenities = parseJson(response);
        this.inflater = LayoutInflater.from(ctx);
        this.context = ctx;
    }

    // Function that will parse the json string and populate the amenities arraylist
    // with the amenities that are available for the current room
    public static ArrayList<Amenity> parseJson(String response) {
        ArrayList<Amenity> amenities = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray amenitiesArray = jsonObject.getJSONArray("amenities");

            for (int i = 0; i < amenitiesArray.length(); i++) {
                JSONObject amenityObject = amenitiesArray.getJSONObject(i);

                String title = amenityObject.getString("title");
                String code = amenityObject.getString("code");
                int cost = amenityObject.getInt("cost");
                String description = amenityObject.getString("description");
                Amenity amenity = new Amenity(title, code, cost, description);

                amenities.add(amenity);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return amenities;
    }

    @Override
    public int getCount() {
        return amenities.size();
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
            convertView = inflater.inflate(R.layout.amenity_card_layout, null);

            Amenity amenity = amenities.get(position);
            setCardData(amenity, convertView, this.context);
        }
        return convertView;
    }

    public static void setCardData(Amenity amenity, View convertView, Context context) {
        // Set values to the card's text fields
        TextView title = (TextView) convertView.findViewById(R.id.txtAmenityTitle);
        title.setText(amenity.getTitle());
        TextView code = (TextView) convertView.findViewById(R.id.txtViewID);
        code.setText(amenity.getCode());
        TextView price = (TextView) convertView.findViewById(R.id.txtViewPrice);
        price.setText(context.getResources().getString(R.string.costPerSession, amenity.getPrice()));
        TextView description = (TextView) convertView.findViewById(R.id.txtViewDescription);
        description.setText(amenity.getDescription());

        // Set the carc's icons
        ImageView IDView = (ImageView) convertView.findViewById(R.id.imgID);
        IDView.setImageResource(R.drawable.hashtag);
        ImageView priceView = (ImageView) convertView.findViewById(R.id.imgPrice);
        priceView.setImageResource(R.drawable.euro);
        ImageView descriptionView = (ImageView) convertView.findViewById(R.id.imgDescription);
        descriptionView.setImageResource(R.drawable.info);

        // Set the expand button's icon
        Button expandButton = (Button) convertView.findViewById(R.id.expandButton);
        expandButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.angle_right_solid, 0, 0, 0);
        Drawable drawable = expandButton.getCompoundDrawables()[0];
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.white, null));
        expandButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (description.getMaxLines() == 3) {
                    description.setMaxLines(Integer.MAX_VALUE);
                    expandButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.angle_down_solid, 0, 0, 0);
                } else {
                    description.setMaxLines(3);
                    expandButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.angle_right_solid, 0, 0, 0);
                }
                Drawable drawable = expandButton.getCompoundDrawables()[0];
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, context.getResources().getColor(R.color.white, null));
                expandButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            }
        });
    }
}
