package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class RequestedAppointments extends AppCompatActivity {
    // TODO Replace.
    ArrayList<String> amkaList = new ArrayList<>(Arrays.asList("18920365429", "01629354107", "01918273026"));
    ArrayList<String> nameList = new ArrayList<>(Arrays.asList("Μανος Ξεςποιοςμανος", "Ampa los", "Αλεξης τσιπρας"));
    ArrayList<String> timeList = new ArrayList<>(Arrays.asList("14:00-15:00", "15:00-16:00", "16:00-18:00"));

    LinearLayout reqAppointmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_appointments);

        reqAppointmentList = this.findViewById(R.id.reqAppointmentList);

        // For x amount of days ahead.
        for(int i = 0; i < 5; i++){
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
            dayLabel.setText("Ημερα " + i); // TODO Add call to DB.
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
            dateLabel.setText("00/00/000" + i); // TODO Add call to DB.
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
            counterLabel.setText("(" + i + ")"); // TODO Add call to DB.
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
            for(int position = 0; position < amkaList.size(); position++){ // TODO Add call to DB.
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View appointmentRequest = inflater.inflate(R.layout.activity_requested_appointment, null);

                TextView txtView = appointmentRequest.findViewById(R.id.amkaTextView);
                txtView.setText(amkaList.get(position)); // Updates AMKA.

                txtView = appointmentRequest.findViewById(R.id.nameTextView);
                txtView.setText(nameList.get(position)); // Updates Name.

                txtView = appointmentRequest.findViewById(R.id.timeTextView);
                txtView.setText(timeList.get(position)); // Updates Time.

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
}