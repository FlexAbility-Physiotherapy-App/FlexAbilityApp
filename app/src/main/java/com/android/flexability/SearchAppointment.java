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
    private Button searchBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        daysSpinner = findViewById(R.id.daysSpinner);
        hoursSpinner = findViewById(R.id.hoursSpinner);
        searchBtn = findViewById(R.id.searchBtn);
        backBtn = findViewById(R.id.backBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String day = daysSpinner.getSelectedItem().toString();
                String hour = hoursSpinner.getSelectedItem().toString();
                Intent intent = new Intent(SearchAppointment.this, SearchAppointmentResults.class);
                intent.putExtra("day", day);
                intent.putExtra("hour", hour);
                startActivity(intent);
            }
        });

        //TO-DO: Add Intent for previous activity when backBtn is pressed
        //backBtn.setOnClickListener(new View.OnClickListener() {});
    }
}