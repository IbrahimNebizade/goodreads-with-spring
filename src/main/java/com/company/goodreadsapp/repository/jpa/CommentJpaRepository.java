package com.company.goodreadsapp.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "select c from CommentEntity c join c.book b where c.id = :id ")
    Optional<CommentEntity> getCommentWithBook(@Param("id") Long id);
}
