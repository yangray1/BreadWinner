package com.example.crystalyip.csc301.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * After user has been authenticated, fill data accordingly. I.e. set its userId,
 * and query all personal orders or listings they may have by user id. With this data, navigation
 * menu items can be adjusted according to the number of orders or listings (if user is cook)
 * the user might have.
 */
public class StaticStorage {
    private static int userId;
    private static ArrayList<Order> personalOrders=new ArrayList<>();
    private static ArrayList<String> personalListings=new ArrayList<>();
    private static Map<String, String> mapOrderToRecentStatus=new HashMap<>();

    public static void setUserId(int id){
        userId=id;
    }

    public static int getUserId(){
        return userId;
    }

    public static void addPersonalOrders(Order order){
        personalOrders.add(order);
    }

    public static void clearStorage(){
        personalOrders.clear();
    }

    public static void mapOrderToStatus(String orderNumber, String status){
        mapOrderToRecentStatus.put(orderNumber, status);
    }

    public static String getMappedStatus(String orderNumber){
        return mapOrderToRecentStatus.get(orderNumber);
    }

    public static ArrayList<Order> getPersonalOrders(){
        return personalOrders;
    }

    public static void addPersonalListings(String order){
        personalListings.add(order);
    }

    public static ArrayList<String> getPersonalListings(){
        return personalListings;
    }
}
