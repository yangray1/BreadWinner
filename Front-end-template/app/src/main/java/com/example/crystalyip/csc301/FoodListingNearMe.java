package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.crystalyip.csc301.Model.Listing;
import com.example.crystalyip.csc301.ViewHolder.GetRequestTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FoodListingNearMe extends Fragment implements View.OnClickListener{

    public static String allListingsURL = "http://18.234.123.109/api/getAllListings";

    public static String getHTML(String urlToRead) throws Exception {
        // reference: https://stackoverflow.com/questions/1485708/how-do-i-do-a-http-get-in-java
//        StringBuilder result = new StringBuilder();
//        URL url = new URL(urlToRead);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String line;
//        while ((line = rd.readLine()) != null) {
//            result.append(line);
//        }
//        rd.close();
//
//        return result.toString();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget= new HttpGet(urlToRead);

        HttpResponse response = httpclient.execute(httpget);

        if(response.getStatusLine().getStatusCode()==200){
            String server_response = EntityUtils.toString(response.getEntity());
            return server_response;
        } else {
            System.out.println("damn");
        }
        return "";

    }

    public static String formatAPIString(String apiString){
        String remove_begin_slash = apiString.replace("\"{", "{");
        String remove_end_slash = remove_begin_slash.replace("}\"", "}");
        String remove_extra_slashes = remove_end_slash.replace("\\", "");
        return remove_extra_slashes;
    }

    private static ArrayList<Listing> getAllListings(String allListingsFormatted) {
        ArrayList<Listing> allListings = new ArrayList<Listing>();

        try {
            JSONObject listingsJSON = new JSONObject(allListingsFormatted);
            JSONArray listings = listingsJSON.getJSONArray("data");

            for (int i = 0 ; i < listings.length() ; i++){
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
        }
        catch (Exception e){ // return what we have so far, even if it's just an empty list
            return allListings;
        }

        return allListings;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View view = inflater.inflate(R.layout.fragment_food_near_me, container, false);

//        String allListings = "";

//        new GetRequestTask().execute(allListingsURL);

        String allListings = "";

        try {
            allListings = getHTML(allListingsURL);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String allListingsFormatted = formatAPIString(allListings);

        System.out.println(allListingsFormatted);

        ArrayList<Listing> populatedListings = getAllListings(allListingsFormatted);

        ArrayList<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0 ; i < populatedListings.size() ; i++){
            HashMap<String, String> titleImagePair = new HashMap<String, String>();
            titleImagePair.put("Food Name", populatedListings.get(i).foodName); // TODO: use getters
            titleImagePair.put("Food Image Drawable", Integer.toString(populatedListings.get(i).imageID)); // TODO: use getters
            aList.add(titleImagePair);
            System.out.println(populatedListings.get(i).foodName);
        }

        String[] from = {"Food Name" , "Food Image Drawable"};
        int[] to = {R.id.food_name, R.id.food_image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                aList,
                R.layout.menu_item,
                from, to);

        ListView listingsList = view.findViewById(R.id.lstFoodList);
        listingsList.setClickable(true);
        listingsList.setAdapter(simpleAdapter);
//        listingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // id and position refer to the index of the clicked thing
//                System.out.println("Clicked");
//            }
//        });


        return view;
    }


    @Override
    public void onClick(View v) {
        System.out.println("iasdasnmkdasdmlasdjklasdjkl");
        switch (v.getId()) {
            case R.id.food_image:
                System.out.println("asdasdasd");
                // launch the map fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentFoodDetail()).commit();
                break;
        }
    }



    /////////////////////////////////////////////////////////////////////////////////

//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Load food listings
//        recycler_menu = (RecyclerView)findViewByID(R.id.recycler_menu);
//        recycler_menu.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recycler_menu.setLayoutManager(layoutManager);
//
//        loadListing();
//    }

    // Fooditem is supposed to reference database (CONNECT WITH DB HERE)
//    private void loadListing() {
//        FirebaseRecyclerAdapter<FoodItem, FoodViewListHolder> adapter = new FirebaseIndexRecyclerAdapter<FoodItem, FoodViewListHolder>(FoodItem.class, R.layout.menu_item, FoodViewListHolder.class, foodItem) {
//            @Override
//            protected void populateViewHolder(FoodViewListHolder viewHolder, FoodItem model, int position) {
//                viewHolder.txtFoodName.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
//                final FoodItem clickItem = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(com.google.firebase.database.core.view.View view, int position, boolean isLongClick) {
//                        Toast.makeText(FoodListingNearMe.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        };
//
//        recycler_menu.setAdapter(adapter);
//    }
}
