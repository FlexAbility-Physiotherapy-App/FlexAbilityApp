package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

public class SearchAppointment extends AppCompatActivity {

    private Spinner daysSpinner;
    private Spinner hoursSpinner;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        daysSpinner = findViewById(R.id.daysSpinner);
        hoursSpinner = findViewById(R.id.hoursSpinner);
        searchBtn = findViewById(R.id.searchBtn);
    }
}