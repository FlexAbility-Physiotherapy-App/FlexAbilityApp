package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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
        updateAmenityCount();

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
        int limit = 2;
        LinearLayout cardsContainer = findViewById(R.id.previewCardsContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        try {
            json_response = new OkHttpHandler().getAmenities(limit);
            System.out.println("HTTP Response received successfully");
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

    @Override
    protected void onResume() {
        super.onResume();

        // Update the total number of amenities after returning to this screen
        updateAmenityCount();
    }

    private void updateAmenityCount() {
        int totalAmenities = 0;
        try {
            totalAmenities = new OkHttpHandler().getAmenitiesCount();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String amenitiesString = getResources().getString(R.string.amenities, totalAmenities);
        TextView amenitiesTitle = findViewById(R.id.txtViewAmenities);
        amenitiesTitle.setText(amenitiesString);
    }
}