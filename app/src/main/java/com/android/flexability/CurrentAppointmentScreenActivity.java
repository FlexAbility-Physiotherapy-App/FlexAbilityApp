package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class CurrentAppointmentScreenActivity extends AppCompatActivity {

    private ConstraintLayout btnBack;
    private EditText editTextComments;
    private ConstraintLayout btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_appointment_screen);

        Intent intent = getIntent();
        int physio_id = Integer.parseInt(intent.getStringExtra("physioId"));
        int patient_id = Integer.parseInt(intent.getStringExtra("patientId"));
        String timestamp = intent.getStringExtra("timestamp");

        btnBack = findViewById(R.id.btnBack);
        editTextComments = findViewById(R.id.editTextComments);
        btnComplete = findViewById(R.id.btnComplete);

        btnBack.setOnClickListener(view -> finish());

        btnComplete.setOnClickListener(view -> {

            String comment = editTextComments.getText().toString();

            try {
                OkHttpHandler okHttpHandler = new OkHttpHandler();
                okHttpHandler.completeAppointment(physio_id, patient_id, timestamp, comment);
                Toast.makeText(getApplicationContext(), "Το ραντεβού ολοκληρώθηκε", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
            startActivity(new Intent(CurrentAppointmentScreenActivity.this, DoctorMainScreenActivity.class));
        });
    }
}