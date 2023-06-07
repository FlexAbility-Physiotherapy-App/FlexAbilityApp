package com.android.flexability;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RequestedAppointments extends AppCompatActivity {
    LinearLayout reqAppointmentList;
    ImageFilterView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_appointments);


        // Retrieves userID.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null)
            return;
        int userID = bundle.getInt("id");

        reqAppointmentList = this.findViewById(R.id.reqAppointmentList);


        // For x amount of days ahead.
        for(int i = 0; i < DayConverter.values().length; i++){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);

            String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(c.getTime());
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(c.getTime());

            ImageView arrowImg = new ImageView(this);
            TextView dayLabel = new TextView(this);
            TextView dateLabel = new TextView(this);
            TextView counterLabel = new TextView(this);


            // Window density.
            float density = this.getResources().getDisplayMetrics().density;


            // arrowImg Parameters.
            arrowImg.setImageResource(R.drawable.angle_right_solid);
            arrowImg.setPadding((int) (7 * density), (int) (10 * density), (int) (7 * density), (int) (10 * density));


            // dayLabel Parameters.
            dayLabel.setText(DayConverter.convertToGreek(day));
            dayLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            dayLabel.setTextColor(getColor(R.color.titleDark));
            dayLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
            dayLabel.setPadding((int) (7 * density), (int) (10 * density), (int) (7 * density), (int) (10 * density));


            // dateLabel Parameters.
            dateLabel.setText(date);
            dateLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            dateLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_regular));
            dateLabel.setPadding((int) (7 * density), (int) (10 * density), (int) (7 * density), (int) (10 * density));


            // counterLabel Parameters.
            counterLabel.setText("(#)"); // Default value. Changes when appointments are loaded.
            counterLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            counterLabel.setTextColor(getColor(R.color.titleDark));
            counterLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
            counterLabel.setPadding((int) (7 * density), (int) (10 * density), (int) (7 * density), (int) (10 * density)
            );


            // LinearLayout list for requested appointments.
            LinearLayout list = new LinearLayout(this);
            list.setOrientation(LinearLayout.VERTICAL);
            list.setVisibility(View.GONE);


            // Loads all given appointment requests for the given day.
            ArrayList<Appointment> appointments = reqAppointmentsParser(new OkHttpHandler().getRequestedAppointments(userID, date));


            // Updates Counter value.
            counterLabel.setText("(" + appointments.size() + ")");


            // Loads all appointments of the day.
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for(Appointment a : appointments){
                View appointmentRequest = inflater.inflate(R.layout.activity_requested_appointment, null);

                Button rejectButton = appointmentRequest.findViewById(R.id.rejectButton);
                Button acceptButton = appointmentRequest.findViewById(R.id.acceptButton);

                rejectButton.setOnClickListener(v -> {
                    new OkHttpHandler().rejectAppointment(userID, a.getPatientId(), a.getTimestamp());
                    recreate();
                });

                acceptButton.setOnClickListener(v -> {
                    new OkHttpHandler().acceptAppointment(userID, a.getPatientId(), a.getTimestamp());
                    recreate();
                });

                TextView amkaTextView = appointmentRequest.findViewById(R.id.amkaTextView);
                amkaTextView.setText(a.getAmka()); // Updates AMKA.

                TextView nameTextView = appointmentRequest.findViewById(R.id.nameTextView);
                nameTextView.setText(a.getName_()); // Updates Name.

                TextView timeTxtView = appointmentRequest.findViewById(R.id.timeTextView);
                timeTxtView.setText(a.getTimestamp().split(" ")[1]); // Updates Time.

                list.addView(appointmentRequest);
            }


            // Container that holds date, counter and arrow down.
            LinearLayout listDataContainer = new LinearLayout(this);
            listDataContainer.setOrientation(LinearLayout.HORIZONTAL);
            listDataContainer.setGravity(Gravity.CENTER_VERTICAL);

            listDataContainer.addView(arrowImg);
            listDataContainer.addView(dayLabel);
            listDataContainer.addView(dateLabel);
            listDataContainer.addView(counterLabel);


            // Toggles list's visibility on click.
            listDataContainer.setOnClickListener(v -> {
                if(list.getVisibility() == View.VISIBLE) {
                    list.setVisibility(View.GONE);
                    arrowImg.setImageResource(R.drawable.angle_right_solid);
                }
                else {
                    list.setVisibility(View.VISIBLE);
                    arrowImg.setImageResource(R.drawable.angle_down_solid);
                }
            });


            // Holds date and all appointments.
            LinearLayout dayContainer = new LinearLayout(this);
            dayContainer.setOrientation(LinearLayout.VERTICAL);

            dayContainer.addView(listDataContainer);
            dayContainer.addView(list);

            reqAppointmentList.addView(dayContainer);
        }


        // Sets back button to return to the physios main screen.
        backButton = findViewById(R.id.backButtonView);
        backButton.setOnClickListener(v -> finish());
    }

    public static ArrayList<Appointment> reqAppointmentsParser(String json) {
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
                int patientId = appointmentObject.getInt("patientId");
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Appointment appointment = new Appointment(name, surname, amka, timestamp, patientId);
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