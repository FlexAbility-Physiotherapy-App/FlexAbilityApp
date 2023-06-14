/*
    This is a static implementation of the R10
 */

package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Transactions extends AppCompatActivity {

    private ArrayList<TransactionInfo> transactionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        transactionsData = (ArrayList<TransactionInfo>) getIntent()
                .getSerializableExtra("transactionData");

        createEarliestTransactionCard();

    }

    public void createEarliestTransactionCard() {

        for(TransactionInfo ti: transactionsData) {
            LinearLayout cvContainer = findViewById(R.id.contentsFinancialMoves);
            LayoutInflater inflater = LayoutInflater.from(cvContainer.getContext());
            View cv = inflater.inflate(
                    R.layout.custom_transactions_card,
                    cvContainer,
                    false
            );


            TextView phName = cv.findViewById(R.id.phText);
            TextView dateTV = cv.findViewById(R.id.dateText);
            TextView cost = cv.findViewById(R.id.costText);

            dateTV.setText(ti.getDate().split(" ")[0]);
            phName.setText(ti.getPhysioName());
            cost.setText(getString(R.string.euro_sign) + Double.toString(ti.getCost()));

            cvContainer.addView(cv, LinearLayout.FOCUS_BACKWARD);
        }
    }

    public void OnClickShowGraph(View v) {
        if(transactionsData != null) {
            Intent newActivity = new Intent(Transactions.this, GraphView.class);
            newActivity.putExtra("TransactionData", transactionsData);

            startActivity(newActivity);
        }
    }

    public void OnClickBackToMain(View view) {
        finish();
    }
}