package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhysioMainScreen extends AppCompatActivity {

    String json_response;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physio_main_screen);

        // Code for R2
        // Get total number of amenities and update the title accordingly
        int totalAmenities = 5; //TODO: Replace with actual number of amenities retrieved from database
        String amenitiesString = getResources().getString(R.string.amenities, totalAmenities);
        TextView amenitiesTitle = findViewById(R.id.txtViewAmenities);
        amenitiesTitle.setText(amenitiesString);

        Button btnCreateAmenity = findViewById(R.id.btnCreateAmenity);
        btnCreateAmenity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysioMainScreen.this, CreateAmenityActivity.class);
                startActivity(intent);
            }
        });

        Button btnShowAllAmenities = findViewById(R.id.btnShowAllAmenities);
        btnShowAllAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhysioMainScreen.this, DisplayAmenitiesScreenActivity.class);
                startActivity(intent);
            }
        });

        // Load 2 amenities for the preview list
        String url = "example.com"; // TODO: Replace with the API URL
        LinearLayout cardsContainer = findViewById(R.id.previewCardsContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        try {
            json_response = new OkHttpHandler().getAmenitiesJSON(url);
            // TODO: Remove the following json_response. Used for demonstration purposes only to return 2 results
            json_response = "{\n" +
                "  \"amenities\": [\n" +
                "    {\n" +
                "      \"title\": \"Swimming Pool\",\n" +
                "      \"code\": \"SWIM-001\",\n" +
                "      \"cost\": 10,\n" +
                "      \"description\": \"Enjoy a refreshing swim in our luxurious pool. Enjoy a refreshing swim in our luxurious pool. Enjoy a refreshing swim in our luxurious pool.\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"Gym Access\",\n" +
                "      \"code\": \"GYM-002\",\n" +
                "      \"cost\": 15,\n" +
                "      \"description\": \"Stay fit and healthy with our fully equipped gym. Stay fit and healthy with our fully equipped gym. Stay fit and healthy with our fully equipped gym.\"\n" +
                "    },\n" +
                "  ]\n" +
                "};";

            System.out.println("HTTP Response received successfully");
            // TODO: Remove toast
            Toast.makeText(getApplicationContext(), "Preview amenities loaded successfully",
                    Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Amenity> previewAmenities = AmenityCardAdapter.parseJson(json_response);
        for (Amenity amenity: previewAmenities) {
            View convertView = inflater.inflate(R.layout.amenity_card_layout, cardsContainer, false);
            AmenityCardAdapter.setCardData(amenity, convertView, this);
            cardsContainer.addView(convertView);
        }
    }
}