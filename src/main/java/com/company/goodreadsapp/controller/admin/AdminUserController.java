package com.company.goodreadsapp.controller.admin;

import com.company.goodreadsapp.dto.Page;
import com.company.goodreadsapp.repository.jpa.UserEntity;
import com.company.goodreadsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<List<UserEntity>>> getUsers(Pageable pageable) {
        log.debug("page: {}", pageable);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }
}
