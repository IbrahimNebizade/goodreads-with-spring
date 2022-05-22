package com.company.goodreadsapp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsJpaRepository extends JpaRepository<UserDetailsEntity, Long> {
    Optional<UserDetailsEntity> findByEmail(String email);
}
