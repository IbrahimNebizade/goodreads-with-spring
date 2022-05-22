package com.company.goodreadsapp.repository;

import com.company.goodreadsapp.model.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByKey(String key);
}
