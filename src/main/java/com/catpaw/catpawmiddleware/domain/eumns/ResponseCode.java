package com.catpaw.catpawmiddleware.domain.eumns;

public enum ResponseCode {

    SUCCESS(0),
    DUPLICATE_EMAIL(1),
    NO_MATCH_PASSWORD(2),
    NOT_FOUND_MEMBER(3),
    DUPLICATE_FRIEND(4),
    INVALID_ARGUMENT(5),
    ILLEGAL_STATE(6),
    NO_SUPPORT_METHOD(7),
    EXCEPTION(99);

    private final int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
