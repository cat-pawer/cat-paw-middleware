package com.catpaw.catpawcore.exception.custom;

public class NoSupportContentTypeException extends RuntimeException {

    public NoSupportContentTypeException(String message) {
        super(message);
    }

    public NoSupportContentTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
