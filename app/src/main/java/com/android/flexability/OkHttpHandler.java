package com.android.flexability;

import android.os.*;
import org.json.*;
import java.util.*;
import okhttp3.*;

public class OkHttpHandler {

    // TODO: Delete this once the API is ready
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

    public OkHttpHandler() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    // TODO: Use this method to get the JSON response from the API once it is ready
//    String getAmenitiesJSON(String url) throws Exception {
//        OkHttpClient client = new OkHttpClient().newBuilder().build();
//        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
//        Request request = new Request.Builder().url(url).method("POST", body).build();
//        try (Response response = client.newCall(request).execute()) {
//            assert response.body() != null;
//            return response.body().string();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
    // TODO: Delete this method once the API is ready. Temporary method to return the JSON response
    String getAmenitiesJSON(String url) throws Exception {
        return json_response;
    }

    public void createAmenity(String url) throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create("", MediaType.parse("text/plain"));
        Request request = new Request.Builder().url(url).method("POST", body).build();
        // TODO: Uncomment this once the API is ready
//        try (Response response = client.newCall(request).execute()) {
//            System.out.println("Amenity created successfully");
//        }
        System.out.println("Amenity created successfully");
    }

}
