package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.model.Book;
import com.company.goodreadsapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Book save(Book entity) {
        var sql = """
                insert into books(title, description, key, overall_rating)\s
                values (:title, :description, :key, :overallRating)
                   """;

        var params = new MapSqlParameterSource()
                .addValue("title", entity.getTitle())
                .addValue("description", entity.getDescription())
                .addValue("key", entity.getKey())
                .addValue("overallRating", entity.getOverallRating());

        var keyHolder = new GeneratedKeyHolder();
        var updated = jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        log.debug("insert count: {}", updated);

        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long aLong) {
        try {
            var sql = """
                    select id, title, description,
                     key, overall_rating, created_at, last_updated_at
                    from books
                    where id = :id
                    """;
            var params = new MapSqlParameterSource()
                    .addValue("id", aLong);

            var book = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var mappedBook = Book.builder()
                        .id(rs.getLong("id"))
                        .key(rs.getString("key"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .overallRating(rs.getBigDecimal("overall_rating"))
                        .build();

                mappedBook.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                mappedBook.setLastUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());

                return mappedBook;
            });
            return Optional.of(book);
        } catch (DataAccessException e) {
            log.error("ActionLog.BookRepositoryImpl.findById.error -  ", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<Book> findByKey(String key) {
        log.info("ActionLog.BookRepositoryImpl.findByKey.start");
        try {
            var sql = """
                    select id, title, description,
                     key, overall_rating, created_at, last_updated_at
                    from books
                    where key = :key
                    """;
            var params = new MapSqlParameterSource()
                    .addValue("key", key);

            var book = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var mappedBook = Book.builder()
                        .id(rs.getLong("id"))
                        .key(rs.getString("key"))
                        .title(rs.getString("title"))
                        .description(rs.getString("description"))
                        .overallRating(rs.getBigDecimal("overall_rating"))
                        .build();

                mappedBook.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                mappedBook.setLastUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());

                return mappedBook;
            });
            log.info("ActionLog.BookRepositoryImpl.findByKey.end");
            return Optional.of(book);
        } catch (DataAccessException e) {
            log.error("ActionLog.BookRepositoryImpl.findById.error -  ", e);
            return Optional.empty();
        }
    }
}
