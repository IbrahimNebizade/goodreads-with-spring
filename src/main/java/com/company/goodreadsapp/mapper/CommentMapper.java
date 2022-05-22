package com.company.goodreadsapp.mapper;

import com.company.goodreadsapp.dto.AddCommentCommand;
import com.company.goodreadsapp.dto.ReplyCommentCommand;
import com.company.goodreadsapp.model.Comment;
import com.company.goodreadsapp.repository.jpa.CommentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CommentMapper {
    public static final CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "user.id", source = "command.userId")
    @Mapping(target = "book.id", source = "command.bookId")
    @Mapping(target = "comment", source = "command.comment")
    @Mapping(target = "status", constant = "ADDED")
    public abstract Comment addCommentToComment(AddCommentCommand command);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "user.id", source = "command.userId")
    @Mapping(target = "book.id", source = "command.bookId")
    @Mapping(target = "comment", source = "command.comment")
    @Mapping(target = "status", constant = "ADDED")
    public abstract CommentEntity addComment(AddCommentCommand command);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent.id", source = "command.parentId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "comment", source = "command.comment")
    @Mapping(target = "status", constant = "ADDED")
    public abstract Comment toEntity(ReplyCommentCommand command, Long userId, Long bookId);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent.id", source = "command.parentId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "comment", source = "command.comment")
    @Mapping(target = "status", constant = "ADDED")
    public abstract CommentEntity toCommentEntity(ReplyCommentCommand command, Long userId, Long bookId);
}
