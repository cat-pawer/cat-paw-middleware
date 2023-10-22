package com.catpaw.catpawmiddleware.common.handler.exception;


import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Result<Object>> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.builder()
                        .code(ResponseCode.NO_MATCH_PASSWORD.getCode())
                        .message("BadCredentialsException")
                        .build(),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Result<Object>> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.builder()
                        .code(ResponseCode.NOT_FOUND_MEMBER.getCode())
                        .message("UsernameNotFoundException")
                        .build(),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<Result<Object>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.builder()
                        .code(ResponseCode.EXCEPTION.getCode())
                        .message("Exception")
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
