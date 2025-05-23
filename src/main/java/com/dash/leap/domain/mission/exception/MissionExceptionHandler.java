package com.dash.leap.domain.mission.exception;

import com.dash.leap.global.exception.ExceptionResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.dash.leap.domain.mission")
public class MissionExceptionHandler {

    @ExceptionHandler(InvalidRecordRequestException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handleInvalidRecordRequestException(InvalidRecordRequestException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        log.info("InvalidRecordRequestExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(InvalidMissionAreaChangeException.class)
    @ApiResponse(responseCode = "400")
    public ResponseEntity<ExceptionResponse> handleInvalidMissionAreaChangeException(InvalidMissionAreaChangeException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
        log.info("InvalidMissionAreaChangeExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(ForbiddenAccessMissionDetailException.class)
    @ApiResponse(responseCode = "403")
    public ResponseEntity<ExceptionResponse> handleForbiddenAccessMissionDetailException(ForbiddenAccessMissionDetailException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.FORBIDDEN.toString(), e.getMessage());
        log.info("ForbiddenAccessMissionDetailExceptionResponse: {}", exceptionResponse);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
    }
}
