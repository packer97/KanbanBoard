package com.group_3.kanbanboard.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.ResourceBundle;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    String messageKey;

    Locale locale;

    public ForbiddenException() {
    }

    public ForbiddenException(String messageKey) {
        this(messageKey,  LocaleContextHolder.getLocale());
    }

    public ForbiddenException(String messageKey, Locale locale) {
        super(messageKey);
        this.messageKey = messageKey;
        this.locale = locale;
    }

    public ForbiddenException(String messageKey, Locale locale, Throwable cause) {
        this(messageKey, locale);
    }

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessages.getMessageForLocale(messageKey, locale);
    }

}
