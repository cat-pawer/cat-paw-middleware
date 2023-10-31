package com.catpaw.catpawmiddleware.exception;


import com.catpaw.catpawmiddleware.exception.custom.UserNotFoundException;
import com.catpaw.catpawmiddleware.controller.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Result<Void>> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NO_MATCH_PASSWORD.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Result<Void>> usernameNotFoundExceptionHandler(UserNotFoundException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NOT_FOUND_MEMBER.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.INVALID_ARGUMENT.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Result<Void>> illegalStateExceptionHandler(IllegalStateException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.ILLEGAL_STATE.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.INVALID_ARGUMENT.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Result<Void>> databaseException(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.ILLEGAL_STATE.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result<Void>> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.INVALID_ARGUMENT.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<Void>> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NO_SUPPORT_METHOD.getCode(), e.getMessage(), null),
                HttpStatus.OK
        );
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<Result<Void>> exHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.EXCEPTION.getCode(), "잘못된 요청입니다", null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
