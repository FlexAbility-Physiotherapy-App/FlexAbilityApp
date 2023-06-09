package com.android.flexability;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphView extends AppCompatActivity {

    ArrayList<TransactionInfo> transactionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        transactionData = (ArrayList<TransactionInfo>) getIntent().getSerializableExtra("TransactionData");

        // First clalulate the full cost, to get the percentages and the cost
        // for each physio:
        float fullCost = 0;
        HashMap<String, Float> physioPayment = new HashMap<>();
        HashMap<String, Float> provisionPayment = new HashMap<>();
        for(TransactionInfo ti: transactionData) {
            fullCost += ti.getCost();

            if(physioPayment.containsKey(ti.getPhysioName()))
                physioPayment.put(ti.getPhysioName(),
                        physioPayment.get(ti.getPhysioName()) + ti.getCost());
            else
                physioPayment.put(ti.getPhysioName(), ti.getCost());

            if(provisionPayment.containsKey(ti.getProvisionName()))
                provisionPayment.put(ti.getProvisionName(),
                        provisionPayment.get(ti.getProvisionName()) + ti.getCost());
            else
                provisionPayment.put(ti.getProvisionName(), ti.getCost());
        }

        // Get the chart and work on it:
        PieChart physioPie = (PieChart) findViewById(R.id.physioGraph);
        PieChart provisionPie = (PieChart) findViewById(R.id.provisionGraph);

        // Create the pie charts
        createPie(physioPie, physioPayment, fullCost, getString(R.string.physio_graph_title));
        createPie(provisionPie, provisionPayment, fullCost, getString(R.string.provision_graph_title));
    }

    private void createPie(PieChart pie, HashMap<String, Float> payment, float fullCost, String legDesc) {

        // Populate Pie Entry Lists:
        List<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String, Float> entry: payment.entrySet())
            entries.add(new PieEntry(((entry.getValue() / fullCost) * 100),
                    entry.getKey() + " " + getString(R.string.euro_sign) + " "
                            + entry.getValue())
            );

        // Build the Pie data set:
        PieDataSet dataSet = new PieDataSet(entries, legDesc);
        int colors[] = getResources().getIntArray(R.array.graph_colors);

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);

        // Format the pie to print and the %:
        pie.setUsePercentValues(true);
        dataSet.setValueFormatter(new GraphDataFormatter(
                new DecimalFormat("###,###,###"), pie)
        );

        pie.setData(data);
        pie.animateY(1000);
        dataSet.setValueTextSize(18);

        // Chnage legent direction:
        Legend legend = pie.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setXEntrySpace(4f);
        legend.setYEntrySpace(0f);
        legend.setWordWrapEnabled(true);
        legend.setDrawInside(false);

        // Must change this
        pie.setDescription(null);
        pie.setDrawSliceText(false);
        pie.invalidate();   // To draw the chart
    }

    public void OnClickBackToTransactions(View v) {
        finish();
    }
}