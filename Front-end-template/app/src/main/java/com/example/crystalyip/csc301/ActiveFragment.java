package com.example.crystalyip.csc301;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.DAOs.UserOrdersDAO;
import com.example.crystalyip.csc301.HTTPIneteractions.APICalls;
import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * create an instance of this fragment.
 */
public class ActiveFragment extends Fragment implements View.OnClickListener{

    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rootView = inflater.inflate(R.layout.fragment_active, container, false);

        final SwipeRefreshLayout pullToRefresh = rootView.findViewById(R.id.swipe_layout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrdersFromDB();
                pullToRefresh.setRefreshing(false);
            }
        });

        getOrdersFromDB();

        return rootView;
    }

    private void getOrdersFromDB(){
        UserOrdersDAO userOrdersDAO = new UserOrdersDAO(String.valueOf(StaticStorage.getUserId()));
        try {

            final List<Order> populatedOrders = userOrdersDAO.getPopulatedOrders();
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
                    if (position==0) {
                        layoutId=R.layout.fragment_order_tracking1;
                    } else if (position==1) {
                        layoutId=R.layout.fragment_order_tracking2;
                    } else {
                        layoutId=R.layout.fragment_order_tracking3;
                    }

                    Bundle bundle = new Bundle();
                    OrderTracking trackOrder = new OrderTracking();
                    bundle.putInt("layoutId", (Integer) layoutId);
                    bundle.putInt("index", position);
                    bundle.putInt("listId", populatedOrders.get(position).getListingID());
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




}
