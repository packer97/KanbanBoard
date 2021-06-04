package com.group_3.kanbanboard.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class ResourceNotFoundException extends RuntimeException {

    private static final String res = "message";

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String messageKey, Object... params) {
        super(String.format(messageKey, params));
    }

    @Override
    public String getLocalizedMessage() {
        return null;
    }
}
