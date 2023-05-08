package com.android.flexability;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DoctorAppointmentsScreen extends AppCompatActivity {

    String amkaList[] = {"18920365429", "01629354107", "01918273026", "92829372430"};
    String nameList[] = {"Γιάννης Ράφτης", "Γιώργος Ψωμάς", "Ντάλια Βεντάλια", "Ορέστης Παυλόπουλος"};
    String timeList[] = {"14:00-15:00", "15:00-16:00", "16:00-18:00", "18:00-19:00"};

    ListView listView1, listView2, listView3, listView4, listView5, listView6,listView7;
    TextView textView;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments_screen);

        listView1 = (ListView) findViewById(R.id.appointmentsList1);
        listView2 = (ListView) findViewById(R.id.appointmentsList2);
        listView3 = (ListView) findViewById(R.id.appointmentsList3);
        listView4 = (ListView) findViewById(R.id.appointmentsList4);
        listView5 = (ListView) findViewById(R.id.appointmentsList5);
        listView6 = (ListView) findViewById(R.id.appointmentsList6);
        listView7 = (ListView) findViewById(R.id.appointmentsList7);

        CustomBasedAdapter customBasedAdapter1 = new CustomBasedAdapter(getApplicationContext(), nameList, amkaList, timeList, 1);
        CustomBasedAdapter customBasedAdapter2 = new CustomBasedAdapter(getApplicationContext(), nameList, amkaList, timeList, 2);

        listView1.setAdapter(customBasedAdapter1);
        listView2.setAdapter(customBasedAdapter2);
        listView3.setAdapter(customBasedAdapter2);
        listView4.setAdapter(customBasedAdapter2);
        listView5.setAdapter(customBasedAdapter2);
        listView6.setAdapter(customBasedAdapter2);
        listView7.setAdapter(customBasedAdapter2);

        Utility.setListViewHeightBasedOnChildren(listView1);
        Utility.setListViewHeightBasedOnChildren(listView2);
        Utility.setListViewHeightBasedOnChildren(listView3);
        Utility.setListViewHeightBasedOnChildren(listView4);
        Utility.setListViewHeightBasedOnChildren(listView5);
        Utility.setListViewHeightBasedOnChildren(listView6);
        Utility.setListViewHeightBasedOnChildren(listView7);

        textView = (TextView) findViewById(R.id.counter1);
        textView.setText("("+ listView1.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter2);
        textView.setText("("+ listView2.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter3);
        textView.setText("("+ listView3.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter4);
        textView.setText("("+ listView4.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter5);
        textView.setText("("+ listView5.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter6);
        textView.setText("("+ listView6.getAdapter().getCount()+")");
        textView = (TextView) findViewById(R.id.counter7);
        textView.setText("("+ listView7.getAdapter().getCount()+")");

        //today
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date1);
        textView.setText(date);

        //tomorrow
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date2);
        textView.setText(date);

        String daysArray[] = {"Κυριακή","Δευτέρα","Τρίτη", "Τετάρτη","Πέμπτη","Παρασκευή", "Σάββατο"};
        //day3
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date3);
        textView.setText(date);
        textView = (TextView) findViewById(R.id.day3);
        int day = c.get(Calendar.DAY_OF_WEEK);
        textView.setText(daysArray[day - 1]);

        //day4
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date4);
        textView.setText(date);
        textView = (TextView) findViewById(R.id.day4);
        day = c.get(Calendar.DAY_OF_WEEK);
        textView.setText(daysArray[day - 1]);

        //day5
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date5);
        textView.setText(date);
        textView = (TextView) findViewById(R.id.day5);
        day = c.get(Calendar.DAY_OF_WEEK);
        textView.setText(daysArray[day - 1]);

        //day6
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date6);
        textView.setText(date);
        textView = (TextView) findViewById(R.id.day6);
        day = c.get(Calendar.DAY_OF_WEEK);
        textView.setText(daysArray[day - 1]);

        //day7
        c.add(Calendar.DAY_OF_YEAR, 1);
        date = sdf.format(c.getTime());
        textView = (TextView) findViewById(R.id.date7);
        textView.setText(date);
        textView = (TextView) findViewById(R.id.day7);
        day = c.get(Calendar.DAY_OF_WEEK);
        textView.setText(daysArray[day - 1]);

        ConstraintLayout constraintLayout1 = (ConstraintLayout) findViewById(R.id.part1);
        ConstraintLayout constraintLayout2 = (ConstraintLayout) findViewById(R.id.part2);
        ConstraintLayout constraintLayout3 = (ConstraintLayout) findViewById(R.id.part3);
        ConstraintLayout constraintLayout4 = (ConstraintLayout) findViewById(R.id.part4);
        ConstraintLayout constraintLayout5 = (ConstraintLayout) findViewById(R.id.part5);
        ConstraintLayout constraintLayout6 = (ConstraintLayout) findViewById(R.id.part6);
        ConstraintLayout constraintLayout7 = (ConstraintLayout) findViewById(R.id.part7);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.part1){
                    ImageView image = (ImageView) findViewById(R.id.arrow1);
                    if(listView1.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView1.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView1.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part2) {
                    ImageView image = (ImageView) findViewById(R.id.arrow2);
                    if(listView2.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView2.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView2.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part3) {
                    ImageView image = (ImageView) findViewById(R.id.arrow3);
                    if(listView3.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView3.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView3.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part4) {
                    ImageView image = (ImageView) findViewById(R.id.arrow4);
                    if(listView4.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView4.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView4.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part5) {
                    ImageView image = (ImageView) findViewById(R.id.arrow5);
                    if(listView5.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView5.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView5.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part6) {
                    ImageView image = (ImageView) findViewById(R.id.arrow6);
                    if(listView6.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView6.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView6.setVisibility(View.VISIBLE);
                    }
                } else if (v.getId() == R.id.part7) {
                    ImageView image = (ImageView) findViewById(R.id.arrow7);
                    if(listView7.getVisibility() == View.VISIBLE) {
                        image.setImageResource(R.drawable.angle_right_solid);
                        listView7.setVisibility(View.GONE);
                    }
                    else {
                        image.setImageResource(R.drawable.angle_down_solid);
                        listView7.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        constraintLayout1.setOnClickListener(onClickListener);
        constraintLayout2.setOnClickListener(onClickListener);
        constraintLayout3.setOnClickListener(onClickListener);
        constraintLayout4.setOnClickListener(onClickListener);
        constraintLayout5.setOnClickListener(onClickListener);
        constraintLayout6.setOnClickListener(onClickListener);
        constraintLayout7.setOnClickListener(onClickListener);
    }
}