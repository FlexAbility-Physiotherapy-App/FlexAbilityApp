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

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientMainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);

        Intent intent = getIntent();
        int patient_id = intent.getIntExtra("id", -1);

        Button searchAptBtn = findViewById(R.id.searchAptBtn);
        TextView seeAllApts = findViewById(R.id.seeAllApts);

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            String upcomingAppointment  = okHttpHandler.getUpcomingPatientAppointment(patient_id);
            ArrayList<String> aptDetails = new ArrayList<>();
            JSONArray aptDetailsJson = new JSONArray(upcomingAppointment);
            for (int i = 0; i < aptDetailsJson.length(); i++) {
                aptDetails.add(aptDetailsJson.getString(i));
            }
            if (aptDetails.size() != 0) {
                createUpcomingAptCard(aptDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            String patientAppointments  = okHttpHandler.getPatientAppointments(patient_id);
            Gson gson = new Gson();
            TypeToken<List<List<String>>> typeToken = new TypeToken<List<List<String>>>() {};
            List<List<String>> allApts = gson.fromJson(patientAppointments, typeToken.getType());

            TextView patientAptHeaderCount = findViewById(R.id.patientAptHeaderCount);
            patientAptHeaderCount.setText("(" + Integer.toString(allApts.size()) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

        seeAllApts.setOnClickListener(v -> {
            Intent seeAllAptsIntent = new Intent(PatientMainScreenActivity.this, PatientAllAppointments.class);
            seeAllAptsIntent.putExtra("pid", patient_id);
            startActivity(seeAllAptsIntent);
        });

        searchAptBtn.setOnClickListener(v -> {
            Intent searchIntent = new Intent(PatientMainScreenActivity.this, SearchAppointment.class);
            searchIntent.putExtra("pid", patient_id);
            startActivity(searchIntent);
        });
    }

    public void createUpcomingAptCard(ArrayList<String> aptDetails) {
        LinearLayout container = findViewById(R.id.patientNextAptMain);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cardView = inflater.inflate(R.layout.appointment_patient_card, container, false);

        TextView dayTextView = cardView.findViewById(R.id.dayText);
        TextView hourTextView = cardView.findViewById(R.id.hourText);
        TextView nameTextView = cardView.findViewById(R.id.nameText);
        TextView phoneTextView = cardView.findViewById(R.id.phoneText);

        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputDateFormat.parse(aptDetails.get(0));
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            dayTextView.setText(DayConverter.convertToGreek(outputDateFormat.format(date)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        hourTextView.setText(aptDetails.get(1).substring(0, 5));
        nameTextView.setText(aptDetails.get(2));
        phoneTextView.setText(aptDetails.get(3));

        container.addView(cardView);
    }
}