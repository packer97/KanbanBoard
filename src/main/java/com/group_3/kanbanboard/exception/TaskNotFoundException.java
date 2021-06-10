package com.group_3.kanbanboard.exception;

import java.util.Locale;

public class TaskNotFoundException extends ResourceNotFoundException {

    public TaskNotFoundException() {
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Locale locale) {
        super(message, locale);
    }

    public TaskNotFoundException(String message, Locale locale, Throwable cause) {
        super(message, locale, cause);
    }

    public TaskNotFoundException(String messageKey, Locale locale, Object... params) {
        super(messageKey, locale, params);
    }

    public TaskNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }

}
