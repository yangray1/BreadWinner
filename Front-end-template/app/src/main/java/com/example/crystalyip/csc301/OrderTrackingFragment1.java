package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class OrderTrackingFragment1 extends Fragment implements View.OnClickListener{
    public static String apiURL;/* = "http://18.234.123.109/api/getOrderStatus";*/
    public View rootView;
    public Order order;
    public boolean pended=false;
    public boolean accepted=false;
    public boolean delivered=false;
    public OrderTrackingFragment1(){
        this.apiURL="http://18.234.123.109/api/getOrderStatus";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_tracking1, container, false);
        // link the TextView containing the address to the google maps by creating an onClick action
        TextView directions = (TextView) rootView.findViewById(R.id.location2);
        directions.setOnClickListener(this);

        order=StaticStorage.getPersonalOrders().get(0);
        String status = order.getOrderStatus();
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



        return rootView;
    }

    /**
     * Assigning actions when a TextView is clicked within the fragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location2:
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

    public void addOrderPlaced(){
        if (!rootView.findViewById(R.id.order_placed2).toString().contains("ORDER PLACED") && pended){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.order_placed2);
            tvStatus.setText("ORDER PLACED");
            TextView time = (TextView) rootView.findViewById(R.id.placed_time2);
            time.setText("10:40 EST");
            TextView tvMessage = (TextView) rootView.findViewById(R.id.order_place_message2);
            tvMessage.setText("Your request has been sent.");
            View icon = (View) rootView.findViewById(R.id.order_placed_icon2);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));

        }

        if (!rootView.findViewById(R.id.order_accepted2).toString().contains("ORDER ACCEPTED") && accepted){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.order_accepted2);
            tvStatus.setText("ORDER ACCEPTED");
            TextView time = (TextView) rootView.findViewById(R.id.accepted_time2);
            time.setText("10:43 EST");
            TextView tvMessage = (TextView) rootView.findViewById(R.id.order_accepted_message2);
            tvMessage.setText("Your order hass been accepted and your credit card has been charged.");
            TextView tvTimePickup = (TextView) rootView.findViewById(R.id.time_of_pickup2);
            tvTimePickup.setText("TIME OF PICKUP: __:__ EST");
            TextView location = (TextView) rootView.findViewById(R.id.location2);
            location.setText("LOCATION: " +order.getLocation());
            View icon = (View) rootView.findViewById(R.id.order_accepted_icon2);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));


        }

        if (!rootView.findViewById(R.id.ready_for_pickup2).toString().contains("READY FOR PICKUP") && delivered){
            TextView tvStatus = (TextView) rootView.findViewById(R.id.ready_for_pickup2);
            tvStatus.setText("READY FOR PICKUP");
            TextView time = (TextView) rootView.findViewById(R.id.delivery_time2);
            time.setText("11:00 EST");
            View icon = (View) rootView.findViewById(R.id.ready_for_pickup_icon2);
            icon.setBackground(getResources().getDrawable(R.drawable.circle_bg));

        }

    }
}
