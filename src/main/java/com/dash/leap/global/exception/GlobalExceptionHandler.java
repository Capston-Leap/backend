package com.dash.leap.global.exception;

import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.global.auth.jwt.exception.PasswordMismatchException;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(UnauthorizedException.class)
    @ApiResponse(responseCode = "401")
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED.toString(), e.getMessage());
        log.warn("UnauthorizedExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handlePasswordMismatchException(PasswordMismatchException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        log.warn("PasswordMismatchExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(responseCode = "404")
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage());
        log.warn("NotFoundExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), "요청 형식이 올바르지 않거나 잘못된 값이 포함되어 있습니다.");
        log.warn("HttpMessageNotReadableExceptionResponse: {}", e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
