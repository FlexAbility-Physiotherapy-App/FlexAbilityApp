package com.android.flexability;

import java.io.Serializable;

public class TransactionInfo implements Serializable {

    private final String date;
    private final String physioName;
    private final String provisionName;
    private final int physioId;
    private final float cost;

    public TransactionInfo(String date, String physioName, String provisionName, int physioId, float cost) {

        this.date = date;
        this.physioName = physioName;
        this.provisionName = provisionName;
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

    public float getCost() {
        return cost;
    }

    public String getProvisionName() {
        return provisionName;
    }

}
