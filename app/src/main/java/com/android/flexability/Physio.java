package com.android.flexability;

public class Physio {
    private Integer physioId;
    private String name;
    private String phone;

    public Physio(Integer physioId, String name, String phone) {
        this.physioId = physioId;
        this.name = name;
        this.phone = phone;
    }

    public Integer getPhysioId() {
        return physioId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
