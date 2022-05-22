package com.company.goodreadsapp.controller;

import com.company.goodreadsapp.model.Book;
import com.company.goodreadsapp.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Book> searchByTitle(@RequestParam("byTitle") String title) {
        return ResponseEntity.ok(searchService.searchByTitle(title));
    }
}
