package com.example.crystalyip.csc301;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.AvgReviewGet;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    int cookID;
    private View rootView;
    public ProfileFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_profile, container, false);

        cookID =this.getArguments().getInt("CookID");
        RatingBar ratingBar =  rootView.findViewById( R.id.avgRatingBar);
        AvgReviewGet avg = new AvgReviewGet(cookID);
        double amnt = 0;
        try {
            amnt = avg.getAvgRating();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ratingBar.setRating((float)amnt/2);

        TextView ratingTxt = rootView.findViewById(R.id.avgRatingText);
        ratingTxt.setText("Average Rating: " + String.format("%.2f", amnt) + "/10");
        Button rev = rootView.findViewById(R.id.seeReviewsButton);
        rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("cookID", cookID);
                ShowReviews showReviews = new ShowReviews();
                showReviews.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,showReviews).commit();
            }
        });

        Button addReview = rootView.findViewById(R.id.addReviewButton);
        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("CookID", cookID);
                AddReview addReviews = new AddReview();
                addReviews.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,addReviews).commit();
            }
        });
        return rootView;
    }

}
