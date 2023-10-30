package com.catpaw.catpawmiddleware.controller.response;

import jakarta.annotation.Nullable;

public class Result<T> {

    private final int code;
    private final String message;
    private final T data;

    public Result(int code, @Nullable String message, @Nullable T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
