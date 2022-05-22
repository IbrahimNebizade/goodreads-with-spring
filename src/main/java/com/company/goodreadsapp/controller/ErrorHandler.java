package com.company.goodreadsapp.controller;

import com.company.goodreadsapp.dto.RestException;
import com.company.goodreadsapp.exception.ForbiddenException;
import com.company.goodreadsapp.exception.NotFoundException;
import com.company.goodreadsapp.exception.UnauthorizedException;
import com.company.goodreadsapp.util.MessageSourceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<RestException> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(RestException.builder()
                        .code(e.code)
                        .message(messageSourceUtil.getMessage(e.code))
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()
                );
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<RestException> handle(UnauthorizedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(RestException.builder()
                        .code(e.code)
                        .message(messageSourceUtil.getMessage(e.code))
                        .httpStatus(HttpStatus.UNAUTHORIZED)
                        .build()
                );
    }

    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity<RestException> handle(ForbiddenException e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(RestException.builder()
                        .code(e.code)
                        .message(messageSourceUtil.getMessage(e.code))
                        .httpStatus(HttpStatus.FORBIDDEN)
                        .build()
                );
    }
}
