package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.AddRatingCommand;
import com.company.goodreadsapp.dto.AddRatingResponse;
import com.company.goodreadsapp.dto.AddReviewCommand;
import com.company.goodreadsapp.dto.AddReviewResponse;
import com.company.goodreadsapp.mapper.ReviewMapper;
import com.company.goodreadsapp.repository.BookRepository;
import com.company.goodreadsapp.repository.ReviewRepository;
import com.company.goodreadsapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    @Override
    public AddReviewResponse addReview(AddReviewCommand command) {
        log.info("ActionLog.ReviewServiceImpl.addReview.start - command: {}",command);
        bookRepository.findById(command.getBookId()).orElseThrow(() -> new RuntimeException("exception.book.not-found"));
        var review= reviewRepository.save(ReviewMapper.INSTANCE.addReview(command));
        log.info("ActionLog.ReviewServiceImpl.addReview.end - command: {}",command);
        return new AddReviewResponse(command.getBookId(),command.getReadStatus());
    }

    @Override
    public AddRatingResponse addRating(AddRatingCommand command) {
        log.info("ActionLog.ReviewServiceImpl.addRating.start - command: {}",command);
        bookRepository.findById(command.getBookId()).orElseThrow(() -> new RuntimeException("exception.book.not.found"));
        reviewRepository.addRating(ReviewMapper.INSTANCE.addRating(command));
        log.info("ActionLog.ReviewServiceImpl.addRating.end - command: {}",command);
        return new AddRatingResponse(command.getBookId(),command.getRating());
    }

}
