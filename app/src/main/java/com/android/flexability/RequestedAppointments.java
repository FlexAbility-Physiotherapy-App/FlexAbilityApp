package com.android.flexability;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RequestedAppointments extends AppCompatActivity {
    LinearLayout reqAppointmentList;

    int TEMP_PHYSIO_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_appointments);

        reqAppointmentList = this.findViewById(R.id.reqAppointmentList);

        // For x amount of days ahead.
        for(int i = 0; i < 7; i++){
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);

            String day = new SimpleDateFormat("EEEE").format(c.getTime());
            String date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

            ImageView arrowImg = new ImageView(this);
            TextView dayLabel = new TextView(this);
            TextView dateLabel = new TextView(this);
            TextView counterLabel = new TextView(this);


            // arrowImg Parameters.
            arrowImg.setImageResource(R.drawable.angle_right_solid);
            arrowImg.setPadding(
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density),
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density)
            );


            // dayLabel Parameters.
            dayLabel.setText(day);
            dayLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            dayLabel.setTextColor(ContextCompat.getColor(this, R.color.titleDark));
            dayLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
            dayLabel.setPadding(
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density),
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density)
            );


            // dateLabel Parameters.
            dateLabel.setText(date);
            dateLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//            dateLabel.setTextColor(getResources().getColor(R.color.titleDark));
            dateLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_regular));
            dateLabel.setPadding(
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density),
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density)
            );


            // counterLabel Parameters.
            counterLabel.setText("(#)"); // Default value. Changes when appointments are loaded.
            counterLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
            counterLabel.setTextColor(ContextCompat.getColor(this, R.color.titleDark));
            counterLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
            counterLabel.setPadding(
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density),
                    (int) (7 * this.getResources().getDisplayMetrics().density),
                    (int) (10 * this.getResources().getDisplayMetrics().density)
            );


            // LinearLayout list for requested appointments.
            LinearLayout list = new LinearLayout(this);
            list.setOrientation(LinearLayout.VERTICAL);
            list.setVisibility(View.GONE);


            // Loads all given appointment requests for the given day.
            ArrayList<Appointment> appointments = appointmentsParser(new OkHttpHandler().getRequestedAppointments(TEMP_PHYSIO_ID, date));


            // Updates Counter value.
            counterLabel.setText("(" + appointments.size() + ")");


            // Loads all appointments of the day.
            for(Appointment a : appointments){
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View appointmentRequest = inflater.inflate(R.layout.activity_requested_appointment, null);
                TextView txtView = appointmentRequest.findViewById(R.id.amkaTextView);
                txtView.setText(a.getAmka()); // Updates AMKA.

                txtView = appointmentRequest.findViewById(R.id.nameTextView);
                txtView.setText(a.getName_()); // Updates Name.

                txtView = appointmentRequest.findViewById(R.id.timeTextView);
                txtView.setText(a.getTimestamp().split(" ")[1]); // Updates Time.

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
    }

    private ArrayList<Appointment> appointmentsParser(String json) {
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