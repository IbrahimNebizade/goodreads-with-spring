package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.client.OpenLibraryClient;
import com.company.goodreadsapp.model.Author;
import com.company.goodreadsapp.model.Book;
import com.company.goodreadsapp.repository.AuthorBookRepository;
import com.company.goodreadsapp.repository.AuthorRepository;
import com.company.goodreadsapp.repository.BookRepository;
import com.company.goodreadsapp.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final OpenLibraryClient openLibraryClient;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;

    @Transactional
    @Override
    public Book searchByTitle(String title) {
        log.info("ActionLog.{}.searchByTitle.start - title: {}", getClass().getSimpleName(), title);
        title = title.toLowerCase();
        var titleSearchResponse = openLibraryClient.search(title);
        var bookKey = titleSearchResponse.getDocs().get(0).getKey();
        var bookOptional = bookRepository.findByKey(bookKey);
        Book bookEntity;
        if (bookOptional.isPresent()) {
            bookEntity = bookOptional.get();
        } else {
            var book = openLibraryClient.getWorks(bookKey);
            bookEntity = bookRepository.save(Book.builder()
                    .title(book.getTitle())
                    .key(book.getKey())
                    .description(book.getDescription().getValue())
                    .build());

            var authorIds = new LinkedList<Long>();
            book.getAuthors().forEach(author -> {
                var authorKey = author.getAuthor().getKey();
                var authorResponse = openLibraryClient.getAuthor(authorKey);
                var authorEntity = authorRepository.findByKey(authorKey);
                if (authorEntity.isEmpty()) {
                    log.debug("author birth date: {}", authorResponse.getBirthDate());
                    var savedAuthor = authorRepository.save(Author.builder()
                            .bio(authorResponse.getBio())
                            .key(authorResponse.getKey())
                            .birthDate(authorResponse.getBirthDate())
                            .build());
                    authorIds.add(savedAuthor.getId());
                }
            });
            authorBookRepository.saveByBookId(bookEntity.getId(), authorIds);
        }
        log.info("ActionLog.{}.searchByTitle.end - title: {}", getClass().getSimpleName(), title);
        return bookEntity;
    }

    private LocalDate parse(String date, String pattern) {
        try {
            if (StringUtils.hasText(pattern)) {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
            }
            return parse(date, getFormat(pattern));
        } catch (Exception e) {
            return parse(date, getFormat(pattern));
        }

    }

    private String getFormat(String current) {
        switch (current) {
            case "dd MMMM yyyy" -> {
                return "d MMMM yyyy";
            }
            case "d MMMM yyyy" -> {
                return "yyyy";
            }
            case "" -> {
                return "dd MMMM yyyy";
            }
            default -> {
                throw new RuntimeException("not found");
            }
        }
    }
}
