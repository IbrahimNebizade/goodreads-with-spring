package com.company.goodreadsapp.repository;

import com.company.goodreadsapp.model.LoginDetail;

import java.util.Optional;

public interface LoginDetailRepository extends CrudRepository<LoginDetail, Long> {
    Optional<LoginDetail> findByEmail(String email);
}
