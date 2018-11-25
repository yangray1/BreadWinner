package com.example.crystalyip.csc301.Model;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Food Listing */
public class Listing implements Parcelable {
    private String foodName;
    private int listingID;
    private String imageLink;
    private int cookID;
    private double price;
    private String location;
    private int imageID;
    private Bitmap imageBytes;

    public Listing(String foodName, int listingID, String imageLink, int cookID, double price, String location, int imageID) {
        this.foodName = foodName;
        this.listingID = listingID;
        this.imageLink = imageLink;
        this.cookID = cookID;
        this.price = price;
        this.location = location;
        this.imageID = imageID;
    }

    protected Listing(Parcel in) {
        foodName = in.readString();
        listingID = in.readInt();
        imageLink = in.readString();
        cookID = in.readInt();
        price = in.readDouble();
        location = in.readString();
        imageID = in.readInt();
        imageBytes = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Listing> CREATOR = new Creator<Listing>() {
        @Override
        public Listing createFromParcel(Parcel in) {
            return new Listing(in);
        }

        @Override
        public Listing[] newArray(int size) {
            return new Listing[size];
        }
    };

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

    public void setImageBytes(Bitmap bitmap){
        this.imageBytes=bitmap;
    }

    public Bitmap getImageBytes(){
        return this.imageBytes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(foodName);
        dest.writeInt(listingID);
        dest.writeString(imageLink);
        dest.writeInt(cookID);
        dest.writeDouble(price);
        dest.writeString(location);
        dest.writeInt(imageID);
        dest.writeParcelable(imageBytes, flags);
    }
}
