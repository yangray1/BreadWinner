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
import com.example.crystalyip.csc301.Model.StaticStorage;

public class PersonalProfile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_personal_profile, container, false);


        System.out.println("FUCKMEUP"+StaticStorage.getUserId());

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RatingBar ratingBar = rootView.findViewById(R.id.avgRatingBar);
                AvgReviewGet avg = new AvgReviewGet(StaticStorage.getUserId());
                double[] amnt = {0};
                try {
                    amnt[0] = avg.getAvgRating();
                    ratingBar.setRating((float) amnt[0] / 2);
                    TextView ratingTxt = rootView.findViewById(R.id.avgRatingText);
                    ratingTxt.setText("Average Rating: " + String.format("%.2f", amnt[0]) + "/10");
                    Button rev = rootView.findViewById(R.id.seeReviewsButton);
                    rev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("cookID", StaticStorage.getUserId());
                            ShowReviews showReviews = new ShowReviews();
                            showReviews.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, showReviews).commit();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }
}
