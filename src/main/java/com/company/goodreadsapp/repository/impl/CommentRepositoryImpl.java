package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.model.Book;
import com.company.goodreadsapp.model.Comment;
import com.company.goodreadsapp.model.CommentStatus;
import com.company.goodreadsapp.model.User;
import com.company.goodreadsapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Comment save(Comment entity) {
        var sql = """
                    insert into user_book_comments (parent_comment_id, user_id, book_id, comment, status)
                    values (:parentId, :userId, :bookId, :comment, :status)
                """;
        var params = new MapSqlParameterSource()
                .addValue("userId", entity.getUser().getId())
                .addValue("bookId", entity.getBook().getId())
                .addValue("comment", entity.getComment())
                .addValue("status", entity.getStatus().name());

        if (entity.getParent() != null) {
            params.addValue("parentId", entity.getParent().getId());
        } else {
            params.addValue("parentId", null, Types.NULL);
        }

        var keyHolder = new GeneratedKeyHolder();
        var updated = jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        log.debug("insert count: {}", updated);

        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
    }

    @Override
    public Comment update(Comment entity) {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long aLong) {
        try {
            var sql = """
                     select id,
                             parent_comment_id,
                             user_id,
                             book_id,
                             comment,
                             created_at,
                             last_updated_at,
                             status
                      from user_book_comments ubc
                      where ubc.id = :id
                    """;
            var params = new MapSqlParameterSource()
                    .addValue("id", aLong);
            var comment = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var mapped = Comment.builder()
                        .id(aLong)
                        .parent(Comment.builder()
                                .id(rs.getLong("parent_comment_id"))
                                .build()
                        )
                        .book(Book.builder()
                                .id(rs.getLong("book_id"))
                                .build()
                        )
                        .comment(rs.getString("comment"))
                        .status(CommentStatus.valueOf(rs.getString("status")))
                        .user(User.builder()
                                .id(rs.getLong("user_id"))
                                .build()
                        )
                        .build();

                mapped.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                mapped.setLastUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
                return mapped;
            });

            return Optional.ofNullable(comment);
        } catch (DataAccessException e) {
            log.error("ActionLog.CommentRepositoryImpl.findById.error - ", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
