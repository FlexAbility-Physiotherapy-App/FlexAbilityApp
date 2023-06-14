package com.android.flexability;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PatientMainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_screen);

        Intent intent = getIntent();
        int patient_id = intent.getIntExtra("id", -1);

        Button searchAptBtn = findViewById(R.id.searchAptBtn);
        TextView seeAllApts = findViewById(R.id.seeAllApts);
        TextView seeAllTransactions = findViewById(R.id.seeAllTransactions);

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            String upcomingAppointment  = okHttpHandler.getUpcomingPatientAppointment(patient_id);
            ArrayList<String> aptDetails = new ArrayList<>();
            JSONArray aptDetailsJson = new JSONArray(upcomingAppointment);
            for (int i = 0; i < aptDetailsJson.length(); i++) {
                aptDetails.add(aptDetailsJson.getString(i));
            }
            if (aptDetails.size() != 0) {
                createUpcomingAptCard(aptDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            OkHttpHandler okHttpHandler = new OkHttpHandler();
            String patientAppointments  = okHttpHandler.getPatientAppointments(patient_id);
            Gson gson = new Gson();
            TypeToken<List<List<String>>> typeToken = new TypeToken<List<List<String>>>() {};
            List<List<String>> allApts = gson.fromJson(patientAppointments, typeToken.getType());

            TextView patientAptHeaderCount = findViewById(R.id.patientAptHeaderCount);
            patientAptHeaderCount.setText("(" + Integer.toString(allApts.size()) + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<TransactionInfo> transactionsData =
                transactionsParser(
                        new OkHttpHandler().getTransactions(patient_id)
                );

        if(transactionsData.size() > 0)
            createEarliestTransactionCard(transactionsData);

        seeAllApts.setOnClickListener(v -> {
            Intent seeAllAptsIntent = new Intent(PatientMainScreenActivity.this, PatientAllAppointments.class);
            seeAllAptsIntent.putExtra("pid", patient_id);
            startActivity(seeAllAptsIntent);
        });

        searchAptBtn.setOnClickListener(v -> {
            Intent searchIntent = new Intent(PatientMainScreenActivity.this, SearchAppointment.class);
            searchIntent.putExtra("pid", patient_id);
            startActivity(searchIntent);
        });

        seeAllTransactions.setOnClickListener(v -> {
            Intent transactions = new Intent(
                    PatientMainScreenActivity.this,
                    Transactions.class
            );
            transactions.putExtra("transactionData", transactionsData);
            startActivity(transactions);
        });
    }

    public void createUpcomingAptCard(ArrayList<String> aptDetails) {
        LinearLayout container = findViewById(R.id.patientNextAptMain);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cardView = inflater.inflate(R.layout.appointment_patient_card, container, false);

        TextView dayTextView = cardView.findViewById(R.id.dayText);
        TextView hourTextView = cardView.findViewById(R.id.hourText);
        TextView nameTextView = cardView.findViewById(R.id.nameText);
        TextView phoneTextView = cardView.findViewById(R.id.phoneText);

        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputDateFormat.parse(aptDetails.get(0));
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            dayTextView.setText(DayConverter.convertToGreek(outputDateFormat.format(date)));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        hourTextView.setText(aptDetails.get(1).substring(0, 5));
        nameTextView.setText(aptDetails.get(2));
        phoneTextView.setText(aptDetails.get(3));

        container.addView(cardView);
    }

    public void createEarliestTransactionCard(ArrayList<TransactionInfo> til) {
        TextView transactionsCnt = findViewById(R.id.transactionCnt);

        transactionsCnt.setText(
                getString(R.string.l_par)
                        + til.size()
                        + getString(R.string.r_par)
        );

        LinearLayout container = findViewById(R.id.transactionLatest);
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cv = inflater.inflate(
                R.layout.custom_earliest_transaction_card,
                container,
                false
        );

        TextView phName = cv.findViewById(R.id.phName);
        TextView dateTV = cv.findViewById(R.id.dateField);
        TextView cost = cv.findViewById(R.id.costField);
        TextView prName = cv.findViewById(R.id.prText);

        dateTV.setText(til.get(0).getDate().split(" ")[0]);
        phName.setText(til.get(0).getPhysioName());
        prName.setText(til.get(0).getProvisionName());
        cost.setText(getString(R.string.euro_sign) + Double.toString(til.get(0).getCost()));

        container.addView(cv);
    }


    private ArrayList<TransactionInfo> transactionsParser(String json) {
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
            throw new RuntimeException(e);
        }

        return transactionsData;
    }
}