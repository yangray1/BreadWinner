package com.example.crystalyip.csc301;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Listing;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.postHTTPJson;


/**
 * A simple {@link Fragment} subclass.
 */
public class CookListingTracker extends Fragment {


    public CookListingTracker() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cook_listing_tracker, container, false);
        Button closeListing = view.findViewById(R.id.close_listing_button);

        // Initialize the values so compiler don't complain.
        String food_name ="";
        float price = -1;
        String location = "";

        /*
         * Have this so HTTPRequests.getHTTP() doesnt fail for some reason.
         * reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // For msgbox
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);

        // Get stuff from database
        try {

            // returns one JSON listing. Convert that json listing into listing object(that we coded for)
            String stringFormatListings = HTTPRequests.getHTTP("http://18.234.123.109/api/getCookListing/11");

            String allListingsFormatted = HTTPRequests.formatJSONStringFromResponse(stringFormatListings);
            JSONObject listingsJSON = new JSONObject(allListingsFormatted);
            JSONArray listings = listingsJSON.getJSONArray("data");

            // Extract listing items. ** ASSUMES THERES ONLY 1 LISTING PER COOK. **
            JSONObject listing = listings.getJSONObject(0);
            food_name = listing.getString("Food Name");
            price = Integer.parseInt(listing.getString("Price"));
            location = listing.getString("Location");

        } catch (Exception e) {
            e.printStackTrace();
            builder.setTitle("FAILED");
            builder.setMessage("ERROR: Failed to get listing details from database.");
            builder.show();

        }

        // Display listing details.
        displayFoodName(view, food_name);
        displayPrice(view, price);
        displayLocation(view, location);

        // Close the listing.
        closeListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String output = HTTPRequests.getHTTP("http://18.234.123.109/api/closeListing/11");
                    if (output.equals("Success")){
                        builder.setTitle("SUCCESS");
                        builder.setMessage("Successfully closed the listing!");
                        builder.setPositiveButton("OK",null);
                        builder.show();
                    }else{
                        throw new Exception("Failed closing the listing.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        return view;

    }

    private void displayFoodName(View view, String foodName){
        TextView foodNameTextBox = view.findViewById(R.id.food_name);
        foodNameTextBox.setText(foodName);
    }
    private void displayPrice(View view, float price){
        TextView priceTextBox = view.findViewById(R.id.price_per_order);
        priceTextBox.setText("$" + String.format("%.2f", price));
    }

    private void displayLocation(View view, String location){
        TextView locationTextBox = view.findViewById(R.id.location);
        locationTextBox.setText(location);
    }


}
