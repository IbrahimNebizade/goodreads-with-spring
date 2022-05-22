package com.company.goodreadsapp.service;

import com.company.goodreadsapp.dto.AddCommentCommand;
import com.company.goodreadsapp.dto.AddCommentResponse;
import com.company.goodreadsapp.dto.ReplyCommentCommand;
import com.company.goodreadsapp.dto.ReplyCommentResponse;

public interface CommentService {
    AddCommentResponse addComment(AddCommentCommand command);

    ReplyCommentResponse replyComment(Long userId, ReplyCommentCommand command);
}
