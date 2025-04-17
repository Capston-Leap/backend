package com.dash.leap.domain.user.exception;

import com.dash.leap.global.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.dash.leap.domain.user")
public class UserExceptionHandler {

    @ExceptionHandler(InvalidChatbotTypeException.class)
    @ApiResponse(responseCode = "400")

    public ResponseEntity<ExceptionResponse> handleInvalidChatbotTypeException(InvalidChatbotTypeException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        log.info("InvalidChatbotTypeExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
