package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.DAOs.ListingsDAO;
import com.example.crystalyip.csc301.HTTPIneteractions.APICalls;
import com.example.crystalyip.csc301.Model.Listing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShowListings extends Fragment implements View.OnClickListener {

    public static String searchURL;/* = "http://18.234.123.109/api/getAllListings";*/

    /**
     * This "constructor" sets the searchURL to display every listing.
     */
    public ShowListings() {
        searchURL = "http://18.234.123.109/api/getAllListings";
    }

    /**
     * This "constructor" sets the searchURL to search for listings matching the query.
     */
    @SuppressLint("ValidFragment")
    public ShowListings(String query) {
        String formattedQuery = query.replace("\\s", "+");
        searchURL = "http://18.234.123.109/api/search/" + formattedQuery;
    }


    /**
     * Create the "Near me" view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_listings, container, false);
        ListingsDAO listingsDAO = new ListingsDAO(searchURL);
        final List<Listing> populatedListings = listingsDAO.getAllListings();

        List<HashMap<String, Object>> aList = new ArrayList<>();

        for (int i = 0; i < populatedListings.size(); i++) {
            HashMap<String, Object> titleImagePair = new HashMap<>();
            titleImagePair.put("Image Drawable", populatedListings.get(i).getImageID());
            String foodDetail = populatedListings.get(i).getFoodName() + "\n  " + populatedListings.get(i).getLocation();
            SpannableString spannable = new SpannableString("  $" + populatedListings.get(i).getPrice() + " " + foodDetail);
            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, foodDetail.indexOf("\n"), 0);
            titleImagePair.put("Description", spannable);
            titleImagePair.put("stringDescription", foodDetail);
            aList.add(titleImagePair);
        }

        String[] from = {"Description", "Image Drawable"};
        int[] to = {R.id.food_description, R.id.food_image};

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

                bundle.putInt("imageURL", (Integer) obj.get("Image Drawable"));
                bundle.putString("Description", (String) obj.get("stringDescription"));
                FragmentFoodDetail foodDetail = new FragmentFoodDetail();
                foodDetail.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.replace(R.id.fragment_container,
                        foodDetail);
                ft.addToBackStack(null);
                ft.commit();
                // id and position refer to the index of the clicked thing
                System.out.println("Clicked the item at position " + position + ". ID is " + id);
            }
        });

        return view;
    }


    @Override
    public void onClick(View v) {

    }


}