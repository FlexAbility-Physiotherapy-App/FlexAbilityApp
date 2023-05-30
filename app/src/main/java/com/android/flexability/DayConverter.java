package com.android.flexability;

public enum DayConverter {
    MONDAY("Δευτέρα"),
    TUESDAY("Τρίτη"),
    WEDNESDAY("Τετάρτη"),
    THURSDAY("Πέμπτη"),
    FRIDAY("Παρασκευή"),
    SATURDAY("Σάββατο"),
    SUNDAY("Κυριακή");

    private String greekName;

    DayConverter(String greekName) {
        this.greekName = greekName;
    }

    public String getGreekName() {
        return greekName;
    }

    public static String convertToGreek(String dayName) {
        for (DayConverter day : DayConverter.values()) {
            if (day.name().equalsIgnoreCase(dayName)) {
                return day.getGreekName();
            }
        }
        return "Invalid day";
    }
}