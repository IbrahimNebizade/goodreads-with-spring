package com.company.goodreadsapp.service;

import com.company.goodreadsapp.model.Book;

public interface SearchService {
    Book searchByTitle(String title);
}
