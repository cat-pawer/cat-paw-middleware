package com.catpaw.catpawmiddleware.exception;


import com.catpaw.catpawmiddleware.exception.custom.*;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.domain.eumns.ResponseCode;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @Hidden
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result<Void>> unauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.UNAUTHORIZED.getCode(), e.getMessage(), null),
                HttpStatus.UNAUTHORIZED
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Result<Void>> forbiddenExceptionHandler(ForbiddenException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.FORBIDDEN.getCode(), e.getMessage(), null),
                HttpStatus.FORBIDDEN
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            HttpMessageConversionException.class,
            MissingServletRequestParameterException.class,
            MissingPathVariableException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
    })
    public ResponseEntity<Result<Void>> invalidArgumentExceptionHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.INVALID_ARGUMENT.getCode(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Result<Void>> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.INVALID_ARGUMENT.getCode(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSupportContentTypeException.class)
    public ResponseEntity<Result<Void>> noSupportContentTypeExceptionHandler(NoSupportContentTypeException e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NO_SUPPORT_CONTENT_TYPE.getCode(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalStateException.class, FileConvertException.class })
    public ResponseEntity<Result<Void>> illegalStateExceptionHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.ILLEGAL_STATE.getCode(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Result<Void>> notMemberFoundExceptionHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NOT_FOUND_MEMBER.getCode(), e.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Result<Void>> dataNotFoundExceptionHandler(DataNotFoundException e) {
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NOT_FOUND.getCode(), e.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class, UnsupportedOperationException.class })
    public ResponseEntity<Result<Void>> noSupportedExceptionHandler(Exception e) {
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.NO_SUPPORT_METHOD.getCode(), e.getMessage(), null),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @Hidden
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class , Exception.class })
    public ResponseEntity<Result<Void>> serverExceptionHandler(Exception e) {
        log.error("[ex handler] ex", e);
        return new ResponseEntity<>(
                Result.createSingleResult(ResponseCode.ILLEGAL_STATE.getCode(), e.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
