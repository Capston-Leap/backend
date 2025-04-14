package com.dash.leap.global.auth.jwt.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "예기치 못한 서버 오류입니다. 다시 시도해주세요.";

    @ExceptionHandler(DuplicateLoginIdException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handleDuplicateLoginIdException(DuplicateLoginIdException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        log.info("DuplicateLoginIdExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
