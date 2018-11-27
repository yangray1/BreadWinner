package com.example.crystalyip.csc301.Model;

public class Review {
    private Integer cookId;
    private Integer reviewerId;
    private int rating;
    private String comments;
    private String reviewerFirstName;
    private  String reviewerLastName;

    public Review(Integer cookId, Integer reviewerId, int rating, String comments, String reviewerFirstName, String reviewerLastName) {
        this.cookId = cookId;
        this.reviewerId = reviewerId;
        this.rating = rating;
        this.comments = comments;
        this.reviewerFirstName = reviewerFirstName;
        this.reviewerLastName = reviewerLastName;
    }

    public String getComments() {
        return comments;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public int getRating() {
        return rating;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public Integer getCookId() {
        return cookId;
    }
}
