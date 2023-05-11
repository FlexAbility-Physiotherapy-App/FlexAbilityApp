package com.android.flexability;

public class Amenity {
    private String title;
    private String code;
    private int price;
    private String description;

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
