package com.catpaw.catpawmiddleware.controller.request.enums;

public interface BaseEnumRequest<T> {

    String getCode();

    T toEnum();
}
