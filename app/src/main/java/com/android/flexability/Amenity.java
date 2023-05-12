package com.android.flexability;

public class Amenity {
    private final String title;
    private final String code;
    private final int price;
    private final String description;

    public Amenity(String title, String code, int price, String description) {
        this.title = title;
        this.code = code;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
