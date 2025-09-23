package com.example.cadettsplitterssushi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyFieldException extends RuntimeException {

  private String object;
  private String field;

  public EmptyFieldException(String object, String field) {
        super(String.format("Cannot create a %s object with an empty %s field.", object, field));
        this.object = object;
        this.field = field;
    }

  public String getObject() {
    return object;
  }

  public String getField() {
    return field;
  }
}
