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

    public String getPhysios() {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PHYSIOS;
        return apiRequest(url, "GET");
    }

    public String getPhysios(int limit) {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PHYSIOS + "?" +
                "limit=" + limit;
        return apiRequest(url, "GET");
    }

    public int getPhysiosCount() {
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_PHYSIOS_COUNT;
        String response = apiRequest(url, "GET");
        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt("totalphysios");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void Physio(int id, String name, String address, String phone, String ssn) {
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_CREATE_PHYSIO + "?" +
                "&id=" + id +
                "&address=" + address +
                "&name=" + name +
                "&afm=" + ssn +
                "&phone=" + phone;
        String response = apiRequest(url, "POST");
        System.out.println("Physio created successfully. Response: " + response);
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
