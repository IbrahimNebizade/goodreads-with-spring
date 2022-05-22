package com.company.goodreadsapp.repository;

import com.company.goodreadsapp.model.Book;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findByKey(String key);
}
