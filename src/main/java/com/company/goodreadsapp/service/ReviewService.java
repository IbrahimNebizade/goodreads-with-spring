package com.company.goodreadsapp.service;


import com.company.goodreadsapp.dto.AddRatingCommand;
import com.company.goodreadsapp.dto.AddRatingResponse;
import com.company.goodreadsapp.dto.AddReviewCommand;
import com.company.goodreadsapp.dto.AddReviewResponse;

public interface ReviewService {
    AddReviewResponse addReview(AddReviewCommand command);

    AddRatingResponse addRating(AddRatingCommand command);
}