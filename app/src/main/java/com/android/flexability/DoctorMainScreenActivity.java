package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

        // Loads requested appointments.
        loadRequestedAppointments();

        int id = -1;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            id = bundle.getInt("id");
        }


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
        int finalId = id;
        changeScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorMainScreenActivity.this, DoctorAppointmentsScreenActivity.class);
                intent.putExtra("id", finalId);
                startActivity(intent);
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

    private void loadRequestedAppointments(){
        int TEMP_PHYSIO_ID = 2; // TODO Remove.
        LinearLayout reqAppointmentsLayout = findViewById(R.id.reqAppointmentsLayout);

        // Appointments Header.
        LinearLayout listDataContainer = new LinearLayout(this);
        listDataContainer.setOrientation(LinearLayout.HORIZONTAL);
        listDataContainer.setGravity(Gravity.CENTER_VERTICAL);

        TextView appointmentsLabel = new TextView(this);
        TextView counterLabel = new TextView(this);
        TextView viewAllLabel = new TextView(this);


        // AppointmentsLabel Parameters.
        appointmentsLabel.setText("Αιτήματα"); // TODO Replace with static string.
        appointmentsLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        appointmentsLabel.setTextColor(ContextCompat.getColor(this, R.color.titleDark));
        appointmentsLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
        appointmentsLabel.setPadding(
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


        // viewAllLabel Parameters.
        viewAllLabel.setText(R.string.showAll);
        viewAllLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        viewAllLabel.setLayoutParams(new ViewGroup.LayoutParams( // Set to position the TextView to the right of the LinearLayout.
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        viewAllLabel.setGravity(Gravity.END);
        viewAllLabel.setTypeface(ResourcesCompat.getFont(this, R.font.manrope_bold));
        viewAllLabel.setTextColor(ContextCompat.getColor(this, R.color.greenButton));
        viewAllLabel.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewAllLabel.setPadding(
                (int) (7 * this.getResources().getDisplayMetrics().density),
                (int) (10 * this.getResources().getDisplayMetrics().density),
                (int) (7 * this.getResources().getDisplayMetrics().density),
                (int) (10 * this.getResources().getDisplayMetrics().density)
        );

        viewAllLabel.setOnClickListener(v -> startActivity(
                new Intent(DoctorMainScreenActivity.this, RequestedAppointments.class)
        ));


        listDataContainer.addView(appointmentsLabel);
        listDataContainer.addView(counterLabel);
        listDataContainer.addView(viewAllLabel);


        // Loads all appointments.
        ArrayList<Appointment> appointments = RequestedAppointments.reqAppointmentsParser(new OkHttpHandler().getAllRequestedAppointments(TEMP_PHYSIO_ID));

        // Stop execution if no appointment is found.
        if(appointments.size() == 0)
            return;


        // Updates Counter value.
        counterLabel.setText("(" + appointments.size() + ")");


        // Gets first appointment.
        Appointment a = appointments.get(0);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View appointmentRequest = inflater.inflate(R.layout.activity_requested_appointment, null);

        Button rejectButton = appointmentRequest.findViewById(R.id.rejectButton);
        Button acceptButton = appointmentRequest.findViewById(R.id.acceptButton);

        rejectButton.setOnClickListener(v -> {
            new OkHttpHandler().rejectAppointment(TEMP_PHYSIO_ID, a.getPatientId(), a.getTimestamp()); // TODO Replace TEMP_PHYSIO_ID
            recreate();
        });

        acceptButton.setOnClickListener(v -> {
            new OkHttpHandler().acceptAppointment(TEMP_PHYSIO_ID, a.getPatientId(), a.getTimestamp()); // TODO Replace TEMP_PHYSIO_ID
            recreate();
        });

        TextView txtView = appointmentRequest.findViewById(R.id.amkaTextView);
        txtView.setText(a.getAmka()); // Updates AMKA.

        txtView = appointmentRequest.findViewById(R.id.nameTextView);
        txtView.setText(a.getName_()); // Updates Name.

        txtView = appointmentRequest.findViewById(R.id.timeTextView);
        txtView.setText(a.getTimestamp().split(" ")[1]); // Updates Time.


        reqAppointmentsLayout.addView(listDataContainer);
        reqAppointmentsLayout.addView(appointmentRequest);
    }
}