package com.company.goodreadsapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    private User user;
    private Book book;
    private ReadStatus readStatus;
    private Integer rating;
}
