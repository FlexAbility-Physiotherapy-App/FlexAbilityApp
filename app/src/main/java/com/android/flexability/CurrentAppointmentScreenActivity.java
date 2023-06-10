package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CurrentAppointmentScreenActivity extends AppCompatActivity {

    private ConstraintLayout btnBack;
    private EditText editTextComments;
    private ConstraintLayout btnComplete;
    private Spinner spinner;
    private ArrayList<Amenity> amenities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_appointment_screen);

        // Get data from the previous screen
        Intent intent = getIntent();
        int physio_id = intent.getIntExtra("physioId", -1);
        int patient_id = intent.getIntExtra("patientId", -1);
        String timestamp = intent.getStringExtra("timestamp");

        // Identify views
        btnBack = findViewById(R.id.btnBack);
        editTextComments = findViewById(R.id.editTextComments);
        btnComplete = findViewById(R.id.btnComplete);

        // Get amenities as a json format string from database and convert it with the parseJson()
        // method into an arraylist of amenities
        OkHttpHandler handler = new OkHttpHandler();
        amenities = parseJson(handler.getAmenities());

        // Create arraylist of strings containing provision name and price
        ArrayList<String> amenitiesStrings = new ArrayList<>();
        for (Amenity amenity: amenities) {
            amenitiesStrings.add(amenity.getTitle() + " - " + amenity.getPrice() + "€");
        }

        // Populate the spinner with data
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, amenitiesStrings);
        spinner.setAdapter(arrayAdapter);



        // Back button functionality
        btnBack.setOnClickListener(view -> {
            finish();
        });

        // Complete button functionality
        btnComplete.setOnClickListener(view -> {

            // Split spinner selection
            String provisionSelection = spinner.getSelectedItem().toString();
            String[] provisionNameAndPrice = provisionSelection.split(" - ");

            // Get provision id and price
            String code = null;
            double cost = -1;
            for (Amenity amenity: amenities) {
                if (amenity.getTitle().equals(provisionNameAndPrice[0])) {
                    code = amenity.getCode();
                    cost = amenity.getPrice();
                }
            }

            String comment = editTextComments.getText().toString();

            try {
                // Call completeAppointment method
                OkHttpHandler okHttpHandler = new OkHttpHandler();
                okHttpHandler.completeAppointment(physio_id, patient_id, timestamp, comment, code, cost);
                Toast.makeText(getApplicationContext(), "Το ραντεβού ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Return to doctor screen and refresh
            finish();
            Intent returnAndRefresh = new Intent(CurrentAppointmentScreenActivity.this, DoctorMainScreenActivity.class);
            returnAndRefresh.putExtra("id", physio_id);
            startActivity(returnAndRefresh);
        });
    }

    public static ArrayList<Amenity> parseJson(String response) {
        ArrayList<Amenity> amenities = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray amenitiesArray = jsonObject.getJSONArray("provisions");

            for (int i = 0; i < amenitiesArray.length(); i++) {
                JSONObject amenityObject = amenitiesArray.getJSONObject(i);

                String name = amenityObject.getString("name");
                String code = amenityObject.getString("code");
                int cost = amenityObject.getInt("cost");
                String description = amenityObject.getString("description");
                Amenity amenity = new Amenity(name, code, cost, description);

                amenities.add(amenity);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return amenities;
    }
}