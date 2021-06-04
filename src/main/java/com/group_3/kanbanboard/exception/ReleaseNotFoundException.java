package com.group_3.kanbanboard.exception;

public class ReleaseNotFoundException extends ResourceNotFoundException{

    public ReleaseNotFoundException() {
    }

    public ReleaseNotFoundException(String message) {
        super(message);
    }

    public ReleaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReleaseNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
