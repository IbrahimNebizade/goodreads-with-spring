package com.company.goodreadsapp.controller;

import com.company.goodreadsapp.dto.AddRatingCommand;
import com.company.goodreadsapp.dto.AddRatingResponse;
import com.company.goodreadsapp.dto.AddReviewCommand;
import com.company.goodreadsapp.dto.AddReviewResponse;
import com.company.goodreadsapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PutMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<AddReviewResponse> addReview(@Valid @RequestBody AddReviewCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(command));
    }
    @PutMapping("/rating/users/{userId}/books/{bookId}")
    public ResponseEntity<AddRatingResponse> addRating(@RequestBody AddRatingCommand command){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.addRating(command));
    }


}
