package com.android.flexability;

public class Appointment {
    private final String date;
    private final String name;
    private final String surname;
    private final String amka;

    public Appointment( String name, String surname, String amka, String date){
        this.date = date;
        this.name = name;
        this.surname = surname;
        this.amka = amka;
    }

    public String getDate(){return this.date;}

    public String getName_(){return this.name;}

    public String getSurname(){return this.surname;}

    public String getAmka(){return this.amka;}
}
