package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.repository.AuthorBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuthorBookRepositoryImpl implements AuthorBookRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveByBookId(Long bookId, List<Long> authorIds) {
        log.info("ActionLog.AuthorBookRepositoryImpl.saveByBookId.start");
        var sql = """
                insert into author_books(author_id, book_id)
                values (?,?)
                """;
        var setter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, authorIds.get(i));
                ps.setLong(2, bookId);
            }

            public int getBatchSize() {
                return authorIds.size();
            }
        };

        var updated = jdbcTemplate.batchUpdate(sql, setter);
        log.debug("updated: {}", updated.length);
        log.info("ActionLog.AuthorBookRepositoryImpl.saveByBookId.start");
    }
}
