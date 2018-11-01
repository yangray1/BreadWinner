package com.example.crystalyip.csc301.ViewHolder;

import android.os.AsyncTask;

import static com.example.crystalyip.csc301.FoodListingNearMe.getHTML;

public class GetRequestTask extends AsyncTask<String, Void, String> {

    protected String doInBackground(String... url){
        String allListings = "";
        String allListingsURL = url[0];

        try {
            allListings = getHTML(allListingsURL);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allListings;
    }

}
