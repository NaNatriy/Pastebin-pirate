package com.example.pastebin.enums;

import lombok.Getter;

@Getter
public enum Access {
    PUBLIC("public"),
    UNLISTED("unlisted");

    private final String access;

    Access(String access) {
        this.access = access;
    }
}
