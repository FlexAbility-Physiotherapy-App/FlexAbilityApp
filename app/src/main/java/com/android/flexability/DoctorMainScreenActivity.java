package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DoctorMainScreenActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    String json_response;
    int json_response1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_screen);

        int id = 8; //WILL CHANGE
        int limit = 3;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.US);
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        try {
            json_response = new OkHttpHandler().getAppointments(id, date, limit);
            System.out.println("HTTP Response received successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        CustomBasedAdapter customBasedAdapter1 = new CustomBasedAdapter(this, json_response, 1);
        listView = (ListView) findViewById(R.id.appointmentsList1);
        listView.setAdapter(customBasedAdapter1);
        Utility.setListViewHeightBasedOnChildren(listView);

        json_response1 = new OkHttpHandler().getAppointmentsCount(id, date);
        textView = (TextView) findViewById(R.id.counter1);
        textView.setText("("+ json_response1 +")");

        for(int i = 0; i < 7; i++){
            c.add(Calendar.DAY_OF_YEAR, 1);
            date = sdf.format(c.getTime());
            json_response1 = json_response1 + new OkHttpHandler().getAppointmentsCount(id, date);
        }

        textView = (TextView) findViewById(R.id.counter);
        textView.setText("("+ json_response1 +")");

        TextView changeScreenBtn = (TextView) findViewById(R.id.changeScreen);
        changeScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorMainScreenActivity.this, DoctorAppointmentsScreenActivity.class));
            }
        });
    }

}