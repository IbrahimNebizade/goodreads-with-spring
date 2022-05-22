package com.company.goodreadsapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserCommand {
    private String alias;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
