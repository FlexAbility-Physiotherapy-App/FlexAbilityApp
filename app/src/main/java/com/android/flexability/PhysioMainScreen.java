package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PhysioMainScreen extends AppCompatActivity {

    private String json_response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physio_main_screen);

        // Code for R1
        // Get total number of physios and update the title accordingly
        updatePhysioCount();

        Button btnCreatePhysio = findViewById(R.id.btnCreatePhysioToChacge);
        btnCreatePhysio.setOnClickListener(view -> {
            Intent intent = new Intent(PhysioMainScreen.this, CreatePhysioActivity.class);
            startActivity(intent);
        });

        Button btnShowAllPhysios = findViewById(R.id.btnShowAllPhysios);
        btnShowAllPhysios.setOnClickListener(view -> {
            Intent intent = new Intent(PhysioMainScreen.this, DisplayPhysiosScreenActivity.class);
            startActivity(intent);
        });

        // Load 2 physios for the preview list
        int limitP = 2;
        loadPhysiosLimit(limitP);

        // Code for R2
        // Get total number of amenities and update the title accordingly
        updateAmenityCount();

        Button btnCreateAmenity = findViewById(R.id.btnCreateAmenity);
        btnCreateAmenity.setOnClickListener(view -> {
            Intent intent = new Intent(PhysioMainScreen.this, CreateAmenityActivity.class);
            startActivity(intent);
        });

        Button btnShowAllAmenities = findViewById(R.id.btnShowAllAmenities);
        btnShowAllAmenities.setOnClickListener(view -> {
            Intent intent = new Intent(PhysioMainScreen.this, DisplayAmenitiesScreenActivity.class);
            startActivity(intent);
        });

        // Load 2 amenities for the preview list
        int limit = 2;
        loadAmenitiesLimit(limit);
    }

    private void loadPhysiosLimit(int limitP) {
        LinearLayout cardsContainerP = findViewById(R.id.previewPhysioCardContainer);
        LayoutInflater inflaterP = LayoutInflater.from(this);

        try {
            json_response = new OkHttpHandler().getPhysios(limitP);
            System.out.println("HTTP Response received successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        cardsContainerP.removeAllViews();
        ArrayList<Physio> previewPhysios = PhysioCardAdapter.parseJson(json_response);
        for (Physio physio : previewPhysios) {
            View convertView = inflaterP.inflate(R.layout.physio_card_layout, cardsContainerP, false);
            PhysioCardAdapter.setCardData(physio, convertView, this);
            cardsContainerP.addView(convertView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update the total number of physios after returning to this screen
        updatePhysioCount();
        updateAmenityCount();

        // Load 2 physios for the preview list
        int limitP = 2;
        loadPhysiosLimit(limitP);

        // Load 2 amenities for the preview list
        int limit = 2;
        loadAmenitiesLimit(limit);
    }

    private void loadAmenitiesLimit(int limit) {
        LinearLayout cardsContainer = findViewById(R.id.previewCardsContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        try {
            json_response = new OkHttpHandler().getAmenities(limit);
            System.out.println("HTTP Response received successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        cardsContainer.removeAllViews();
        ArrayList<Amenity> previewAmenities = AmenityCardAdapter.parseJson(json_response);
        for (Amenity amenity: previewAmenities) {
            View convertView = inflater.inflate(R.layout.amenity_card_layout, cardsContainer, false);
            AmenityCardAdapter.setCardData(amenity, convertView, this);
            cardsContainer.addView(convertView);
        }
    }

    private void updatePhysioCount() {
        int totalPhysios = 0;
        try {
            totalPhysios = new OkHttpHandler().getPhysiosCount();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String physiosString = getResources().getString(R.string.physios, totalPhysios);
        TextView physiosTitle = findViewById(R.id.textViewPhysios);
        physiosTitle.setText(physiosString);
    }

    private void updateAmenityCount() {
        int totalAmenities = 0;
        try {
            totalAmenities = new OkHttpHandler().getAmenitiesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String amenitiesString = getResources().getString(R.string.amenities, totalAmenities);
        TextView amenitiesTitle = findViewById(R.id.txtViewAmenities);
        amenitiesTitle.setText(amenitiesString);

    }
}