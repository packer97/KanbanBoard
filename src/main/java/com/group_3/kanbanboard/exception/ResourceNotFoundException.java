package com.group_3.kanbanboard.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class ResourceNotFoundException extends RuntimeException {

    String messageKey;
    Locale locale;
    Object[] params;


    public ResourceNotFoundException() {
        this.params = new Object[0];
    }

    public ResourceNotFoundException(String messageKey) {
        this(messageKey, LocaleContextHolder.getLocale());
    }

    public ResourceNotFoundException(String messageKey, Locale locale) {
        super(messageKey);
        this.messageKey = messageKey;
        this.locale = locale;
        this.params = new Object[0];
    }

    public ResourceNotFoundException(String messageKey, Locale locale, Throwable cause) {
        this(messageKey, locale);
    }

    public ResourceNotFoundException(String messageKey, Locale locale, Object... params) {
        this(messageKey, locale);
        this.params = params;
    }

    public ResourceNotFoundException(String messageKey, Object... params) {
        this(messageKey, LocaleContextHolder.getLocale(), params);
    }


    @Override
    public String getLocalizedMessage() {
        return String.format(ExceptionMessages.getMessageForLocale(messageKey, locale), params);
    }

}
