package com.catpaw.catpawmiddleware.domain.eumns;

import lombok.Getter;

@Getter
public enum FileFolder {

    RECRUIT("recruit"),
    PROFILE("profile");

    private final String value;

    FileFolder(String value) {
        this.value = value;
    }
}
