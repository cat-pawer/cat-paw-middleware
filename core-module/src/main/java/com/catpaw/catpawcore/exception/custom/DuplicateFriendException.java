package com.catpaw.catpawcore.exception.custom;

public class DuplicateFriendException extends RuntimeException {

    public DuplicateFriendException(String message) {
        super(message);
    }

    public DuplicateFriendException(String message, Throwable cause) {
        super(message, cause);
    }
}
