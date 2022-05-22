package com.company.goodreadsapp.controller;

import com.company.goodreadsapp.dto.*;
import com.company.goodreadsapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PreAuthorize("hasAuthority('comment.create')")
    @PostMapping
    public ResponseEntity<AddCommentResponse> addComment(
            @Valid @RequestBody AddCommentCommand command,
            Principal principal
    ) {
        var userDetails = (UsernamePasswordAuthenticationToken) principal;
        command.setUserId((Long) userDetails.getPrincipal());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.addComment(command));
    }

    @PostMapping("/reply")
    public ResponseEntity<ReplyCommentResponse> replyComment(
            @RequestHeader(HeaderKeys.USER_ID) Long userId,
            @RequestBody ReplyCommentCommand command
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(commentService.replyComment(userId, command));
    }


}
