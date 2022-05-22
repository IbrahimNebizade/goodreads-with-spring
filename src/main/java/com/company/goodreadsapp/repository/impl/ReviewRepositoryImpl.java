package com.company.goodreadsapp.repository.impl;


import com.company.goodreadsapp.model.Book;
import com.company.goodreadsapp.model.ReadStatus;
import com.company.goodreadsapp.model.Review;
import com.company.goodreadsapp.model.User;
import com.company.goodreadsapp.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Review save(Review entity) {
        var sql = "insert into user_book_reviews(user_id, book_id, read_status) values (:userId,:bookId,:readStatus);";
        var params = new MapSqlParameterSource()
                .addValue("userId", entity.getUser().getId())
                .addValue("bookId", entity.getBook().getId())
                .addValue("readStatus", entity.getReadStatus().name());
        var count=jdbcTemplate.update(sql, params);
        log.debug("review inser count: {}",count);
        return entity;
    }

    @Override
    public Review update(Review entity) {
        var sql="update user_book_reviews  set read_status=:readStatus where book_id=:bookId and user_id=:userId";
        var params=new MapSqlParameterSource()
                .addValue("readStatus",entity.getReadStatus())
                .addValue("bookId",entity.getBook().getId())
                .addValue("userId",entity.getUser().getId());
        var count=jdbcTemplate.update(sql,params);
        log.debug("review update count: {}",count);
        return entity;
    }

    @Override
    public Optional<Review> findById(Long aLong) {
        try {
            var sql = "select user_id,book_id,read_status,rating from user_book_reviews where book_id=:bookId;";
            var params = new MapSqlParameterSource()
                    .addValue("bookId", aLong);
            var review= jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var reviewO = Review.builder()
                        .user(User.builder().id(rs.getLong("user_id")).build())
                        .book(Book.builder().id(aLong).build())
                        .readStatus(ReadStatus.valueOf(rs.getString("read_status")))
                        .rating(rs.getInt("rating"))
                        .build();
                return reviewO;
            });
            return Optional.of(review);
        }catch (Exception e){
            log.error("ActionLog.ReviewRepositoryImpl.findById.error ", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long aLong) {
        var sql="";

    }

    @Override
    public Review addRating(Review review) {
        var sql="insert into user_book_reviews(user_id, book_id,rating) values (:userId,:bookId,:rating)";
        var params=new MapSqlParameterSource()
                .addValue("userId",review.getUser().getId())
                .addValue("bookId",review.getBook().getId())
                .addValue("rating",review.getRating());
        var count= jdbcTemplate.update(sql,params);
        log.debug("rating insert count",count);
        return review;
    }

    @Override
    public Review upddateRating(Review review) {
        var sql="update user_book_reviews  set rating=:rating where book_id=:bookId and user_id=:userId";
        var params=new MapSqlParameterSource()
                .addValue("rating",review.getRating())
                .addValue("bookId",review.getBook().getId())
                .addValue("userId",review.getUser().getId());
        var count=jdbcTemplate.update(sql,params);
        log.debug("rating update count: {}",count);
        return review;
    }
}
