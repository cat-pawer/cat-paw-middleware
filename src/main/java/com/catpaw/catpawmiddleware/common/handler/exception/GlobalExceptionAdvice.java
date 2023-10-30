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
                new Result<>(ResponseCode.NO_MATCH_PASSWORD.getCode(), "잘못된 비밀번호입니다.", null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Result<Object>> usernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new Result<>(ResponseCode.NOT_FOUND_MEMBER.getCode(), "사용자를 찾을 수 없습니다.", null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<Result<Object>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                new Result<>(ResponseCode.EXCEPTION.getCode(), "잘못된 요청입니다", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
