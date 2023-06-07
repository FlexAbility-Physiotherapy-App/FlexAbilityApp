package com.android.flexability;

public class Physio {
    private final Integer id;
    private final String name;
    private final String address;
    private final String phone;
    private final String ssn;

    public Physio(Integer id, String name, String address, String phone, String ssn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.ssn = ssn;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() { return address; }

    public String getPhone() { return phone;  }

    public String getSSN() { return ssn; }
}
