package com.company.goodreadsapp.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(exclude = "authors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book extends BaseModel {
    private Long id;
    private String title;
    private String description;
    private String key;
    private BigDecimal overallRating;
    private Set<Author> authors;
}
