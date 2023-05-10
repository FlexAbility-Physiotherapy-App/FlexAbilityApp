package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class DisplayAmenitiesScreenActivity extends AppCompatActivity {

    String json_response = "{\n" +
            "  \"amenities\": [\n" +
            "    {\n" +
            "      \"title\": \"Swimming Pool\",\n" +
            "      \"code\": \"SWIM-001\",\n" +
            "      \"cost\": 10,\n" +
            "      \"description\": \"Enjoy a refreshing swim in our luxurious pool. Enjoy a refreshing swim in our luxurious pool. Enjoy a refreshing swim in our luxurious pool.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\": \"Gym Access\",\n" +
            "      \"code\": \"GYM-002\",\n" +
            "      \"cost\": 15,\n" +
            "      \"description\": \"Stay fit and healthy with our fully equipped gym. Stay fit and healthy with our fully equipped gym. Stay fit and healthy with our fully equipped gym.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"title\": \"Spa Treatment\",\n" +
            "      \"code\": \"SPA-003\",\n" +
            "      \"cost\": 25,\n" +
            "      \"description\": \"Indulge in a relaxing spa session and rejuvenate your senses. Indulge in a relaxing spa session and rejuvenate your senses. Indulge in a relaxing spa session and rejuvenate your senses.\"\n" +
            "    }\n" +
            "  ]\n" +
            "};";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_amenities_screen);

        listView = (ListView)findViewById(R.id.amenitiesList);
        AmenityCardAdapter adapter = new AmenityCardAdapter(getApplicationContext(), json_response);
        listView.setAdapter(adapter);
    }
}