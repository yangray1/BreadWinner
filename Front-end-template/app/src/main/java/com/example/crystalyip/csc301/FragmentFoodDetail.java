package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentFoodDetail extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_food_detail, container, false);
        Bundle bundle = this.getArguments();
        System.out.println("henlo0");
        if (bundle != null) {
            System.out.println("henlo");
            System.out.println("foodname: "+bundle.getString("imageName"));
            int imageID = bundle.getInt("imageURL");
            ImageView imageView=(ImageView) rootView.findViewById(R.id.image_food);
            imageView.setImageResource(imageID);
            TextView tv = (TextView) rootView.findViewById(R.id.food_name);
            System.out.println("foodname2: "+bundle.getString("imageName"));
            tv.setText(bundle.getString("imageName"));
        }

        return rootView;
    }
}
