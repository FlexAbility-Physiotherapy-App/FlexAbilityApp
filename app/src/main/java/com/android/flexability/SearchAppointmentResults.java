package com.android.flexability;

import android.content.Intent;
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
        int patient_id = intent.getIntExtra("id", -1);
        String day = intent.getStringExtra("day");
        String date = intent.getStringExtra("date");
        String hour = intent.getStringExtra("hour");
        String timestamp = date + " " + hour;
        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            availablePhysios = okHttpHandler.getAvailablePhysios(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayout container = findViewById(R.id.requestApointmentsLayout);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        Button backBtn = findViewById(R.id.backBtn);

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
            int physio_id = physio.getId();

            Button requestBtn = cardView.findViewById(R.id.sendRequestBtn);
            requestBtn.setOnClickListener(v -> {
                requestBtn.setBackgroundColor(ContextCompat.getColor(container.getContext(), R.color.titleDark));
                requestBtn.setText("Το αίτημα στάλθηκε!");
                requestBtn.setEnabled(false);
                requestBtn.setTextColor(ContextCompat.getColor(this, R.color.white));

                try {
                    OkHttpHandler okHttpHandler = new OkHttpHandler();
                    okHttpHandler.makeAppointmentRequest(patient_id, physio_id, timestamp);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            container.addView(cardView);
        }

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}