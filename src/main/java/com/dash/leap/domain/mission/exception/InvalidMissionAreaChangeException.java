package com.dash.leap.domain.mission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMissionAreaChangeException extends RuntimeException {
    public InvalidMissionAreaChangeException(String message) {
        super(message);
    }
}
