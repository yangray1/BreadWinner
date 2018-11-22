package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.Model.Listing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.postHTTPJson;

public class AddListing extends Fragment implements View.OnClickListener{
    @Override
    public void onClick(View v) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final View view = inflater.inflate(R.layout.add_listing, container, false);

        Button submitButton = view.findViewById(R.id.add_listing_button);

        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String foodName = getFood(view);
                String price = getPrice(view);
                String location = getLocation(view);
                JSONArray tags = getTags(view);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);

                if (checkIfFloat(price)){
                    // send a post request
                    try {
                        JSONObject listing = new JSONObject();
                        listing.put("CookID", 1); // TODO: change the hardcoded UserID value after login is implemented
                        listing.put("Food Name", foodName);
                        listing.put("Price", Float.parseFloat(price));
                        listing.put("Location", location);
                        listing.put("Image", "greatest image 2018");
                        listing.put("tags", tags);

                        postHTTPJson("http://18.234.123.109/api/add", listing); // "http://18.234.123.109/api/add"

                        builder.setTitle("Successfully added listing.");
                        builder.show();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        //System.out.println("Error adding listing.");
                    }


                }
                else{
                    // inflate a popup. reference: https://www.youtube.com/watch?v=7vWoi8j5vL4
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(true);
                    builder.setTitle("Invalid parameters");
                    builder.setMessage("Either you're not logged in, or you've supplied an invalid price. Try again.");
                    builder.show();
                }

            }
        });


        return view;
    }

    public /*List<String>*/JSONArray getTags(View view) {
        EditText getTags = view.findViewById(R.id.get_tags);
        String separatedTags = getTags.getText().toString();
        String[] tagsArray = separatedTags.split(" ");

        JSONArray arr = new JSONArray();
        for (int i = 0 ; i < tagsArray.length ; i++){
            arr.put(tagsArray[i]);
        }

        return arr;
    }

    public String getFood(View v){
        EditText getFood = v.findViewById(R.id.get_food_name);
        return getFood.getText().toString();
    }

    public String getPrice(View v){
        EditText getPrice = v.findViewById(R.id.get_price);
        return getPrice.getText().toString();
    }

    public String getLocation(View v){
        EditText getLocation = v.findViewById(R.id.get_location);
        return getLocation.getText().toString();
    }

    public boolean checkIfFloat(String toCheck){
        try{
            Float.parseFloat(toCheck);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
