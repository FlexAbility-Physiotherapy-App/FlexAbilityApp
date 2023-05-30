package com.android.flexability;

import android.net.Uri;
import android.os.*;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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

    public String getAppointments(int id, String date){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_APPOINTMENTS + "?" + "date=" + date + "&id=" + id;
        return apiRequest(url, "GET");
    }

    public String getAppointments(int id, String date, int limit){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_APPOINTMENTS + "?" + "date=" + date + "&id=" + id + "&limit=" + limit;
        return apiRequest(url, "GET");
    }

    public String getRequestedAppointments(int id, String date){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_REQUESTED_APPOINTMENTS + "?" + "date=" + date + "&id=" + id;
        return apiRequest(url, "GET");

    }public String getAllRequestedAppointments(int id){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_ALL_REQUESTED_APPOINTMENTS + "?" + "id=" + id;
        return apiRequest(url, "GET");
    }

    public String rejectAppointment(int physio_id, int patient_id, String timestamp) {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_POST_REJECT_APPOINTMENT +
                "?physio_id=" + physio_id + "&patient_id=" + patient_id + "&timestamp=" + timestamp;
        return apiRequest(url, "GET");
    }

    public String acceptAppointment(int physio_id, int patient_id, String timestamp) {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_POST_ACCEPT_APPOINTMENT +
                "?physio_id=" + physio_id + "&patient_id=" + patient_id + "&timestamp=" + timestamp;
        return apiRequest(url, "GET");
    }

    public int getAppointmentsCount(int id, String date) {
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_APPOINTMENT_COUNT + "?" + "date=" + date + "&id=" + id;
        String response = apiRequest(url, "GET");
        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getInt("totalAppointments");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public void completeAppointment(int physio_id, int patient_id, String timestamp, String comment) {
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_POST_COMPLETE_APPOINTMENT +
                "?physio_id=" + physio_id + "&patient_id=" + patient_id + "&timestamp=" + timestamp + "&comment=" + comment ;
        String response = apiRequest(url, "GET");
        System.out.println("Appointment completed and DB was updated. Response: " + response);
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

    public String getPatientFromAMKA(String amka){
        String url= AppConfig.BACKEND_SERVER_IP + AppConfig.API_SEARCH_PATIENT + "?" + "amka=" + amka;

        String response = apiRequest(url, "GET");
        try{
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getTransactions(){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_TRANSACTIONS;
        return apiRequest(url, "GET");
    }

    public String getUser(String username, String password){
        String url = AppConfig.BACKEND_SERVER_IP + AppConfig.API_GET_USER + "?" + "username=" + username + "&password=" + password;
        return apiRequest(url, "GET");
    }
}
