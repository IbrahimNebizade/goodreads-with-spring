package com.company.goodreadsapp.repository;

import com.company.goodreadsapp.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByAlias(String alias);
}
