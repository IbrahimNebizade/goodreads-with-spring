package com.company.goodreadsapp.repository.jpa;

import com.company.goodreadsapp.model.CommentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = "parent")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "parent")
@Table(name = "user_book_comments")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "parentCommentId")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent")
    private Set<CommentEntity> children = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY)
    private BookEntity book;

    private String comment;

    @Enumerated(value = EnumType.STRING)
    private CommentStatus status;

}
