package com.example.crystalyip.csc301.Model;

import android.widget.ImageView;

public class Listing {
    public String foodName;
    public int listingID;
    public String imageLink;
    public int cookID;
    public double price;
    public String location;
    public int imageID;

    public Listing(String foodName, int listingID, String imageLink, int cookID, double price, String location, int imageID) {
        this.foodName = foodName;
        this.listingID = listingID;
        this.imageLink = imageLink;
        this.cookID = cookID;
        this.price = price;
        this.location = location;
        this.imageID = imageID;
    }
}
