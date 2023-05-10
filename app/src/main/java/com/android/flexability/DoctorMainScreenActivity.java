package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class DoctorMainScreenActivity extends AppCompatActivity {

    String amkaList[] = {"18920365429", "01629354107", "01918273026"};
    String nameList[] = {"Γιάννης Ράφτης", "Γιώργος Ψωμάς", "Ντάλια Βεντάλια"};
    String timeList[] = {"14:00-15:00", "15:00-16:00", "16:00-18:00"};
    ListView listView1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main_screen);

        listView1 = (ListView) findViewById(R.id.appointmentsList1);
        CustomBasedAdapter customBasedAdapter1 = new CustomBasedAdapter(getApplicationContext(), nameList, amkaList, timeList, 1);
        listView1.setAdapter(customBasedAdapter1);
        Utility.setListViewHeightBasedOnChildren(listView1);

        textView = (TextView) findViewById(R.id.counter1);
        textView.setText("("+ listView1.getAdapter().getCount()+")");

        TextView changeScreenBtn = (TextView) findViewById(R.id.changeScreen);
        changeScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorMainScreenActivity.this, DoctorAppointmentsScreenActivity.class));
            }
        });
    }

}