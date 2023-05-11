package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PhysioMainScreen extends AppCompatActivity {

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
    }
}