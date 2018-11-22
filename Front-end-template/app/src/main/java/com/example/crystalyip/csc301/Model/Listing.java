package com.example.crystalyip.csc301.Model;

import java.io.Serializable;

/**
 * A Food Listing */
public class Listing implements Serializable {
    private String foodName;
    private int listingID;
    private String imageLink;
    private int cookID;
    private double price;
    private String location;
    private int imageID;

    public Listing(String foodName, int listingID, String imageLink, int cookID, double price, String location, int imageID) {
        this.foodName = foodName;
        this.listingID = listingID;
        this.imageLink = imageLink;
        this.cookID = cookID;
        this.price = price;
        this.location = location;
        this.imageID = imageID;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getListingID() {
        return listingID;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getCookID() {
        return cookID;
    }

    public double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public int getImageID() {
        return imageID;
    }
}
