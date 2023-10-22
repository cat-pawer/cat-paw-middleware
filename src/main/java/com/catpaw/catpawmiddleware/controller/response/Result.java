package com.catpaw.catpawmiddleware.controller.response;

import lombok.Builder;

@Builder
public class Result<T> {

    private int code;
    private String message;
    private T data;

}
