package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PatientMainScreenActivity extends AppCompatActivity {

    private ArrayList<String> upcomingAptLst;
    private ArrayList<TransactionInfo> transactionsData;
    private ArrayList<String> patientAppointmentsLst;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);

        // This is for the Appointments part:
        // Get the id from the login screen and fill the text views with
        // the data we got
        pid = (Integer) getIntent().getSerializableExtra("id");

        // Gte data from API:
        getUpcomingAppointment();
        transactionsParser(new OkHttpHandler().getTransactions(pid));

        TextView tv;
        if(upcomingAptLst.size() != 0)
            createUpcomingAppointmentView();
        else {
            tv = (TextView) findViewById(R.id.aptTitle);

            tv.append(
                    getString(R.string.l_par)
                    + getString(R.string.zero)
                    + getString(R.string.r_par)
            );
        }

        if(transactionsData.size() != 0)
            createTransactionView();
        else {
            tv = (TextView) findViewById(R.id.transactionTitle);

            tv.append(
                    getString(R.string.l_par)
                    + getString(R.string.zero)
                    + getString(R.string.r_par)
            );
        }

        createAmenitySearchButton();

    }

    private void getPatientAppointments() {
        String patientAppointments = (new OkHttpHandler().getPatientAppointments(pid));

        Gson gson = new Gson();
        patientAppointmentsLst = gson.fromJson(patientAppointments, ArrayList.class);
    }

    private void getUpcomingAppointment() {
        String upcomingApt = (new OkHttpHandler().getUpcomingPatientAppointment(pid));

        // Parse the json like object to a java ArrayList
        Gson gson = new Gson();
        upcomingAptLst = gson.fromJson(upcomingApt, ArrayList.class);
    }

    private void transactionsParser(String json) {
        ArrayList<TransactionInfo> transactionsData = new ArrayList<>();

        try {
            JSONObject transactionsJSONObj = new JSONObject(json);
            JSONArray transactionsJSONArray = transactionsJSONObj.getJSONArray("transactions");

            for (int i = 0; i < transactionsJSONArray.length(); ++i) {
                JSONObject elmObj = transactionsJSONArray.getJSONObject(i);

                String phName = elmObj.getString("ph_name");
                String date = elmObj.getString("date");
                String prName = elmObj.getString("pr_name");
                double cost = elmObj.getDouble("cost");
                int id = elmObj.getInt("id");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                    transactionsData.add(new TransactionInfo(date, phName, prName, id, (float) cost));
            }
        } catch (JSONException e) {
        }

        this.transactionsData = transactionsData;
    }

    public void OnClickShowTransactions(View view) {
         if(transactionsData != null) {
             Intent transactions = new Intent(PatientMainScreenActivity.this,
                     Transactions.class
             );
             transactions.putExtra("transactionData", transactionsData);
             startActivity(transactions);
         }
    }

    //@SuppressLint("ResourceAsColor")
    private void createAmenitySearchButton() {
        ScrollView sv = (ScrollView) findViewById(R.id.mainPatientScreen);

        // Get the outer Linear Layout.
        LinearLayout outerLL = (LinearLayout) findViewById(R.id.outterMainPatientScreen);

        Button amenityBtn = new Button(this);

        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                800,
                150
        );
        llParams.setMargins(0, 100, 0, 0);

        amenityBtn.setLayoutParams(llParams);
        outerLL.setGravity(Gravity.CENTER_HORIZONTAL);

        amenityBtn.setText(getString(R.string.amenitySearch));

        GradientDrawable gd = new GradientDrawable();

        gd.setCornerRadius(6);
        gd.setColor(getColor(R.color.buttons));

        amenityBtn.setBackground(gd);
        amenityBtn.setTextColor(getColor(R.color.white));
        amenityBtn.setAllCaps(false);
        amenityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appointments = new Intent(PatientMainScreenActivity.this,
                        SearchAppointment.class
                );
                appointments.putExtra("pid", Integer.toString(pid));
                startActivity(appointments);
            }
        });

        outerLL.addView(amenityBtn);
    }

    private void createUpcomingAppointmentView() {

        getPatientAppointments();

        ScrollView sv = (ScrollView) findViewById(R.id.mainPatientScreen);

        // Get the outer Linear Layout.
        LinearLayout outerLL = (LinearLayout) findViewById(R.id.outterMainPatientScreen);

        // Get the appointment layout:
        ConstraintLayout aptCL = (ConstraintLayout) findViewById(R.id.aptLayout);

        // Create The card view
        CardView cv = new CardView(this);

        CardView.LayoutParams cvParams = new FrameLayout.LayoutParams(
                CardView.LayoutParams.MATCH_PARENT,
                CardView.LayoutParams.WRAP_CONTENT
        );
        cvParams.setMargins(7, 7, 7, 7);

        // Change radius
        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(12);

        //cv.setId(ti.getPhysioId());
        cv.setBackground(gd);
        cv.setCardElevation(5);
        cv.setLayoutParams(cvParams);

        // The two containers of card view
        ConstraintLayout colorLayout = new ConstraintLayout(this);
        ConstraintLayout infoLayout = new ConstraintLayout(this);
        infoLayout.setId(View.generateViewId());

        ConstraintLayout.LayoutParams clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );

        colorLayout.setLayoutParams(clParams);
        colorLayout.setBackground(getDrawable(R.drawable.appointment_card_green));

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.setMargins(12, 0, 0, 0);

        infoLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_light_background));
        infoLayout.setLayoutParams(clParams);
        infoLayout.setPadding(7, 7, 7, 7);

        // Append number of appointments
        TextView titleApt = (TextView) findViewById(R.id.aptTitle);
        titleApt.append(" " + getString(R.string.l_par) + patientAppointmentsLst.size() + getString(R.string.r_par));

        // Create the sub text views
        TextView nameTV = new TextView(this);
        nameTV.setId(View.generateViewId());
        TextView hourTV = new TextView(this);
        hourTV.setId(View.generateViewId());
        TextView dateTV = new TextView(this);
        dateTV.setId(View.generateViewId());

        // Create the title text views:
        TextView nameTitle = new TextView(this);
        nameTitle.setId(View.generateViewId());
        TextView hourTitle = new TextView(this);
        hourTitle.setId(View.generateViewId());
        TextView dateTitle = new TextView(this);
        dateTitle.setId(View.generateViewId());

        Typeface fontSub = Typeface.createFromAsset(
                getAssets(),
                "font/manrope_light.ttf"
        );

        dateTV.setText(upcomingAptLst.get(0));
        dateTV.setTextSize(22);
        dateTV.setTypeface(fontSub);

        hourTV.setText(upcomingAptLst.get(1));
        hourTV.setTextSize(22);
        hourTV.setTypeface(fontSub);

        nameTV.setText(upcomingAptLst.get(2));
        nameTV.setTextSize(22);
        nameTV.setTypeface(fontSub);

        Typeface fontTitle = Typeface.createFromAsset(
                getAssets(),
                "font/manrope_extra_bold.ttf"
        );

        nameTitle.setText(R.string.patientsname);
        nameTitle.setTextSize(20);
        nameTitle.setTypeface(fontTitle);

        hourTitle.setText(R.string.time);
        hourTitle.setTextSize(20);
        hourTitle.setTypeface(fontTitle);

        dateTitle.setText(R.string.day);
        dateTitle.setTextSize(20);
        dateTitle.setTypeface(fontTitle);

        infoLayout.addView(nameTitle);
        infoLayout.addView(hourTitle);
        infoLayout.addView(dateTitle);

        infoLayout.addView(nameTV);
        infoLayout.addView(hourTV);
        infoLayout.addView(dateTV);

        // Create the constraints inside the card view:
        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.setMargins( 0, 3, 0, 12);
        clParams.topToBottom = nameTitle.getId();
        clParams.startToStart = infoLayout.getId();
        clParams.bottomToTop = dateTitle.getId();

        nameTV.setLayoutParams(clParams);

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.setMargins( 0, 3, 0, 0);
        clParams.topToBottom = dateTitle.getId();
        clParams.startToStart = infoLayout.getId();

        dateTV.setLayoutParams(clParams);

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.setMargins( 0, 3, 0, 12);
        clParams.topToBottom = hourTitle.getId();
        clParams.endToEnd = infoLayout.getId();

        hourTV.setLayoutParams(clParams);

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.topToTop = infoLayout.getId();

        nameTitle.setLayoutParams(clParams);

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.setMargins(0, 40, 0, 0);
        clParams.topToBottom = nameTV.getId();
        clParams.startToStart = infoLayout.getId();
        clParams.bottomToTop = dateTV.getId();

        dateTitle.setLayoutParams(clParams);

        clParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        clParams.topToTop = infoLayout.getId();
        clParams.endToEnd = infoLayout.getId();
        clParams.bottomToBottom = infoLayout.getId();

        hourTitle.setLayoutParams(clParams);

        // Add the card view in the screen
        colorLayout.addView(infoLayout);
        cv.addView(colorLayout);

        outerLL.addView(cv, LinearLayout.FOCUS_BACKWARD);
    }

    private void createTransactionView() {

        ScrollView sv = (ScrollView) findViewById(R.id.mainPatientScreen);

        // Get the outer Linear Layout.
        LinearLayout outerLL = (LinearLayout) findViewById(R.id.outterMainPatientScreen);

        // This is for the transactions part:

        TextView title = (TextView) findViewById(R.id.transactionTitle);
        title.append(" " + getString(R.string.l_par)
                + transactionsData.size()
                + getString(R.string.r_par));

        // Create the contents. They are CardView objects.
        if(transactionsData.size() != 0) {
            TransactionInfo ti = transactionsData.get(0);

            // The card view
            CardView cv = new CardView(this);

            CardView.LayoutParams cvParams = new FrameLayout.LayoutParams(
                    CardView.LayoutParams.MATCH_PARENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cvParams.setMargins(7, 7, 7, 7);

            // Change radius
            GradientDrawable gd = new GradientDrawable();
            gd.setCornerRadius(12);

            //cv.setId(ti.getPhysioId());
            cv.setBackground(gd);
            cv.setCardElevation(5);
            cv.setLayoutParams(cvParams);

            // The two containers of card view
            ConstraintLayout colorLayout = new ConstraintLayout(this);
            ConstraintLayout infoLayout = new ConstraintLayout(this);
            infoLayout.setId(View.generateViewId());

            ConstraintLayout.LayoutParams clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            colorLayout.setLayoutParams(clParams);
            colorLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_dark_background));

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins(12, 0, 0, 0);

            infoLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_light_background));
            infoLayout.setLayoutParams(clParams);
            infoLayout.setPadding(7, 7, 7, 7);

            // Now insert the text views in the last layout:
            TextView phName = new TextView(this);
            phName.setId(View.generateViewId());
            TextView date = new TextView(this);
            date.setId(View.generateViewId());
            TextView price = new TextView(this);
            price.setId(View.generateViewId());
            TextView amenity = new TextView(this);
            amenity.setId(View.generateViewId());

            TextView phTitle = new TextView(this);
            phTitle.setId(View.generateViewId());
            TextView dateTitle = new TextView(this);
            dateTitle.setId(View.generateViewId());
            TextView priceTitle = new TextView(this);
            priceTitle.setId(View.generateViewId());
            TextView amenityTitle = new TextView(this);
            amenityTitle.setId(View.generateViewId());

            Typeface fontSub = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_light.ttf"
            );

            phName.setText(ti.getPhysioName());
            phName.setTextSize(22);
            phName.setTypeface(fontSub);

            date.setTextSize(22);
            date.setText(ti.getDate());
            date.setTypeface(fontSub);

            price.setText(getString(R.string.price_mark) + Double.toString(ti.getCost()));
            price.setTextSize(22);
            price.setTypeface(fontSub);

            amenity.setText(ti.getProvisionName());
            amenity.setTextSize(22);
            amenity.setTypeface(fontSub);

            Typeface fontTitle = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_extra_bold.ttf"
            );

            phTitle.setText(getString(R.string.physioHeader));
            phTitle.setTextSize(20);
            phTitle.setTypeface(fontTitle);

            dateTitle.setText(getString(R.string.dateHeader));
            dateTitle.setTextSize(20);
            dateTitle.setTypeface(fontTitle);

            priceTitle.setText(getString(R.string.costHeader));
            priceTitle.setTextSize(20);
            priceTitle.setTypeface(fontTitle);

            amenityTitle.setText(getString(R.string.amenityHeader));
            amenityTitle.setTextSize(20);
            amenityTitle.setTypeface(fontTitle);

            infoLayout.addView(phName);
            infoLayout.addView(date);
            infoLayout.addView(price);
            infoLayout.addView(amenity);

            infoLayout.addView(phTitle);
            infoLayout.addView(dateTitle);
            infoLayout.addView(priceTitle);
            infoLayout.addView(amenityTitle);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins( 0, 3, 0, 12);
            clParams.topToBottom = phTitle.getId();
            clParams.startToStart = infoLayout.getId();
            clParams.bottomToTop = dateTitle.getId();

            phName.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.startToStart = infoLayout.getId();
            clParams.bottomToBottom = infoLayout.getId();

            date.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins( 0, 3, 0, 12);
            clParams.topToBottom = priceTitle.getId();
            clParams.endToEnd = infoLayout.getId();

            price.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.bottomToBottom = infoLayout.getId();
            clParams.endToEnd = infoLayout.getId();

            amenity.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.topToTop = infoLayout.getId();

            phTitle.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins(0, 40, 0, 0);
            clParams.topToTop = phName.getId();
            clParams.startToStart = infoLayout.getId();
            clParams.bottomToTop = date.getId();

            dateTitle.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.topToTop = infoLayout.getId();
            clParams.endToEnd = infoLayout.getId();
            clParams.bottomToTop = price.getId();

            priceTitle.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins(0, 40, 0, 0);
            clParams.topToTop = price.getId();
            clParams.bottomToTop = amenity.getId();
            clParams.endToEnd = infoLayout.getId();

            amenityTitle.setLayoutParams(clParams);

            colorLayout.addView(infoLayout);

            cv.addView(colorLayout);

            outerLL.addView(cv);
        }
    }
}