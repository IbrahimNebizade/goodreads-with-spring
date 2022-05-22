package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.model.User;
import com.company.goodreadsapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User save(User user) {
        var sql = """ 
                insert into users (alias, firstname, lastname)
                values (:alias, :firstname, :lastname)
                """;
        var params = new BeanPropertySqlParameterSource(user);
        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        try {
            log.info("ActionLog.{}.findById.start- {}",getClass().getSimpleName());
            var sql = "select id,alias,firstname,lastname,created_at,last_updated_at from users where id=:id";
            var params = new MapSqlParameterSource()
                    .addValue("id", aLong);
            var user = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var userMapped = User.builder()
                        .id(rs.getLong("id"))
                        .alias(rs.getString("alias"))
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("rs.lastname"))
                        .build();
                userMapped.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                userMapped.setLastUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
                return userMapped;
            });
            return Optional.of(user);
        }catch (DataAccessException e){
            log.error("ActionLog.{}.findById.error- {}",getClass().getSimpleName(),e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<User> findByAlias(String alias) {
        try {
            var sql = """
                    select id, alias, firstname, lastname, created_at, last_updated_at
                    from users
                    where alias = :alias
                    """;

            var params = new MapSqlParameterSource()
                    .addValue("alias", alias);
            var user = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
                var userMapped = User.builder()
                        .id(rs.getLong("id"))
                        .firstname(rs.getString("firstname"))
                        .lastname(rs.getString("lastname"))
                        .alias(rs.getString("alias"))
                        .build();
                userMapped.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                userMapped.setLastUpdatedAt(rs.getTimestamp("last_updated_at").toLocalDateTime());
                return userMapped;
            });
            return Optional.of(user);
        } catch (DataAccessException e) {
            log.error("ActionLog.UserRepositoryImpl.findByAlias.error - ", e);
            return Optional.empty();
        }
    }
}
