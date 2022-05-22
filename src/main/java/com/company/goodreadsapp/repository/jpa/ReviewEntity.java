package com.company.goodreadsapp.repository.jpa;

import com.company.goodreadsapp.model.ReadStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_book_reviews")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @EmbeddedId
    private ReviewId id;

    @Enumerated(EnumType.STRING)
    private ReadStatus readStatus;

    private Integer rating;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class ReviewId implements Serializable {
        @OneToOne(fetch = FetchType.LAZY)
        private UserEntity user;
        @OneToOne(fetch = FetchType.LAZY)
        private BookEntity book;
    }
}
