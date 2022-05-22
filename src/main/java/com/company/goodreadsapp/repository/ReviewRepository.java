package com.company.goodreadsapp.repository;


import com.company.goodreadsapp.model.Review;

public interface ReviewRepository extends CrudRepository<Review,Long> {
    Review addRating(Review review);
    Review upddateRating(Review review);
}
