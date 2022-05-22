package com.company.goodreadsapp.repository.impl;

import com.company.goodreadsapp.model.LoginDetail;
import com.company.goodreadsapp.model.User;
import com.company.goodreadsapp.repository.LoginDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LoginDetailRepositoryImpl implements LoginDetailRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public LoginDetail save(LoginDetail loginDetail) {
        var sql = "insert into user_login_details(user_id, email, password) values (:userId, :email, :password)";
        var params = new MapSqlParameterSource()
                .addValue("userId", loginDetail.getUser().getId())
                .addValue("email", loginDetail.getEmail())
                .addValue("password", loginDetail.getPassword());

        var updateCount = jdbcTemplate.update(sql, params);
        log.debug("user details insert count: {}", updateCount);

        return loginDetail;
    }

    @Override
    public LoginDetail update(LoginDetail entity) {
        return null;
    }

    @Override
    public Optional<LoginDetail> findById(Long userId) {
        var sql = "select user_id, email, password " +
                " from user_login_details where user_id=:userId";
        var params = new MapSqlParameterSource("userId", userId);
        var loginDetail = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> LoginDetail.builder()
                .user(User.builder()
                        .id(rs.getLong("user_id"))
                        .build()
                )
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build());
        return Optional.ofNullable(loginDetail);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Optional<LoginDetail> findByEmail(String email) {
        var sql = """
            select user_id, email, password
            from user_login_details
            where email = :email
            """;
        var params = new MapSqlParameterSource("email", email);
        try {
            var loginDetail = jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> LoginDetail.builder()
                    .user(User.builder()
                            .id(rs.getLong("user_id"))
                            .build()
                    )
                    .email(rs.getString("email"))
                    .password(rs.getString("password"))
                    .build());
            return Optional.ofNullable(loginDetail);
        } catch (Exception e) {
            log.error("error: {}", e);
            return Optional.empty();
        }
    }
}
