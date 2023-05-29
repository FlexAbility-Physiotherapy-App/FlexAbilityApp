package com.android.flexability;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SearchAppointmentResults extends AppCompatActivity {

    private ArrayList<Physio> availablePhysios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment_results);

        Intent intent = getIntent();
        String day = intent.getStringExtra("day");
        String hour = intent.getStringExtra("hour");

        LinearLayout container = findViewById(R.id.requestsLayout);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        Button backBtn = findViewById(R.id.backBtn);

        //ONLY FOR TEST PURPOSES
        availablePhysios.add(new Physio(1, "Physio 1", "Phone 1"));
        availablePhysios.add(new Physio(2, "Physio 2", "Phone 2"));
        availablePhysios.add(new Physio(3, "Physio 3", "Phone 3"));
        availablePhysios.add(new Physio(4, "Physio 4", "Phone 4"));
        availablePhysios.add(new Physio(5, "Physio 5", "Phone 5"));
        availablePhysios.add(new Physio(6, "Physio 6", "Phone 6"));

        for (Physio physio : availablePhysios) {
            View cardView = inflater.inflate(R.layout.appointment_request_card, container, false);

            TextView dayTextView = cardView.findViewById(R.id.dayText);
            TextView hourTextView = cardView.findViewById(R.id.hourText);
            TextView nameTextView = cardView.findViewById(R.id.nameText);
            TextView phoneTextView = cardView.findViewById(R.id.phoneText);

            dayTextView.setText(day);
            hourTextView.setText(hour);
            nameTextView.setText(physio.getName());
            phoneTextView.setText(physio.getPhone());

            Button requestBtn = cardView.findViewById(R.id.sendRequestBtn);
            requestBtn.setOnClickListener(v -> {
                requestBtn.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.titleDark));
                requestBtn.setText("Το αίτημα στάλθηκε!");
                requestBtn.setEnabled(false);
                requestBtn.setTextColor(getResources().getColor(R.color.white));

            });

            container.addView(cardView);
        }

        backBtn.setOnClickListener(v -> {
            Intent intent1 = new Intent(SearchAppointmentResults.this, SearchAppointment.class);
            startActivity(intent1);
        });
    }
}