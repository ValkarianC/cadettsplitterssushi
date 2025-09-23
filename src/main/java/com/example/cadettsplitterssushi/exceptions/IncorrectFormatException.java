package com.example.cadettsplitterssushi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncorrectFormatException extends RuntimeException {

    private String object;
    private String field;
    private Object value;
    private String format;


    public IncorrectFormatException(String object, String field, Object value, String format    ) {
        super(String.format("%s with %s value '%s' is invalid. Please enter the value in the following format/s: %s", object, field, value, format));
        this.object = object;
        this.field = field;
        this.value = value;
        this.format = format;
    }
}
