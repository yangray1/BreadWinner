package com.example.crystalyip.csc301.DAOs;

import com.example.crystalyip.csc301.HTTPIneteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Listing;
import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListingsDAO {
    private String apiURL;
    private List<Listing> allListings = new ArrayList<>();
    public ListingsDAO(String url){
        this.apiURL=url;
        setUpListingsList();

    }

    private void setUpListingsList(){
        String stringFormatListings = "";

        try {
            stringFormatListings = APICalls.getHTTP(apiURL);
            String allListingsFormatted = APICalls.formatAPIString(stringFormatListings);
            JSONObject listingsJSON = new JSONObject(allListingsFormatted);
            JSONArray listings = listingsJSON.getJSONArray("data");

            for (int i = 0; i < listings.length(); i++) {
                JSONObject listing = listings.getJSONObject(i);

                Listing listingToAdd = new Listing(
                        listing.getString("Food Name"),
                        listing.getInt("ListingID"),
                        listing.getString("Image"),
                        listing.getInt("CookID"),
                        listing.getDouble("Price"),
                        listing.getString("Location"),
                        R.drawable.rice);

                allListings.add(listingToAdd);
            }
        } catch (Exception e) { // return what we have so far, even if it's just an empty list
            e.printStackTrace();
        }
    }

    public final List<Listing> getAllListings(){
        return this.allListings;
    }
}
