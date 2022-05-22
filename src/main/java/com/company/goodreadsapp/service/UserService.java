package com.company.goodreadsapp.service;

import com.company.goodreadsapp.dto.CreateUserCommand;
import com.company.goodreadsapp.dto.Page;
import com.company.goodreadsapp.dto.UserCreatedResponse;
import com.company.goodreadsapp.repository.jpa.UserEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserCreatedResponse createUser(CreateUserCommand command);

    Page<List<UserEntity>> getAllUsers(Pageable pageable);
}
