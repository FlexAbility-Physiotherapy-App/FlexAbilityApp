package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    ImageButton btnSearchPatient;
    EditText inputAMKA;
    Button btnAddPatient;

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
        CustomBasedAdapter customBasedAdapter1 = new CustomBasedAdapter(this, json_response, 1, id);
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

        btnSearchPatient = findViewById(R.id.searchPatientButton);
        inputAMKA = findViewById(R.id.amka_input);
        createSearchButton();

        btnAddPatient = findViewById(R.id.addPat);
        btnAddPatient.setOnClickListener(view -> {
            startActivity(new Intent(DoctorMainScreenActivity.this, CreateNewPatient.class));
        });

    }

    private void createSearchButton(){

        btnSearchPatient.setOnClickListener(view -> {
            String amka = inputAMKA.getText().toString();

            if(amka.length() > 0){
                try{
                    OkHttpHandler okHttpHandler = new OkHttpHandler();
                    String jsonString = okHttpHandler.getPatientFromAMKA(amka);
                    Intent newIntent;
                    if(jsonString == ""){
                        newIntent = new Intent(this, PatientNotFound.class);
                        newIntent.putExtra("AMKA", amka);
                        finish();
                        startActivity(newIntent);
                    }
                    else{
                        newIntent = new Intent(this, PatientFound.class);
                        newIntent.putExtra("AMKA", amka);
                        newIntent.putExtra("PATIENT_JSON", jsonString);
                        finish();
                        startActivity(newIntent);
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "AMKA is empty",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }



}