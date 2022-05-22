package com.company.goodreadsapp.repository.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_login_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    private String email;

    private String password;
}
