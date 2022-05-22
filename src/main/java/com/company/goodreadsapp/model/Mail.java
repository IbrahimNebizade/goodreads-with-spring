package com.company.goodreadsapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail implements Serializable {
    @Builder.Default
    private String from = "goodreads@gmail.com";
    @Builder.Default
    private String alias = "Good Reads";

    private String to;
    private String subject;
    private String body;

    @Builder.Default
    private boolean html = false;
    private Template template;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Template implements Serializable{
        private String path;
        private Map<String, Object> params;
    }
}
