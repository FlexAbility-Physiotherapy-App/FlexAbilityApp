package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class PatientHistory extends AppCompatActivity {

    TextView nameLabel;
    TextView amkaLabel;
    TextView genderLabel;

    LinearLayout list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_history);

        nameLabel = findViewById(R.id.patientName);
        amkaLabel = findViewById(R.id.amka);;
        genderLabel = findViewById(R.id.gender);;
        list = findViewById(R.id.historyList);
        String amka = "";
        Intent intent = getIntent();

        amka = intent.getStringExtra("amka");

        // Loads all appointments of the day.
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
        ArrayList <Appointment> appointmentList = completedAppointmentsParser(new OkHttpHandler().getCompletedAppointments(amka));
        Log.d("testtt", amka);
        for (Appointment a : appointmentList){
            View appointmentHistory = inflater.inflate(R.layout.activity_patient_history_card, null);

            TextView dateLabel = appointmentHistory.findViewById(R.id.dateLabel);
            dateLabel.setText(a.getTimestamp().split(" ")[0]); // Displays date.

            EditText comment = appointmentHistory.findViewById(R.id.docNotes);
            comment.setText(a.getComment()); // Displays date.

            list.addView(appointmentHistory);
        }



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