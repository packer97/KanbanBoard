package com.group_3.kanbanboard.exception;

import java.util.Locale;

public class ProjectNotFoundException extends ResourceNotFoundException {

    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException(String message, Locale locale) {
        super(message, locale);
    }

    public ProjectNotFoundException(String message, Locale locale, Throwable cause) {
        super(message, locale, cause);
    }

    public ProjectNotFoundException(String messageKey, Locale locale, Object... params) {
        super(messageKey, locale, params);
    }

    public ProjectNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
