package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodListingNearMe extends Fragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        View view = inflater.inflate(R.layout.fragment_food_near_me, container, false);

        String[] listviewTitle = new String[]{
                "food",
                "more food",
                "even more food",
                "even even more food"
        };

        int[] listviewImage = new int[]{
                R.drawable.rice, R.drawable.rice, R.drawable.rice, R.drawable.rice
        };



        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0 ; i < 4 ; i++){
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_title", listviewTitle[i]);
            hm.put("listview_image", Integer.toString(listviewImage[i]));
            aList.add(hm);
        }

        String[] from = {"listview_title" , "listview_image"};
        int[] to = {R.id.food_name, R.id.food_image};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                aList,
                R.layout.menu_item,
                from, to);

        ListView listingsList = view.findViewById(R.id.lstFoodList);
        listingsList.setClickable(true);
        listingsList.setAdapter(simpleAdapter);
//        listingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // id and position refer to the index of the clicked thing
//                System.out.println("Clicked");
//            }
//        });


        return view;
    }

    @Override
    public void onClick(View v) {
        System.out.println("iasdasnmkdasdmlasdjklasdjkl");
        switch (v.getId()) {
            case R.id.food_image:
                System.out.println("asdasdasd");
                // launch the map fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentFoodDetail()).commit();
                break;
        }
    }



    /////////////////////////////////////////////////////////////////////////////////

//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // Load food listings
//        recycler_menu = (RecyclerView)findViewByID(R.id.recycler_menu);
//        recycler_menu.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recycler_menu.setLayoutManager(layoutManager);
//
//        loadListing();
//    }

    // Fooditem is supposed to reference database (CONNECT WITH DB HERE)
//    private void loadListing() {
//        FirebaseRecyclerAdapter<FoodItem, FoodViewListHolder> adapter = new FirebaseIndexRecyclerAdapter<FoodItem, FoodViewListHolder>(FoodItem.class, R.layout.menu_item, FoodViewListHolder.class, foodItem) {
//            @Override
//            protected void populateViewHolder(FoodViewListHolder viewHolder, FoodItem model, int position) {
//                viewHolder.txtFoodName.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
//                final FoodItem clickItem = model;
//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(com.google.firebase.database.core.view.View view, int position, boolean isLongClick) {
//                        Toast.makeText(FoodListingNearMe.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        };
//
//        recycler_menu.setAdapter(adapter);
//    }
}
