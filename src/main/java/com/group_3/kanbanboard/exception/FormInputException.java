package com.group_3.kanbanboard.exception;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Locale;
import java.util.ResourceBundle;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormInputException extends RuntimeException {

    private static final String BUNDLE_BASE_NAME = "messages";
    private String localizedMessage;

    public FormInputException() {
    }

    public FormInputException(String messageKey) {
        super(getLocalizedMessageFromBundle(Locale.US, messageKey));
        localizedMessage = getLocalizedMessageFromBundle(LocaleContextHolder.getLocale(), messageKey);
    }

    public FormInputException(String messageKey, Throwable cause) {
        super(getLocalizedMessageFromBundle(Locale.US, messageKey), cause);
        localizedMessage = getLocalizedMessageFromBundle(LocaleContextHolder.getLocale(), messageKey);
    }

    @Override
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    private static String getLocalizedMessageFromBundle(Locale locale, String messageKey){
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale).getString(messageKey);
    }
}