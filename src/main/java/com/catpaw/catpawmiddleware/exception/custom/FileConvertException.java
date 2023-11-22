package com.catpaw.catpawmiddleware.exception.custom;

public class FileConvertException extends RuntimeException {

    public FileConvertException() {
        super("파일 변환에 실패했습니다.");
    }

    public FileConvertException(String message) {
        super(message);
    }

    public FileConvertException(String message, Throwable cause) {
        super(message, cause);
    }
}
