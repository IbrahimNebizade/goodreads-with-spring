package com.company.goodreadsapp.repository;

import java.util.List;

public interface AuthorBookRepository {
    void saveByBookId(Long bookId, List<Long> authorId);
}
