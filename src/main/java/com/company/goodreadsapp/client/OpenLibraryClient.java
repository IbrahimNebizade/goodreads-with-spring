package com.company.goodreadsapp.client;

import com.company.goodreadsapp.client.dto.AuthorResponse;
import com.company.goodreadsapp.client.dto.TitleSearchResponse;
import com.company.goodreadsapp.client.dto.WorkResponse;

public interface OpenLibraryClient {
    TitleSearchResponse search(String title);
    WorkResponse getWorks(String key);
    AuthorResponse getAuthor(String key);
}
