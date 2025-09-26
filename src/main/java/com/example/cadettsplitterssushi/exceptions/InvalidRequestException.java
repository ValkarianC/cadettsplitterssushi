package com.example.cadettsplitterssushi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    private String additionalMessage;


    public InvalidRequestException(String additionalMessage) {
        super(String.format("Unable to complete request - %s", additionalMessage));
        this.additionalMessage = additionalMessage;
    }


    public String getAdditionalMessage() {
        return additionalMessage;
    }
}
