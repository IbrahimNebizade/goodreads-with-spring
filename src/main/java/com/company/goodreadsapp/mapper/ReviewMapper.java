package com.company.goodreadsapp.mapper;


import com.company.goodreadsapp.dto.AddRatingCommand;
import com.company.goodreadsapp.dto.AddReviewCommand;
import com.company.goodreadsapp.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ReviewMapper {
    public static final ReviewMapper INSTANCE= Mappers.getMapper(ReviewMapper.class);
    @Mapping(target = "user.id",source = "command.userId")
    @Mapping(target = "book.id",source = "command.bookId")
    @Mapping(target = "readStatus",source = "command.readStatus")
    @Mapping(target = "rating",ignore = true)
    public abstract Review addReview(AddReviewCommand command);

    @Mapping(target = "user.id",source = "command.userId")
    @Mapping(target = "book.id",source = "command.bookId")
    @Mapping(target = "readStatus",ignore = true)
    @Mapping(target = "rating",source = "command.rating")
    public abstract Review addRating(AddRatingCommand command);
}

