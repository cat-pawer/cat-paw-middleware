package com.catpaw.catpawmiddleware.controller.response;

import com.catpaw.catpawmiddleware.service.dto.CustomPageDto;
import jakarta.annotation.Nullable;


public class Result<T> {

    private int code;

    private String message;

    private T data;


    public static <T> Result<T> createSingleResult(int code, @Nullable String message, @Nullable T data) {
        Result<T> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static <T> Result<CustomPageDto<T>> createPageResult(int code, @Nullable String message, CustomPageDto<T> pageDto) {
        Result<CustomPageDto<T>> result = new Result<>();
        result.code = code;
        result.message = message;
        result.data = pageDto;
        return result;
    }

    private Result() {};
}
