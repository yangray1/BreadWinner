package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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

        if (bundle != null) {
            int imageID = bundle.getInt("imageURL");
            ImageView imageView=(ImageView) rootView.findViewById(R.id.image_food);
            imageView.setImageResource(imageID);

            TextView tv = (TextView) rootView.findViewById(R.id.food_description);
            String foodDescription = bundle.getString("Description");
            SpannableString descriptions = new SpannableString(foodDescription);
            descriptions.setSpan(new RelativeSizeSpan(1.3f), 0, foodDescription.indexOf("\n"), 0);
            tv.setText(descriptions);

        }

        return rootView;
    }


}
