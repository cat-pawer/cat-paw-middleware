package com.catpaw.catpawmiddleware.service.file;

import lombok.Getter;

@Getter
public enum FileDestination {

    RECRUIT("recruit"),
    PROFILE("profile"),
    PORTFOLIO("portfolio"),
    GROUP_BOARD("group_board");

    private final String value;

    FileDestination(String value) {
        this.value = value;
    }
}
