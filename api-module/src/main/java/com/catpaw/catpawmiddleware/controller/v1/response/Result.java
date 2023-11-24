package com.catpaw.catpawmiddleware.controller.v1.response;

import com.catpaw.catpawcore.domain.dto.service.CustomPageDto;
import jakarta.annotation.Nullable;
import lombok.Getter;


@Getter
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

    public Result() {};
}
