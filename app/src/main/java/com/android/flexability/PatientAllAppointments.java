package com.android.flexability;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientAllAppointments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_all_appointments);

        Intent intent = getIntent();
        int patient_id = intent.getIntExtra("pid", -1);

        Button backBtn = findViewById(R.id.backBtn);

        LinearLayout container = findViewById(R.id.allApointmentsLayout);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            String patientAppointments  = okHttpHandler.getPatientAppointments(patient_id);
            Gson gson = new Gson();

            TypeToken<List<List<String>>> typeToken = new TypeToken<List<List<String>>>() {};
            List<List<String>> allApts = gson.fromJson(patientAppointments, typeToken.getType());

            for (List<String> appointment: allApts) {
                View cardView = inflater.inflate(R.layout.appointment_patient_card, container, false);

                TextView dayTextView = cardView.findViewById(R.id.dayText);
                TextView hourTextView = cardView.findViewById(R.id.hourText);
                TextView nameTextView = cardView.findViewById(R.id.nameText);
                TextView phoneTextView = cardView.findViewById(R.id.phoneText);

                try {
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = inputDateFormat.parse(appointment.get(0));
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                    dayTextView.setText(DayConverter.convertToGreek(outputDateFormat.format(date)));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                hourTextView.setText(appointment.get(1).substring(0, 5));
                nameTextView.setText(appointment.get(2));
                phoneTextView.setText(appointment.get(3));

                container.addView(cardView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }
}