package com.example.crystalyip.csc301.Model;

import java.math.BigDecimal;
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
    private static String firstName;
    private static String lastName;

    public static void setUserId(int id){
        userId=id;
    }

    public static int getUserId(){
        return userId;
    }

    public static void refreshAllPersonalOrders(List<Order> orders){
        personalOrders.clear();
        personalOrders.addAll(orders);
    }

    public static ArrayList<Order> getPersonalOrders(){
        return personalOrders;
    }

    public static void setFirstName(String setName){
        firstName=setName;
    }

    public static void setLastName(String setName){
        lastName=setName;
    }

    public static String getFirstName(){
        return firstName;
    }

    public static String getLastName(){
        return lastName;
    }

}
