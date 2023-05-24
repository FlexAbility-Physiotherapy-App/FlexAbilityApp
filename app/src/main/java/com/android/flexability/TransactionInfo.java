package com.android.flexability;

public class TransactionInfo {

    private final String date;
    private final String physioName;
    private final int physioId;

    private final double cost;

    public TransactionInfo(String date, String physioName, int physioId, double cost) {

        this.date = date;
        this.physioName = physioName;
        this.physioId = physioId;
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public String getPhysioName() {
        return physioName;
    }

    public int getPhysioId() {
        return physioId;
    }

    public double getCost() {
        return cost;
    }

}
