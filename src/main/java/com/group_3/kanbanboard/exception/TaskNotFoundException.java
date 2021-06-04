package com.group_3.kanbanboard.exception;

public class TaskNotFoundException extends ResourceNotFoundException{

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
