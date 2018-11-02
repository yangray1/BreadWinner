package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.Model.Listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ShowListings extends Fragment implements View.OnClickListener{

    public static String searchURL;/* = "http://18.234.123.109/api/getAllListings";*/

    /**
     * This "constructor" sets the searchURL to display every listing. */
    public ShowListings() {
        searchURL = "http://18.234.123.109/api/getAllListings";
    }

    /**
     * This "constructor" sets the searchURL to search for listings matching the query. */
    @SuppressLint("ValidFragment")
    public ShowListings(String query) {
        String formattedQuery = query.replace("\\s","+");
        searchURL = "http://18.234.123.109/api/search/" + formattedQuery;
    }

    /**
     * Sends a GET request to the url at urlToRead, and return the string representing the response.
     *
     * @param urlToRead url to GET from
     * @return String response of GET request
     * */
    public static String getHTML(String urlToRead) throws Exception {
        // reference: https://stackoverflow.com/questions/34691175/how-to-send-httprequest-and-get-json-response-in-android/34691486
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget= new HttpGet(urlToRead);

        HttpResponse response = httpclient.execute(httpget);

        if(response.getStatusLine().getStatusCode()==200){
            String server_response = EntityUtils.toString(response.getEntity());
            return server_response;
        } else {
            System.out.println("no response from server");
        }
        return "";
    }

    /**
     * Format the string returned from a GET request to our API, ridding it of newline characters
     * and redundant backslashes.
     *
     * @param apiString (JSON formatted) string to format
     * @return correctly formatted string (that can be parsed with a JSON parser)
     * */
    public static String formatAPIString(String apiString){
        String remove_new_line = apiString.replace("\\n", "\\");
        String remove_begin_slash = remove_new_line.replace("\"{", "{");
        String remove_end_slash = remove_begin_slash.replace("}\"", "}");
        String remove_extra_slashes = remove_end_slash.replace("\\", "");
        return remove_extra_slashes;
    }

    /**
     * Return an List of Listing objects fetched from parsing allListingsFormatted as a JSON object
     *
     * @param allListingsFormatted JSON parsable string representing listing data
     * @return List of Listings from parsing the input string
     * */
    private static List<Listing> getAllListings(String allListingsFormatted) {
        List<Listing> allListings = new ArrayList<Listing>();

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

    /**
     * Create the "Near me" view
     * */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_listings, container, false);


        String allListings = "";

        try {
            allListings = getHTML(searchURL);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String allListingsFormatted = formatAPIString(allListings);

        final List<Listing> populatedListings = getAllListings(allListingsFormatted);

        List<HashMap<String, Object>> aList = new ArrayList<>();

        for (int i = 0 ; i < populatedListings.size() ; i++){
            HashMap<String, Object> titleImagePair = new HashMap<>();
            titleImagePair.put("Food Image Drawable", populatedListings.get(i).getImageID());
            titleImagePair.put("Food Name", populatedListings.get(i).getFoodName());
            aList.add(titleImagePair);
        }

        String[] from = {"Food Name" , "Food Image Drawable"};
        int[] to = {R.id.food_name, R.id.food_image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                aList,
                R.layout.menu_item,
                from, to);

        final ListView listingsList = view.findViewById(R.id.lstFoodList);
        listingsList.setClickable(true);
        listingsList.setAdapter(simpleAdapter);
        listingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                HashMap<String, Object> obj = (HashMap<String, Object>) listingsList.getAdapter().getItem(position);

                bundle.putInt("imageURL", (Integer) obj.get("Food Image Drawable"));
                bundle.putString("imageName", (String) obj.get("Food Name"));
                FragmentFoodDetail foodDetail = new FragmentFoodDetail();
                foodDetail.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        foodDetail).commit();
                // id and position refer to the index of the clicked thing
                System.out.println("Clicked the item at position " + position + ". ID is " + id);
            }
        });

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
