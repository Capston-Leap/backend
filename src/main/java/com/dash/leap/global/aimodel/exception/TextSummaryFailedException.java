package com.dash.leap.global.aimodel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TextSummaryFailedException extends RuntimeException {

    public TextSummaryFailedException(String message) {
        super(message);
    }
}
