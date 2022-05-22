package com.company.goodreadsapp.model;

import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author extends BaseModel{
    private Long id;
    private String key;
    private String name;
    private String bio;
    private String birthDate;
    private Set<Book> books;
}
