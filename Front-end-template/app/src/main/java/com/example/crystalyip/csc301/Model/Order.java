package com.example.crystalyip.csc301.Model;

public class Order {
    private String status;
    private int clientID;
    private int listingID;
    private String foodName;
    private String location;

    public Order(String status, int listingID, int clientID, String foodName, String location) {
        this.status = status;
        this.listingID = listingID;
        this.clientID = clientID;
        this.foodName=foodName;
        this.location=location;
    }

    public String getOrderStatus() {
        return this.status;
    }

    public int getListingID() {
        return listingID;
    }

    public int getClientID() {
        return this.clientID;
    }

    public String getFoodName() {
        return this.foodName;
    }

    public String getLocation() {return this.location; }
}
