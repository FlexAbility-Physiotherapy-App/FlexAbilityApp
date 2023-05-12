package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayAmenitiesScreenActivity extends AppCompatActivity {

    String json_response;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_amenities_screen);

        Button btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.amenitiesList);
        try {
            json_response = new OkHttpHandler().getAmenities();
            System.out.println("HTTP Response received successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        AmenityCardAdapter adapter = new AmenityCardAdapter(getApplicationContext(), json_response);
        listView.setAdapter(adapter);
    }
}