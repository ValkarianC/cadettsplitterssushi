package com.example.cadettsplitterssushi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceNotFoundException extends RuntimeException {

    private Object value;
    private String field;
    private String object;

    public ResourceNotFoundException(String object, String field, Object value) {
        super(String.format("%s with %s '%s' not found.", object, field, value));
        this.value = value;
        this.field = field;
        this.object = object;
    }

    public String getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }
}
