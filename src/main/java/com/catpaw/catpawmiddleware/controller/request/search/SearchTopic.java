package com.catpaw.catpawmiddleware.controller.request.search;

public enum SearchTopic {

    DEADLINE("deadLine"), ISNEW("isNew");

    private final String value;

    SearchTopic(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
