package com.android.flexability;

public class Appointment {
    private final String timestamp;
    private final String name;
    private final String surname;
    private final String amka;
    private final int patientId;

    public Appointment( String name, String surname, String amka, String timestamp, int patientId){
        this.timestamp = timestamp;
        this.name = name;
        this.surname = surname;
        this.amka = amka;
        this.patientId = patientId;
    }

    public String getTimestamp(){return this.timestamp;}

    public String getName_(){return this.name;}

    public String getSurname(){return this.surname;}

    public String getAmka(){return this.amka;}

    public int getPatientId(){return this.patientId;}
}
