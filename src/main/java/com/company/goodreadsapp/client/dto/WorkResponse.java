package com.company.goodreadsapp.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkResponse {
    private String title;
    private TypeValue description;
    private String key;
    private List<Author> authors;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Author {
        private Key author;
        private Key type;
    }
}
