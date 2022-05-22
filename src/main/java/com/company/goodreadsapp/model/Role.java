package com.company.goodreadsapp.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Role {
    USER("comment.create", "comment.update"),
    ADMIN(USER, "user.update", "user.delete");

    private List<String> permissions;

    Role(String... permissions) {
        this.permissions = Arrays.stream(permissions).collect(Collectors.toList());
    }

    Role(Role parent, String... permissions) {
        this.permissions = Arrays.stream(permissions).collect(Collectors.toList());
        this.permissions.addAll(parent.permissions);
        this.permissions.add(parent.getRole());
    }


    public String getRole() {
        return "ROLE_" + this.name();
    }
}
