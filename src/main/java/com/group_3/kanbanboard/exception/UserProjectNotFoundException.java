package com.group_3.kanbanboard.exception;

import java.util.Locale;

public class UserProjectNotFoundException extends ResourceNotFoundException {

    public UserProjectNotFoundException() {
    }

    public UserProjectNotFoundException(String message) {
        super(message);
    }

    public UserProjectNotFoundException(String message, Locale locale) {
        super(message, locale);
    }

    public UserProjectNotFoundException(String message, Locale locale, Throwable cause) {
        super(message, locale, cause);
    }

    public UserProjectNotFoundException(String messageKey, Locale locale, Object... params) {
        super(messageKey, locale, params);
    }

    public UserProjectNotFoundException(String messageKey, Object... params){
        super(messageKey, params);
    }
}

