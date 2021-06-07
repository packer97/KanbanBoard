package com.group_3.kanbanboard.exception;

import java.util.Locale;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Locale locale) {
        super(message, locale);
    }

    public UserNotFoundException(String message, Locale locale, Throwable cause) {
        super(message, locale, cause);
    }

    public UserNotFoundException(String messageKey, Locale locale, Object... params) {
        super(messageKey, locale, params);
    }

    public UserNotFoundException(String messageKey, Object... params) {
        super(messageKey, params);
    }
}
