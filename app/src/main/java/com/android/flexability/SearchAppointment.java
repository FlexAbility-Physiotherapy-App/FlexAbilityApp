package com.android.flexability;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SearchAppointment extends AppCompatActivity {

    private Spinner daysSpinner;
    private Spinner hoursSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        daysSpinner = findViewById(R.id.daysSpinner);
        hoursSpinner = findViewById(R.id.hoursSpinner);
        Button searchBtn = findViewById(R.id.searchBtn);
        Button backBtn = findViewById(R.id.backBtn);

        searchBtn.setOnClickListener(v -> {
            String day = daysSpinner.getSelectedItem().toString();
            String hour = hoursSpinner.getSelectedItem().toString();
            Intent intent = new Intent(SearchAppointment.this, SearchAppointmentResults.class);
            intent.putExtra("day", day);
            intent.putExtra("hour", hour);
            startActivity(intent);
        });

        //TO-DO: Add Intent for previous activity when backBtn is pressed
        //backBtn.setOnClickListener(new View.OnClickListener() {});
    }
}