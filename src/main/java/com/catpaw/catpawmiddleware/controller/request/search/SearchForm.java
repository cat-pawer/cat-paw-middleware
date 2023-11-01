package com.catpaw.catpawmiddleware.controller.request.search;

public enum SearchForm {

    DEADLINE("deadLine"), ISNEW("isNew");

    private final String value;

    SearchForm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
