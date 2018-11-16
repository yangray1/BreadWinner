package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * create an instance of this fragment.
 */
public class ActiveFragment extends Fragment implements View.OnClickListener{
    public static String apiURL;/* = "http://18.234.123.109/api/...";*/
    public ActiveFragment(){
        this.apiURL="http://18.234.123.109/api/getAllOrders/11";
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View rootView = inflater.inflate(R.layout.fragment_active, container, false);

        String orders = "";

        try {
            orders = getHTML(this.apiURL);
            String allOrdersFormatted=formatAPIString(orders);
            StaticStorage.clearStorage();
            final List<Order> populatedOrders = getAllOrders(allOrdersFormatted);


            List<HashMap<String, Object>> aList = new ArrayList<>();
            for (int i = 0 ; i < populatedOrders.size() ; i++){
                HashMap<String, Object> titleImagePair = new HashMap<>();
                titleImagePair.put("Description", "  "+populatedOrders.get(i).getFoodName().toUpperCase()+"\n  "+populatedOrders.get(i).getOrderStatus().toLowerCase());
                aList.add(titleImagePair);
            }

            String[] from = {"Description"};
            int[] to = {R.id.order_description};

            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                    aList,
                    R.layout.active_order_item,
                    from, to);

            final ListView listingsList = rootView.findViewById(R.id.listOrders);
            listingsList.setClickable(true);
            listingsList.setAdapter(simpleAdapter);
            listingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int layoutId;
                    int index;
                    if (position==0) {
                        layoutId=R.layout.fragment_order_tracking1;
                        index=0;
                    } else if (position==1) {
                        layoutId=R.layout.fragment_order_tracking2;
                        index=1;
                    } else {
                        layoutId=R.layout.fragment_order_tracking3;
                        index=2;
                    }

                    Bundle bundle = new Bundle();
                    OrderTracking trackOrder = new OrderTracking();
                    bundle.putInt("layoutId", (Integer) layoutId);
                    bundle.putInt("index", index);
                    trackOrder.setArguments(bundle);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container,
                            trackOrder);
                    ft.addToBackStack(null);
                    ft.commit();

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }



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
     * @param allOrdersFormatted JSON parsable string representing listing data
     * @return List of Listings from parsing the input string
     * */
    private static List<Order> getAllOrders(String allOrdersFormatted) {
        List<Order> allOrders = new ArrayList<>();

        try {
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
            return allOrders;
        }

        return allOrders;
    }


}
