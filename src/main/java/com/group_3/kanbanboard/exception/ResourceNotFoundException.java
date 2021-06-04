package com.group_3.kanbanboard.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.ResourceBundle;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class ResourceNotFoundException extends RuntimeException {

    private static final String BUNDLE_BASE_NAME = "messages";

    private String localizedMessage;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String messageKey, Object... params) {
        super(String.format(ResourceBundle.getBundle(BUNDLE_BASE_NAME, Locale.US).getString(messageKey), params));

        localizedMessage = ResourceBundle.getBundle(BUNDLE_BASE_NAME, LocaleContextHolder.getLocale()).getString(messageKey);
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }
}
