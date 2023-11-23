package com.catpaw.catpawmiddleware.controller.v1.request.enums;

public interface BaseEnumRequest<T> {

    String getCode();

    T toEnum();
}
