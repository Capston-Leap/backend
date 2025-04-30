package com.dash.leap.domain.mission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAccessMissionDetailException extends RuntimeException {
    public ForbiddenAccessMissionDetailException(String message) {
        super(message);
    }
}
