package com.android.flexability;

import android.os.*;

import org.json.JSONObject;

import okhttp3.*;

public class OkHttpHandler {

    public OkHttpHandler() {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public String getAmenities() {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PROVISIONS;
        return apiRequest(url, "GET");
    }

    public String getAmenities(int limit) {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PROVISIONS + "?" +
                "limit=" + limit;
        return apiRequest(url, "GET");
    }

    public int getAmenitiesCount() {
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PROVISION_COUNT;
        String response = apiRequest(url, "GET");
        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt("totalProvisions");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void createAmenity(String name, String code, String price, String desc) {
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_CREATE_PROVISION + "?" +
                "name=" + name +
                "&id=" + code +
                "&cost=" + price +
                "&description=" + desc;
        String response = apiRequest(url, "POST");
        System.out.println("Amenity created successfully. Response: " + response);
    }

    private String apiRequest(String url, String method) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody requestBody = RequestBody.create("", MediaType.parse("text/plain"));
        if (method.equals("GET")) {
            requestBody = null;
        }
        Request request = new Request.Builder().url(url).method(method, requestBody).build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
