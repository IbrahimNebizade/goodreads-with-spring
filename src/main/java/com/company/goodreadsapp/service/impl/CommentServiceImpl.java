package com.company.goodreadsapp.service.impl;

import com.company.goodreadsapp.dto.AddCommentCommand;
import com.company.goodreadsapp.dto.AddCommentResponse;
import com.company.goodreadsapp.dto.ReplyCommentCommand;
import com.company.goodreadsapp.dto.ReplyCommentResponse;
import com.company.goodreadsapp.exception.NotFoundException;
import com.company.goodreadsapp.mapper.CommentMapper;
import com.company.goodreadsapp.repository.jpa.BookJpaRepository;
import com.company.goodreadsapp.repository.jpa.CommentJpaRepository;
import com.company.goodreadsapp.service.CommentService;
import com.company.goodreadsapp.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final BookJpaRepository bookRepository;
    private final CommentJpaRepository commentRepository;

    @Override
    public AddCommentResponse addComment(AddCommentCommand command) {
        log.info("ActionLog.CommentServiceImpl.addComment.start - command: {}", command);
        // 1. book exist?
        bookRepository.findById(command.getBookId())
                .orElseThrow(() -> new RuntimeException("exception.book.not-found"));

        // 2. save comment to db
        var comment = commentRepository
                .save(CommentMapper.INSTANCE.addComment(command));

        log.info("ActionLog.CommentServiceImpl.addComment.end - command: {}", command);
        return new AddCommentResponse(comment.getId(), comment.getBook().getId());
    }

    @Transactional
    @Override
    public ReplyCommentResponse replyComment(Long userId, ReplyCommentCommand command) {
        log.info("ActionLog.CommentServiceImpl.replyComment.start - userId: {}, command: {}", userId, command);
        var parentComment = commentRepository.findById(command.getParentId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.COMMENT_NOT_FOUND));

        var comment = CommentMapper
                .INSTANCE
                .toCommentEntity(command, userId, parentComment.getBook().getId());

        comment = commentRepository.save(comment);

        log.info("ActionLog.CommentServiceImpl.replyComment.end - userId: {}, command: {}", userId, command);
        return new ReplyCommentResponse(comment.getId());
    }
}
