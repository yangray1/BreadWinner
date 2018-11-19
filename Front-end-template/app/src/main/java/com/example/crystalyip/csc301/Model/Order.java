package com.example.crystalyip.csc301.Model;

import java.util.Date;

public class Order {
    private String status;
    private int clientID;
    private int listingID;
    private String foodName;
    private String location;
    private int quantity;
    private Date timeOfOrder;

    public Order(String status, int listingID, int clientID, String foodName, String location) {
        this.status = status;
        this.listingID = listingID;
        this.clientID = clientID;
        this.foodName=foodName;
        this.location=location;
        this.quantity = 1;
        this.timeOfOrder = new Date();
    }

    public int getQuantity() {
        return  this.quantity;
    }

    public Date getTimeOfOrder() {
        return this.timeOfOrder;
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
