package com.example.crystalyip.csc301;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderTracking extends Fragment implements View.OnClickListener{
    public static String apiURL;/* = "http://18.234.123.109/api/getOrderStatus";*/
    public View rootView;
    public Order order;
    public boolean pended=false;
    public boolean accepted=false;
    public boolean delivered=false;
    public String status;
    private Bundle bundle;
    public OrderTracking(){
        this.apiURL="http://18.234.123.109/api/getOrderStatus/11/";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        bundle = this.getArguments();

        rootView = inflater.inflate((Integer) bundle.getInt("layoutId"), container, false);

        final SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipe_layout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });


        // link the TextView containing the address to the google maps by creating an onClick action
        TextView directions = (TextView) rootView.findViewById(R.id.location);
        directions.setOnClickListener(this);

        order=StaticStorage.getPersonalOrders().get(bundle.getInt("index"));
        int listingId = order.getListingID();
        status = order.getOrderStatus();
        setStatusBooleans();
        return rootView;
    }

    /**
     * Assigning actions when a TextView is clicked within the fragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                // launch the map fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapFragment()).commit();
                break;
        }
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

    public String orderCanclled(){
        try {//TODO:FIX HARDCODEDVALUE
            System.out.println("thelistingid"+bundle.getInt("listId"));
            String cancelled = HTTPRequests.getHTTP("http://18.234.123.109/api/cancel/11/"+bundle.getInt("listId"));
            if (cancelled!="Failed")
                return "ORDER CANCELLED";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "CANCEL FAILED";
    }

    public void addOrderPlaced(){



        if (!rootView.findViewById(R.id.order_placed).toString().contains("ORDER PLACED") && (pended || accepted || delivered)){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.order_placed);
            tvStatus.setText("ORDER PLACED");
            TextView time = (TextView) rootView.findViewById(R.id.placed_time);
            time.setText("10:40 EST");
            TextView tvMessage = (TextView) rootView.findViewById(R.id.order_place_message);
            tvMessage.setText("Your request has been sent.");
            View icon = (View) rootView.findViewById(R.id.order_placed_icon);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));
            if (!accepted){
                TextView cancel = rootView.findViewById(R.id.cancel_order);
                cancel.setText("CANCEL ORDER");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setCancelable(true);
                        builder.setMessage(orderCanclled());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.getWindow().setLayout(200, 100);
                        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.panels);
                    }
                });
            }

        }

        if (!rootView.findViewById(R.id.order_accepted).toString().contains("ORDER ACCEPTED") && (accepted || delivered)){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.order_accepted);
            tvStatus.setText("ORDER ACCEPTED");
            TextView time = (TextView) rootView.findViewById(R.id.accepted_time);
            time.setText("10:43 EST");
            TextView tvMessage = (TextView) rootView.findViewById(R.id.order_accepted_message);
            tvMessage.setText("Your order hass been accepted and your credit card has been charged.");
            TextView tvTimePickup = (TextView) rootView.findViewById(R.id.time_of_pickup);
            tvTimePickup.setText("TIME OF PICKUP: __:__ EST");
            TextView location = (TextView) rootView.findViewById(R.id.location);
            location.setText("LOCATION: " +order.getLocation());
            View icon = (View) rootView.findViewById(R.id.order_accepted_icon);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));


        }
        System.out.println(delivered);
        if (!rootView.findViewById(R.id.ready_for_pickup).toString().contains("READY FOR PICKUP") && delivered){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.ready_for_pickup);
            tvStatus.setText("READY FOR PICKUP");
            TextView time = (TextView) rootView.findViewById(R.id.delivery_time);
            time.setText("11:00 EST");
            View icon = (View) rootView.findViewById(R.id.ready_for_pickup_icon);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));

        }

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
     * @param orderFormatted JSON parsable string representing listing data
     * @return List of Listings from parsing the input string
     * */
    private void getNewStatus(String orderFormatted) {

        try {
            System.out.println("THE JSON FILE:"+orderFormatted);

            JSONObject jsonOrder = new JSONObject(orderFormatted);

            status=jsonOrder.getString("Status");
            System.out.println("NEW STATUS: "+ status);

        }
        catch (Exception e){ // return what we have so far, even if it's just an empty list
            e.printStackTrace();
        }

    }


    public void refreshData(){
        System.out.println("refreshing");
        String orders = "";
        try {
            System.out.println("THE URL IS"+ this.apiURL+order.getListingID());
            orders = getHTML(this.apiURL+order.getListingID());
            System.out.println("THE RESPONSE WAS: "+orders);
            String orderFormatted = formatAPIString(orders);
            getNewStatus(orderFormatted);
            setStatusBooleans();
        } catch (Exception e){
            e.printStackTrace();
        }



    }

    public void setStatusBooleans(){
        if (status.toLowerCase().equals("pending")){
            pended=true;
        }

        if (status.toLowerCase().equals("in progress")){
            accepted=true;
        }

        if (status.toLowerCase().equals(("ready for delivery"))){
            delivered=true;
        }

        addOrderPlaced();
    }
}
