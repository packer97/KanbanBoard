package com.group_3.kanbanboard.exception;

public class UserNotFoundException extends ResourceNotFoundException{

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
