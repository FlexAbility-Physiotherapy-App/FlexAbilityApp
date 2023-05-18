package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PatientNotFound extends AppCompatActivity {


    // Attribues
    Button btnAddPatient;
    Button btnBack;
    ImageButton btnSearchPatient;
    EditText inputAMKA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_not_found);

        btnAddPatient = findViewById(R.id.addPat);
        btnBack = findViewById(R.id.backButtonToMain);
        btnSearchPatient = findViewById(R.id.searchPatientButton);
        inputAMKA = findViewById(R.id.amka_input);

        Intent prevIntent = getIntent();
        String prevAmka = prevIntent.getStringExtra("AMKA");

        if(prevAmka != null){
            inputAMKA.setText(prevAmka);
        }

        // Assign Button Events
        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(this, DoctorMainScreenActivity.class));
        });

        btnAddPatient.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateNewPatient.class));
        });

        createSearchButton();


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