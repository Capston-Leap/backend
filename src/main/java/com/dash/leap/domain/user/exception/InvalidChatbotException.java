package com.dash.leap.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidChatbotException extends RuntimeException {
    public InvalidChatbotException(String message) {
        super(message);
    }
}
