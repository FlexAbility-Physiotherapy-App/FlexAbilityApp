package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PatientHistory extends AppCompatActivity {

    TextView nameLabel;
    TextView amkaLabel;
    TextView genderLabel;
    TextView phoneLabel;
    Button backButton;
    LinearLayout list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        nameLabel = findViewById(R.id.name);
        amkaLabel = findViewById(R.id.amka);;
        genderLabel = findViewById(R.id.gender);;
        list = findViewById(R.id.historyList);
        phoneLabel = findViewById(R.id.phone);

        // Retrieve the amka value from the Intent
        Intent intent = getIntent();
        String amka = intent.getStringExtra("amka");

        // Create an OkHttpHandler object to fetch the JSON response
        OkHttpHandler okHttpHandler = new OkHttpHandler();

        // Run the getCompletedAppointments PHP code using the amka value
        String json = okHttpHandler.getCompletedAppointments(amka);

        // Parse the JSON response and retrieve the appointment list
        ArrayList<Appointment> appointmentList = completedAppointmentsParser(json);

        OkHttpHandler okHttpHandlerPatient = new OkHttpHandler();
        String jsonString = okHttpHandlerPatient.getPatientFromAMKA(amka);
        Log.d("JSON Response", jsonString);
        try {
            JSONObject patient = new JSONObject(jsonString);
            String name = patient.getString("name") + " " + patient.getString("surname");
            nameLabel.setText(name);
            String gender = patient.getString("sex");
            genderLabel.setText(gender);
            String phone = patient.getString("phone_number");
            phoneLabel.setText(phone);
            amkaLabel.setText(amka);



        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (Appointment a : appointmentList) {
            View appointmentHistory = inflater.inflate(R.layout.activity_patient_history_card, null);

            Log.d("Appointment List Size", String.valueOf(appointmentList.size()));
            TextView dateLabel = appointmentHistory.findViewById(R.id.dateLabel);
            dateLabel.setText(a.getTimestamp().split(" ")[0]); // Displays date.

            EditText comment = appointmentHistory.findViewById(R.id.docNotes);
            comment.setText(a.getComment()); // Displays comment.

            list.addView(appointmentHistory);

        }
        backButton = findViewById(R.id.backButtonToMain);
        backButton.setOnClickListener(v -> finish());

    }

    public static ArrayList<Appointment> completedAppointmentsParser(String json) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONArray appointmentsArray = jsonObject.getJSONArray("appointments");

            for (int i = 0; i < appointmentsArray.length(); i++) {
                JSONObject appointmentObject = appointmentsArray.getJSONObject(i);

                String name = appointmentObject.getString("name");
                String surname = appointmentObject.getString("surname");
                String amka = appointmentObject.getString("amka");
                String timestamp = appointmentObject.getString("timestamp");
                String comment = appointmentObject.getString("comment");

                int patientId = appointmentObject.getInt("patientId");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Appointment appointment = new Appointment(name, surname, amka, timestamp, patientId, comment);
                    Log.d("AppointmentTest", appointment.toString());
                    appointments.add(appointment);

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return appointments;
    }
}