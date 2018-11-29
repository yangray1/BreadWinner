package com.example.crystalyip.csc301;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.StaticStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.getHTTP;
import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.postHTTPJson;

public class AddReview extends Fragment implements RatingBar.OnRatingBarChangeListener {
    private float currRating;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        final View view = inflater.inflate(R.layout.fragment_add_review, container, false);
        currRating=0;
        final RatingBar rate_bar = view.findViewById(R.id.add_new_rating);
        rate_bar.setOnRatingBarChangeListener(this);
        rate_bar.setStepSize((float) 0.5);
        rate_bar.setMax(5);

        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        FloatingActionButton submitButton = view.findViewById(R.id.addBtn);
        final Bundle bundle = this.getArguments();
        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                // take user to the

                try {
                    JSONObject review = new JSONObject();

                    int cookId=bundle.getInt("CookID"); // TODO: change the hardcoded UserID value after login is implemented
                    int reviewerId=StaticStorage.getUserId();
                    String comments=getFood(view);
                    review.put("CookID", cookId);
                    review.put("ReviewerID", reviewerId);
                    review.put("Comments", comments);
                    review.put("Rating", currRating);
                    postHTTPJson("http://18.234.123.109/api/addReview", review);
                    Bundle bundle = new Bundle();

                    bundle.putInt("CookID", cookId);
                    ProfileFragment backToProfile = new ProfileFragment();
                    backToProfile.setArguments(bundle);

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            backToProfile).commit();
                }
                catch(Exception e){
                    e.printStackTrace();
                    //System.out.println("Error adding listing.");
                }



            }
        });


        return view;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingBar.setRating(ratingBar.getRating());
        currRating=ratingBar.getRating()*2;
    }

    /**
     * This class is for setting the rating bar
     */
    class MyBinder implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if(view.getId() == R.id.reviewItemRatingBar){
                Integer intval = (Integer) data;
                float ratingValue = intval;
                RatingBar ratingBar = (RatingBar) view;
                ratingBar.setRating(ratingValue/2);

                return true;
            }
            return false;
        }
    }

    public String getFood(View v){
        EditText getFood = v.findViewById(R.id.add_review);
        return getFood.getText().toString();
    }
}
