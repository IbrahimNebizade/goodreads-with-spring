package com.company.goodreadsapp.security.filter;

import java.util.List;

public interface ExcludedFilter {
    List<String> EXCLUSIONS = List.of(
            "/swagger-ui/**",
            "/webjars/springfox-swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/health",
            "/readiness"
    );

    static String[] convert() {
        var arr = new String[EXCLUSIONS.size()];
        for (int i = 0; i < EXCLUSIONS.size(); i++) {
            arr[i] = EXCLUSIONS.get(i);
        }
        return arr;
    }
}
