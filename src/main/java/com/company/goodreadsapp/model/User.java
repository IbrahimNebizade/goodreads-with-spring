package com.company.goodreadsapp.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter(value = AccessLevel.PUBLIC)
public class User extends BaseModel {
    private Long id;
    private String alias;
    private String firstname;
    private String lastname;
    private List<Role> role;
}
