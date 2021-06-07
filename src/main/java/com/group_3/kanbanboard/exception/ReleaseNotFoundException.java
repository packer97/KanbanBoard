package com.group_3.kanbanboard.exception;

import java.util.Locale;

public class ReleaseNotFoundException extends ResourceNotFoundException {

    public ReleaseNotFoundException() {
    }

    public ReleaseNotFoundException(String message) {
        super(message);
    }

    public ReleaseNotFoundException(String message, Locale locale) {
        super(message, locale);
    }

    public ReleaseNotFoundException(String message, Locale locale, Throwable cause) {
        super(message, locale, cause);
    }

    public ReleaseNotFoundException(String messageKey, Locale locale, Object... params) {
        super(messageKey, locale, params);
    }

    public ReleaseNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
