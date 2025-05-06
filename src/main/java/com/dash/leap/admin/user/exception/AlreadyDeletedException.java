package com.dash.leap.admin.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyDeletedException extends RuntimeException {

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
