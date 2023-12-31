package com.catpaw.catpawcore.exception.custom;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
