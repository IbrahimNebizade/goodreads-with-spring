package com.company.goodreadsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyCommentCommand {
    @NotNull
    private Long parentId;
    @NotNull
    private Long bookId;

    @NotBlank
    @Size(min = 1, max = 140)
    private String comment;

}
