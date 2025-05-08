package com.dash.leap.global.aimodel.exception;

import com.dash.leap.global.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AIModelRunExceptionHandler {

    @ExceptionHandler(TextSummaryFailedException.class)
    @ApiResponse(responseCode = "500")
    public ResponseEntity<ExceptionResponse> handleTextSummaryFailedException(TextSummaryFailedException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage());
        log.info("TextSummaryFailedExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.internalServerError().body(exceptionResponse);
    }
}
