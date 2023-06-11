package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PatientFound extends AppCompatActivity {

    // Attribues
    Button btnAddPatient;
    Button btnBack;
    ConstraintLayout btnViewHistory;
    ImageButton btnSearchPatient;
    EditText inputAMKA;
    TextView nameText;
    TextView fathersNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_found);

        btnAddPatient = findViewById(R.id.addPat);
        btnBack = findViewById(R.id.backButtonToMain);
        btnSearchPatient = findViewById(R.id.searchPatientButton);
        inputAMKA = findViewById(R.id.amka_input);
        nameText = findViewById(R.id.patientName);
        fathersNameText = findViewById(R.id.patientFatherName);

        Intent prevIntent = getIntent();

        String prevAmka = prevIntent.getStringExtra("AMKA");
        String patientJson = prevIntent.getStringExtra("PATIENT_JSON");
        if(prevAmka != null){
            inputAMKA.setText(prevAmka);
        }


        try {
            JSONObject patient = new JSONObject(patientJson);
            String test = patient.getString("name");
            nameText.setText(test);
            nameText.setText(patient.getString("name"));
            fathersNameText.setText(patient.getString("surname"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // Assign Button Events
        btnBack.setOnClickListener(view -> {
            finish();
        });

        btnAddPatient.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateNewPatient.class));
        });

        createSearchButton();

        btnViewHistory = findViewById(R.id.btnCreateNewPhysio);
        btnViewHistory.setOnClickListener(v -> {
            Intent navToReqAppointments = new Intent(PatientFound.this, PatientHistory.class);
            navToReqAppointments.putExtra("amka", prevAmka);
            startActivity(navToReqAppointments);
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