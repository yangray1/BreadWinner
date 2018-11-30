package com.example.crystalyip.csc301;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.AvgReviewGet;
import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Profile;
import com.example.crystalyip.csc301.Model.StaticStorage;

public class PersonalProfile extends Fragment {

    private View rootView;
    public PersonalProfile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =inflater.inflate(R.layout.fragment_personal_profile, container, false);


        TextView name = rootView.findViewById(R.id.chef_name);
        name.setText(StaticStorage.getFirstName());
        TextView about = rootView.findViewById(R.id.chef_description);
        about.setText(StaticStorage.getAbout());
        RatingBar ratingBar =  rootView.findViewById( R.id.avgRatingBar);
        AvgReviewGet avg = new AvgReviewGet(StaticStorage.getUserId());
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
                bundle.putInt("cookID", StaticStorage.getUserId());
                ShowReviews showReviews = new ShowReviews();
                showReviews.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,showReviews).commit();
            }
        });

        return rootView;
    }

}
