package com.example.crystalyip.csc301.HTTPIneteractions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPRequests {
    private static String domain;
    private static String cookIDCol = "CookID";
    private static String foodNameCol = "Food Name";
    private static String priceCol = "Price";
    private static String locationCol = "Location";
    private static String imgCol = "Image";
    private static final String prefix = "http://";

    /**
     *   Creates an http POST request to add a listing to the database.
     * @param addLocation the location of the resource that responds to requests.
     * @param cookId user id of the cook who made the listing.
     * @param foodName name of food to be added.
     * @param  price price of food
     * @param  location location to pick up
     * @param  imgLocation URL of image of listing
     *
     *
     **/
    public static void addListingReq(String addLocation, int cookId, String foodName, double price, String location, String imgLocation, String[] tagList ) throws IOException, JSONException {


        //creating json to send
        JSONObject listingData = new JSONObject();
        listingData.put(cookIDCol, cookId);
        listingData.put(foodNameCol, foodName);
        listingData.put(priceCol, price);
        listingData.put(locationCol, location);
        listingData.put(imgCol, imgLocation);

        JSONArray tagJSONList = new JSONArray();
        if(tagList != null) {
            for (int x = 0; x < tagList.length; x++) {
                tagJSONList.put(tagList[x]);
            }
        }
        listingData.put("tags", tagJSONList);

        //seending json data via POST
        URL url = new URL(prefix + domain + addLocation);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect(); // Note the connect() here
        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

        osw.write(listingData.toString());
        osw.flush();
        osw.close();

        conn.disconnect();

    }

    /**
     * Sends an http request to cancel an order
     * @param clientId the client who wants to cancel
     * @param listingId the listing that is being canceled
     * @param  cancelLoc location of canceled resource for URL
     */
    public static void cancelReq(String cancelLoc, int clientId, int listingId) throws IOException {
        URL url = new URL(prefix + domain + cancelLoc + "/" + Integer.toString(clientId) + "/" + Integer.toString(listingId));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        conn.disconnect();
    }

    /**
     * Sends http req to get all listings
     * @param listingsLoc url resource location
     * @return the listings as a json array converted to string
     */
    public static String  getAllListings(String listingsLoc) throws IOException {
        URL url = new URL(prefix + domain + "/" + listingsLoc);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        //Getting data from all listings
        BufferedReader br;
        if (200 <= responseCode && responseCode < 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        conn.disconnect();

        return sb.toString();
    }

    /**
     * Sends http req to get user orders
     * @param ordersLoc url location for orders
     * @param clientId client id of user whose orders are to be obtained
     * @return the list of orders as a JSON string
     */
    public static String getUserOrders(String ordersLoc, int clientId) throws IOException {
        URL url = new URL(prefix + domain + ordersLoc + "/" + Integer.toString(clientId));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        //Getting data from all listings
        BufferedReader br;
        if (200 <= responseCode && responseCode < 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        conn.disconnect();

        return sb.toString();
    }

    /**
     * Sends an http request to complete an order
     * @param clientId the client who wants to cancel
     * @param listingId the listing that is being canceled
     * @param  completeLoc location of canceled resource for URL
     */
    public static void completeReq(String completeLoc, int clientId, int listingId) throws IOException {
        URL url = new URL(prefix + domain + completeLoc + "/" + Integer.toString(clientId) + "/" + Integer.toString(listingId));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        conn.disconnect();
    }

    /**
     * Makes an http get request to search and returns results as a json
     *
     * @param searchLoc url location of search resource
     * @param searchQuery what is being searched
     * @return
     * @throws IOException
     */
    public static String searchReq(String searchLoc, String searchQuery) throws IOException {
        URL url = new URL(prefix + domain + searchLoc + "/" + searchQuery);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        //Getting data from all listings
        BufferedReader br;
        if (200 <= responseCode && responseCode < 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }

        conn.disconnect();

        return sb.toString();
    }
    

}
