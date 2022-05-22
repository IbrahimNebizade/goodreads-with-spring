package com.company.goodreadsapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
    private String bio;
    private String name;
    private String key;
    @JsonProperty("birth_date")
    private String birthDate;


}
