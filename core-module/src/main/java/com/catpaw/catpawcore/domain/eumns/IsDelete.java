package com.catpaw.catpawcore.domain.eumns;

public enum IsDelete {

    YES("Y"), NO("N");

    private final String value;

    IsDelete(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
