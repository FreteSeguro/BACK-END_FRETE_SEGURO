package br.com.seguro.frete.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Role {
    ADMIN("admin"),
    USER("user");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
