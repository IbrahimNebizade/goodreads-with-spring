package com.company.goodreadsapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String title;
    private String key;
    @JsonProperty("first_publish_year")
    private String firstPublishYear;
}
