package com.example.crystalyip.csc301.HTTPInteractions;

import android.content.res.Resources;

import com.example.crystalyip.csc301.Model.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.postHTTPJson;


/**
 * This class places a single order
 */
public class OrderPlacer {
    private static String makeOrderURL = "http://18.234.123.109/api/addOrder/";

    //column names for table
    private static String clientCol = "ClientID";
    private static String listingCol = "ListingID";
    private static String timeCol = "Time of Order";
    private static String quantCol = "quantity";

    //date format for table
    private static String dateFormatString = "yyyy-MM-dd HH:mm:ss";


    private Order orderToPlace;

    public OrderPlacer(Order orderToPlace){
        this.orderToPlace = orderToPlace;
    }

    /**
     * Creates a json containing the info to send to backend that adds order
     * to database and returns it
     *
     * @return A JSONObject with the col variables as keys
     */
    private JSONObject makeJSON() {
        //formatting date string
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormatString);
        String dateString = dateFormatter.format(this.orderToPlace.getTimeOfOrder()).toString();


        JSONObject orderInfoJSON = new JSONObject();
        try {
            orderInfoJSON.put(clientCol, this.orderToPlace.getClientID());
            orderInfoJSON.put(listingCol, this.orderToPlace.getListingID());
            orderInfoJSON.put(quantCol, this.orderToPlace.getQuantity());
            orderInfoJSON.put(timeCol, dateString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orderInfoJSON;

    }

    /**
     * Makes the order by sending request to server, adding order to db
     * @throws IOException
     * @throws Resources.NotFoundException
     */
    public String makeOrder() throws IOException, Resources.NotFoundException {
        return postHTTPJson(makeOrderURL, makeJSON());

    }






}
