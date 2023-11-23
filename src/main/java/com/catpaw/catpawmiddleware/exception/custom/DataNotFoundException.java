package com.catpaw.catpawmiddleware.exception.custom;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super("존재하지 않는 객체입니다.");
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
