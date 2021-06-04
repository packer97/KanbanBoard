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

    public ResourceNotFoundException(String messageKey) {
        super(getLocalizedMessageFromBundle(Locale.US, messageKey));
        localizedMessage = getLocalizedMessageFromBundle(LocaleContextHolder.getLocale(), messageKey);
    }

    public ResourceNotFoundException(String messageKey, Throwable cause) {
        super(getLocalizedMessageFromBundle(Locale.US, messageKey), cause);
        localizedMessage = getLocalizedMessageFromBundle(LocaleContextHolder.getLocale(), messageKey);
    }

    public ResourceNotFoundException(String messageKey, Object... params) {
        super(String.format(getLocalizedMessageFromBundle(Locale.US, messageKey), params));
        localizedMessage = String.format(getLocalizedMessageFromBundle(LocaleContextHolder.getLocale(), messageKey), params);
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    private static String getLocalizedMessageFromBundle(Locale locale, String messageKey){
       return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale).getString(messageKey);
    }
}
