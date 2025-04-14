package com.dash.leap.global.auth.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateLoginIdException extends RuntimeException {

    public DuplicateLoginIdException() {
    }

    public DuplicateLoginIdException(String message) {
        super(message);
    }

    public DuplicateLoginIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateLoginIdException(Throwable cause) {
        super(cause);
    }
}
