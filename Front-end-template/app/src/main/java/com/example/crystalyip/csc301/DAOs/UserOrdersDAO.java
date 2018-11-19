package com.example.crystalyip.csc301.DAOs;

import com.example.crystalyip.csc301.HTTPIneteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserOrdersDAO {
    private List<Order> allOrders = new ArrayList<>();
    public String apiURL;/* = "http://18.234.123.109/api/...";*/

    public UserOrdersDAO(){
        this.apiURL="http://18.234.123.109/api/getAllOrders/11";
        String orders = "";
        StaticStorage.clearStorage();
        try {
            orders = HTTPRequests.getHTTP(this.apiURL);
            String allOrdersFormatted=HTTPRequests.formatJSONStringFromResponse(orders);
            JSONObject ordersJSON = new JSONObject(allOrdersFormatted);
            JSONArray ordersArray = ordersJSON.getJSONArray("data");

            for (int i = 0 ; i < ordersArray.length() ; i++){
                JSONObject order = ordersArray.getJSONObject(i);

                Order orderToAdd = new Order(
                        order.getString("Status"),
                        order.getInt("ListingID"),
                        order.getInt("ClientID"),
                        order.getString("Food Name"),
                        order.getString("Location"));

                allOrders.add(orderToAdd);
                StaticStorage.addPersonalOrders(orderToAdd);
            }
        }
        catch (Exception e){ // return what we have so far, even if it's just an empty list
            e.printStackTrace();
        }
    }

    public final List<Order> getPopulatedOrders(){
        return this.allOrders;
    }
}
