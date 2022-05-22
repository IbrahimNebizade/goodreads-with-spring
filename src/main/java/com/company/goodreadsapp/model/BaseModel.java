package com.company.goodreadsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
