package com.android.flexability;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DecimalFormat;

public class GraphDataFormatter extends PercentFormatter {
    private DecimalFormat decFromat;
    private PieChart pie;

    public GraphDataFormatter(DecimalFormat decFromat, PieChart pie) {
        this.decFromat = decFromat;
        this.pie = pie;
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + "%";
    }

    @Override
    public String getPieLabel(float value, PieEntry pieEntry) {
        if (pie != null && pie.isUsePercentValuesEnabled()) {
            return getFormattedValue(value);
        } else {
            return mFormat.format(value);
        }
    }
}