package com.example.crystalyip.csc301.HTTPInteractions;


import org.json.JSONObject;

public class AvgReviewGet {
    String url = "http://18.234.123.109/api/cookAvgRating/";
    public AvgReviewGet(int id) {
        this.url += Integer.toString(id);
    }

    public double getAvgRating() throws Exception {

        String output = HTTPRequests.getHTTP(url);
        if (!output.equals("failure")) {
            return Double.parseDouble(output);
        }
        else{return 0;  }
    }
}
