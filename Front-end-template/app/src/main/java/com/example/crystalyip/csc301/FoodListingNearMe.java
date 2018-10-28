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
import android.widget.ImageView;
import android.widget.TextView;

public class FoodListingNearMe extends Fragment implements View.OnClickListener{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.menu_item, container, false);
        ImageView directions = (ImageView) view.findViewById(R.id.food_image);
        directions.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.food_image:
                // launch the map fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentFoodDetail()).commit();
                break;
        }
    }

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
