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
        int limit = 2;
        LinearLayout cardsContainer = findViewById(R.id.previewPhysioCardContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        try {
            json_response = new OkHttpHandler().getPhysios(limit);
            System.out.println("HTTP Response received successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Physio> previewPhysios = PhysioCardAdapter.parseJson(json_response);
        for (Physio physio : previewPhysios) {
            View convertView = inflater.inflate(R.layout.physio_card_layout, cardsContainer, false);
            PhysioCardAdapter.setCardData(physio, convertView, this);
            cardsContainer.addView(convertView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Update the total number of physios after returning to this screen
        updatePhysioCount();
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
}