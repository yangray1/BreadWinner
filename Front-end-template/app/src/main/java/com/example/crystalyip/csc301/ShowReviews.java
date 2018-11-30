package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.crystalyip.csc301.DAOs.ListingsDAO;
import com.example.crystalyip.csc301.DAOs.ReviewsDAO;
import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Listing;
import com.example.crystalyip.csc301.Model.Profile;
import com.example.crystalyip.csc301.Model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowReviews extends Fragment implements View.OnClickListener {

    public static String searchURL;/* = "http://18.234.123.109/api/cookReviews/";*/


    /**
     * This "constructor" sets the searchURL to search for listings matching the query.
     */
    @SuppressLint("ValidFragment")
    public ShowReviews() {
        searchURL ="http://18.234.123.109/api/cookReviews/";
    }





    /**
     * Create the ShowReviews view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_reviews, container, false);
        int cookId = this.getArguments().getInt("cookID");
        ReviewsDAO reviewsDAO = new ReviewsDAO(searchURL+Integer.toString(cookId));
        final List<Review> populatedReviews = reviewsDAO.getAllReviews();

        List<HashMap<String, Object>> aList = new ArrayList<>();

        for (int i = 0; i < populatedReviews.size(); i++) {
            HashMap<String, Object> titleImagePair = new HashMap<>();
            SpannableString userFullName = new SpannableString(
                    populatedReviews.get(i).getReviewerFirstName() + " " +
                            populatedReviews.get(i).getReviewerLastName());
            titleImagePair.put("Rating", populatedReviews.get(i).getRating() );
            titleImagePair.put("Full Name",userFullName);
            titleImagePair.put("Comments", populatedReviews.get(i).getComments());
            titleImagePair.put("cookId", populatedReviews.get(i).getCookId());
            titleImagePair.put("reviewerId", populatedReviews.get(i).getReviewerId());
            aList.add(titleImagePair);
        }

        String[] from = {"Comments", "Full Name","Rating"};
        int[] to = {R.id.reviewContent, R.id.firstAndLast, R.id.reviewItemRatingBar};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                aList,
                R.layout.review_item,
                from, to);
        simpleAdapter.setViewBinder(new MyBinder());
        final ListView reviewsList = view.findViewById(R.id.reviewList);
        reviewsList.setClickable(false);
        reviewsList.setAdapter(simpleAdapter);
        reviewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Bundle bundle = new Bundle();
//                HashMap<String, Object> obj = (HashMap<String, Object>) reviewsList.getAdapter().getItem(position);
//                bundle.putString("foodName", (String) obj.get("foodName"));
//                bundle.putString("foodLocation", (String) obj.get("foodLocation"));
//                bundle.putInt("imageURL", (Integer) obj.get("Image Drawable"));
//                bundle.putString("Description", (String) obj.get("stringDescription"));
//                bundle.putInt("cookID", (Integer) obj.get("cookID"));
//                bundle.putInt("listingID", (Integer) obj.get("listingID"));
//                FragmentFoodDetail foodDetail = new FragmentFoodDetail();
//                foodDetail.setArguments(bundle);
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//
//                ft.replace(R.id.fragment_container,
//                        foodDetail);
//                ft.addToBackStack(null);
//                ft.commit();
//                // id and position refer to the index of the clicked thing
//                System.out.println("Clicked the item at position " + position + ". ID is " + id);
            }
        });

        return view;
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

    @Override
    public void onClick(View v) {

    }


}