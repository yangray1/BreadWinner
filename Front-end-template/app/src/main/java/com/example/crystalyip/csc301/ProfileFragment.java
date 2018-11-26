package com.example.crystalyip.csc301;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.AvgReviewGet;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    int cookID;

    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cookID =savedInstanceState.getInt("CookID");
        RatingBar ratingBar =  getView().findViewById( R.id.avgRatingBar);
        AvgReviewGet avg = new AvgReviewGet(cookID);
        double amnt = 0;
        try {
            amnt = avg.getAvgRating();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ratingBar.setRating((float)amnt);

        TextView ratingTxt = getView().findViewById(R.id.avgRatingText);
        ratingTxt.setText("Average Rating: " + String.format("%.2f", amnt) + "/10");

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
