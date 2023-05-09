package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PhysioMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physio_main_screen);

        // Get total number of amenities and update the title accordingly
        int totalAmenities = 5; //TODO: Replace with actual number of amenities retrieved from database
        String amenitiesString = getResources().getString(R.string.amenities, totalAmenities);
        TextView amenitiesTitle = findViewById(R.id.txtViewAmenities);
        amenitiesTitle.setText(amenitiesString);
    }
}