package com.catpaw.catpawmiddleware.common.constants;

public enum SortConst {

    CREATED("created"),
    UPDATED("updated");

    private final String value;

    SortConst(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
