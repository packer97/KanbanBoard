package com.group_3.kanbanboard.exception;

public class UserProjectNotFoundException extends RuntimeException{

    public UserProjectNotFoundException() {
    }

    public UserProjectNotFoundException(String message) {
        super(message);
    }

    public UserProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
