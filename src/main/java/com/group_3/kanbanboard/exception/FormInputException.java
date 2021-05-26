package com.group_3.kanbanboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormInputException extends RuntimeException{

  public FormInputException() {
  }

  public FormInputException(String message) {
    super(message);
  }

  public FormInputException(String message, Throwable cause) {
    super(message, cause);
  }
}
