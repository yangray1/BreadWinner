package com.example.crystalyip.csc301;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.DAOs.ListingsDAO;
import com.example.crystalyip.csc301.DAOs.UserOrdersDAO;
import com.example.crystalyip.csc301.Model.Listing;
import com.example.crystalyip.csc301.Model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class OrderHistoryFragment extends Fragment {

    public ListingsDAO listingsDAO;
    public UserOrdersDAO userOrdersDAO;
    public String searchURL;

    /**
     * Create the order_history view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("on create view");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);

        SimpleDateFormat dateFormat = new SimpleDateFormat ("MMM dd yyyy 'at' hh:mm:ss a");

        searchURL = "http://18.234.123.109/api/getAllListings";
        userOrdersDAO = new UserOrdersDAO();
        //listings are required to pick out a specific picture
        listingsDAO = new ListingsDAO(searchURL);
        //Get orders
        final List<Order> populatedOrders = userOrdersDAO.getPopulatedOrders();

        List<HashMap<String, Object>> aList = new ArrayList<>();
        Listing currentListing;


        for (int i = 0; i < populatedOrders.size(); i++) {
            HashMap<String, Object> order = new HashMap<>();

            Date time = populatedOrders.get(i).getTimeOfOrder();

            currentListing = findListingByID(populatedOrders.get(i).getListingID());

            order.put("Image Drawable", currentListing.getImageID());
            String orderDetail = currentListing.getFoodName() + "\n  " + dateFormat.format(time);
            SpannableString spannable = new SpannableString(orderDetail);
            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, orderDetail.indexOf("\n"), 0);

            order.put("Description", spannable);
            order.put("stringDescription", orderDetail);
            aList.add(order);
        }

        String[] from = {"Description", "Image Drawable"};
        int[] to = {R.id.food_description, R.id.food_image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                aList,
                R.layout.menu_item,
                from, to);

        final ListView orderHistoryList = view.findViewById(R.id.ordersListView);
        orderHistoryList.setAdapter(simpleAdapter);


        return view;
    }

    private Listing findListingByID(int ID) {
        List<Listing> listingList = listingsDAO.getAllListings();
        for (Listing listing : listingList) {
            if (listing.getListingID() == ID) {
                return listing;
            }
        }
        return null;
    }
}