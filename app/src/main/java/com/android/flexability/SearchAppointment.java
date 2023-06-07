package com.android.flexability;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchAppointment extends AppCompatActivity {

    private Spinner daysSpinner;
    private Spinner hoursSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment);

        //Get android items
        daysSpinner = findViewById(R.id.daysSpinner);
        hoursSpinner = findViewById(R.id.hoursSpinner);
        Button searchBtn = findViewById(R.id.searchBtn);
        Button backBtn = findViewById(R.id.backBtn);

        //TO-DO: CHANGE PATIENT_ID WITH ACTUAL VALUE FROM DB.
        int patient_id = 3;

        class FullDate {
            String day;
            String date;

            public FullDate(String day, String date) {
                this.day = day;
                this.date = date;
            }

            public String getDay() {
                return day;
            }

            public String getDate() {
                return date;
            }
        }

        ArrayList<FullDate> daysSpinnerEntries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            String day = new SimpleDateFormat("EEEE").format(c.getTime());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

            day = DayConverter.convertToGreek(day);

            daysSpinnerEntries.add(new FullDate(day, date));
        }

        ArrayList<String> allDays = new ArrayList<>();
        for (FullDate d : daysSpinnerEntries) {
            allDays.add(d.getDay());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allDays);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daysSpinner.setAdapter(adapter);

        searchBtn.setOnClickListener(v -> {
            String day = daysSpinner.getSelectedItem().toString();
            String date = "";
            for (FullDate d : daysSpinnerEntries) {
                if (d.getDay().equals(day)) {
                    date = d.getDate();
                }
            }
            String hour = hoursSpinner.getSelectedItem().toString();
            hour += ":00";

            String timestamp = date + " " + hour;
            boolean isUsedTimestamp = false;
            try {
                OkHttpHandler okHttpHandler = new OkHttpHandler();
                isUsedTimestamp = okHttpHandler.alreadyUsedTimestamp(patient_id, timestamp);
                if (isUsedTimestamp) {
                    String toastMsg = "Έχετε κάνει ήδη προγραμματίσει ή κάνει αίτηση για ραντεβού για " + day + " στις " + hour.substring(0, 5) + "!";
                    Toast usedToast = Toast.makeText(this, toastMsg, Toast.LENGTH_LONG);
                    usedToast.show();
                } else {
                    Intent intent = new Intent(SearchAppointment.this, SearchAppointmentResults.class);
                    intent.putExtra("day", day);
                    intent.putExtra("date", date);
                    intent.putExtra("hour", hour);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //TO-DO: Add Intent for previous activity when backBtn is pressed
        //backBtn.setOnClickListener(new View.OnClickListener() {});
    }

    public void OnClickBackToMainPatient(View view) {
        finish();
    }

}