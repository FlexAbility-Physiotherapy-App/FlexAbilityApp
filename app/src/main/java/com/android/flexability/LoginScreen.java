package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {
    String json_response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText textView_username = (EditText) findViewById(R.id.txtUsername);
        EditText textView_password = (EditText) findViewById(R.id.txtPassword);
        Button loginBtn = (Button)findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    json_response = new OkHttpHandler().getUser(textView_username.getText().toString(), textView_password.getText().toString());
                    System.out.println("HTTP Response received successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                User user = parseJson(json_response);
                if(user.getCategory().equals("admin")){
                    startActivity(new Intent(LoginScreen.this, PhysioMainScreen.class));
                } else if (user.getCategory().equals("physio")) {
                    Intent intent = new Intent(LoginScreen.this, DoctorMainScreenActivity.class);
                    intent.putExtra("id", user.getId());
                    startActivity(intent);
                } else if (user.getCategory().equals("patient")){
                    Intent intent = new Intent(LoginScreen.this, PatientMainScreenActivity.class);
                    intent.putExtra("id", user.getId());
                    startActivity(intent);
                }
            }
        });

    }

    public static User parseJson(String response) {
        User user = null;
        try{
            JSONObject jsonObject = new JSONObject(response);
            int id = jsonObject.getInt("id");
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");
            String category = jsonObject.getString("category");
            user = new User(id, username, password, category);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}