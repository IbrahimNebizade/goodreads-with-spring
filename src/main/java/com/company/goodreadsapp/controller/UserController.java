package com.company.goodreadsapp.controller;

import com.company.goodreadsapp.dto.CreateUserCommand;
import com.company.goodreadsapp.dto.UserCreatedResponse;
import com.company.goodreadsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserCreatedResponse> createUser(
            @RequestBody CreateUserCommand command
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(command));
    }
}
