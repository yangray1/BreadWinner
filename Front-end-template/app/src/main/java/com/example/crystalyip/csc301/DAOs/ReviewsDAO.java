package com.example.crystalyip.csc301.DAOs;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Listing;
import com.example.crystalyip.csc301.Model.Review;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewsDAO {
    private String apiURL;
    private List<Review> allReviews = new ArrayList<>();
    public ReviewsDAO(String url){
        this.apiURL=url;
        setUpReviewList();

    }

    private void setUpReviewList(){
        String stringFormatListings = "";

        try {
            stringFormatListings = HTTPRequests.getHTTP(apiURL);
            String allReviewsFormatted = HTTPRequests.formatJSONStringFromResponse(stringFormatListings);
            JSONArray reviews = new JSONArray(allReviewsFormatted);

            for (int i = 0; i < reviews.length(); i++) {
                JSONObject review = reviews.getJSONObject(i);
                Review reviewToAdd = new Review(
                        review.getInt("CookID"),
                        review.getInt("ReviewerID"),
                        review.getInt("Rating"),
                        review.getString("Comments"),
                        review.getString("FName"),
                        review.getString("LName")
                );

                allReviews.add(reviewToAdd);
            }
        } catch (Exception e) { // return what we have so far, even if it's just an empty list
            e.printStackTrace();
        }
    }

    public final List<Review> getAllReviews(){
        return this.allReviews;
    }
}
