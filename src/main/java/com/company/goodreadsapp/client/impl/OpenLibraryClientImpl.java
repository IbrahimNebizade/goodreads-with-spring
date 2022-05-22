package com.company.goodreadsapp.client.impl;

import com.company.goodreadsapp.client.OpenLibraryClient;
import com.company.goodreadsapp.client.dto.AuthorResponse;
import com.company.goodreadsapp.client.dto.TitleSearchResponse;
import com.company.goodreadsapp.client.dto.WorkResponse;
import com.company.goodreadsapp.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.function.Supplier;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenLibraryClientImpl implements OpenLibraryClient {
    private static final String JSON = ".json";
    private static final String SEARCH = "search" + JSON;
    private static final String WORKS = "works";
    private static final String AUTHORS = "authors";

    private final RestTemplate restTemplate;
    // https://openlibrary.org/search.json?title=the+lord+of+the+rings&limit=1
    // https://openlibrary.org/works/OL27448W.json
    // https://openlibrary.org/authors/OL23919A.json

    @Value("${client.openlib.url}")
    private String openlibUrl;


    @Override
    public TitleSearchResponse search(String title) {
        log.info("ActionLog.{}.search.start - title: {}", getClass().getSimpleName(), title);
        var uri = UriComponentsBuilder.fromUriString(openlibUrl + SEARCH)
                .queryParam("title", title)
                .queryParam("limit", 1)
                .encode()
                .build()
                .toUri();

        var responseEntity = handleException(() ->
                restTemplate.getForEntity(uri, TitleSearchResponse.class)
        );

        if (responseEntity.getBody() != null && responseEntity.getBody().getDocs().isEmpty()) {
            throw new NotFoundException("exception.client.search.title.not-found");
        }

        log.info("ActionLog.{}.search.end - title: {}", getClass().getSimpleName(), title);
        return responseEntity.getBody();
    }

    @Override
    public WorkResponse getWorks(String key) {
        log.info("ActionLog.{}.getWorks.start - key: {}", getClass().getSimpleName(), key);
        if (!key.contains(WORKS)) {
            key = WORKS + "/" + key;
        }
        var uri = UriComponentsBuilder.fromUriString(openlibUrl)
                .path(key + JSON)
                .build()
                .toUri();
        var response = handleException(() -> restTemplate.getForEntity(uri, WorkResponse.class));

        if (response.getBody() == null) {
            throw new NotFoundException("exception.client.works.key.not-found");
        }
        log.info("ActionLog.{}.getWorks.end - key: {}", getClass().getSimpleName(), key);
        return response.getBody();
    }

    @Override
    public AuthorResponse getAuthor(String key) {
        log.info("ActionLog.{}.getAuthor.start - key: {}", getClass().getSimpleName(), key);
        if (!key.contains(AUTHORS)) {
            key = AUTHORS + "/" + key;
        }
        var uri = UriComponentsBuilder.fromUriString(openlibUrl)
                .path(key + JSON)
                .build()
                .toUri();
        var response = handleException(() -> restTemplate.getForEntity(uri, AuthorResponse.class));

        if (response.getBody() == null) {
            throw new NotFoundException("exception.client.authors.key.not-found");
        }
        log.info("ActionLog.{}.getAuthor.end - key: {}", getClass().getSimpleName(), key);
        return response.getBody();
    }

    private <R> R handleException(Supplier<R> supplier) {
        try {
            return supplier.get();
        } catch (RestClientException e) {
            log.error("client failed: ", e);
            throw e;
        }
    }
}
