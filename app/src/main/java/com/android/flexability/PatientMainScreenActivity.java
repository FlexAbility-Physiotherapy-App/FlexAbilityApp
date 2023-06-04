package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class PatientMainScreenActivity extends AppCompatActivity {

    private ArrayList<String> upcomingAptLst;
    private ArrayList<TransactionInfo> transactionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);

        ScrollView sv = (ScrollView) findViewById(R.id.mainPatientScreen);

        // Get the outer Linear Layout.
        LinearLayout outerLL = (LinearLayout) findViewById(R.id.outterMainPatientScreen);

        // This is for the Appointments part:
        // Get the id from the login screen and fill the text views with
        // the data we got
        int id = (Integer) getIntent().getSerializableExtra("id");
        getUpcomingAppointment(id);

        TextView titleApt = (TextView) findViewById(R.id.aptTitle);
        TextView nameTV = (TextView) findViewById(R.id.nameFieldTV);
        TextView hourTV = (TextView) findViewById(R.id.hourFieldTV);
        TextView dateTV = (TextView) findViewById(R.id.dateFieldTV);

        titleApt.append(" " + getString(R.string.l_par) + "1" + getString(R.string.r_par));
        dateTV.setText(upcomingAptLst.get(0));
        hourTV.setText(upcomingAptLst.get(1));
        nameTV.setText(upcomingAptLst.get(2));

        // This is for the transactions part:
        // Extract JSON data, from the database, concerning user transactions:
        transactionsData = transactionsParser(new OkHttpHandler().getTransactions());

        // Create the contents. They are CardView objects.
        for (int i = 0; i < 2; i++) {
            TransactionInfo ti = transactionsData.get(i);

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
                    80
            );
            clParams.setMargins(7, 0, 0, 0);

            infoLayout.setBackgroundColor(getColor(androidx.cardview.R.color.cardview_light_background));
            infoLayout.setLayoutParams(clParams);
            infoLayout.setPadding(5, 5, 5, 5);

            // Now insert the text views in the last layout:
            TextView phName = new TextView(this);
            phName.setId(View.generateViewId());

            TextView date = new TextView(this);
            date.setId(View.generateViewId());

            TextView price = new TextView(this);
            price.setId(View.generateViewId());

            Typeface fontName = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_extra_bold.ttf"
            );

            phName.setText(ti.getPhysioName());
            phName.setTextSize(18);
            phName.setTypeface(fontName);

            Typeface fontDate = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_extra_light.ttf"
            );

            date.setTextSize(14);
            date.setText(ti.getDate());
            date.setTypeface(fontDate);

            Typeface fontPrice = Typeface.createFromAsset(
                    getAssets(),
                    "font/manrope_regular.ttf"
            );

            price.setText(getString(R.string.price_mark) + Double.toString(ti.getCost()));
            price.setTextSize(20);
            price.setTypeface(fontPrice);

            infoLayout.addView(phName);
            infoLayout.addView(date);
            infoLayout.addView(price);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            clParams.startToStart = infoLayout.getId();
            clParams.topToTop = infoLayout.getId();

            phName.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.bottomToBottom = infoLayout.getId();

            date.setLayoutParams(clParams);

            clParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );
            clParams.setMargins(0, 0, 24, 0);
            clParams.topToTop = infoLayout.getId();
            clParams.bottomToBottom = infoLayout.getId();
            clParams.endToEnd = infoLayout.getId();

            price.setLayoutParams(clParams);

            colorLayout.addView(infoLayout);

            cv.addView(colorLayout);

            outerLL.addView(cv);
        }

    }

    private void getUpcomingAppointment(int id) {
        String upcomingApt = (new OkHttpHandler().getUpcomingPatientAppointment(id));

        // Parse the json like object to a java ArrayList
        Gson gson = new Gson();
        upcomingAptLst = gson.fromJson(upcomingApt, ArrayList.class);

        String hello;
    }

    private ArrayList<TransactionInfo> transactionsParser(String json) {
        ArrayList<TransactionInfo> transactionData = new ArrayList<>();

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
                    transactionData.add(new TransactionInfo(date, phName, prName, id, (float) cost));
            }
        } catch (JSONException e) {
        }

        return transactionData;
    }

    public void OnClickShowAppointments(View view) {
        Intent intent = new Intent(PatientMainScreenActivity.this, CurrentAppointmentScreenActivity.class);
        startActivity(intent);
    }
}