package com.company.goodreadsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRatingCommand {
    @NotNull
    private Long userId;
    @NotNull
    private Long bookId;
    @Min(1)
    @Max(5)
    private Long rating;
}
