package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class DisplayPhysiosScreenActivity extends AppCompatActivity {

    private String json_response;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_physios_screen);

        Button btnBack = findViewById(R.id.backButton);
        btnBack.setOnClickListener(view -> finish());

        listView = findViewById(R.id.physiosList);
        try {
            json_response = new OkHttpHandler().getPhysios();
            System.out.println("HTTP Response received successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        PhysioCardAdapter adapter = new PhysioCardAdapter(getApplicationContext(), json_response);
        listView.setAdapter(adapter);
    }
}