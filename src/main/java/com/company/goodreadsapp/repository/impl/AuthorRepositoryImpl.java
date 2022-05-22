package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.exception.NotImplementedException;
import com.company.goodreadsapp.model.Author;
import com.company.goodreadsapp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Author save(Author entity) {
        var sql = """
                insert into authors(key, name, bio, birth_date)
                values (:key, :name, :bio, :birthDate)
                """;

        var params = new MapSqlParameterSource()
                .addValue("key", entity.getKey())
                .addValue("name", entity.getName())
                .addValue("bio", entity.getBio())
                .addValue("birthDate", entity.getBirthDate());

        var keyHolder = new GeneratedKeyHolder();
        var updated = jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        log.debug("insert count: {}", updated);

        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Author update(Author entity) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Author> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<Author> findByKey(String key) {
        return Optional.empty();
    }
}
