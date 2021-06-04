package com.group_3.kanbanboard.exception;

public class ProjectNotFoundException extends ResourceNotFoundException{

    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
