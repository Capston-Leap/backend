package com.dash.leap.domain.mission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRecordRequestException extends RuntimeException {
    public InvalidRecordRequestException(String message) {
        super(message);
    }
}
