package com.group_3.kanbanboard.exception;

public class UserProjectNotFoundException extends ResourceNotFoundException{

    public UserProjectNotFoundException() {
    }

    public UserProjectNotFoundException(String message) {
        super(message);
    }

    public UserProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserProjectNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
