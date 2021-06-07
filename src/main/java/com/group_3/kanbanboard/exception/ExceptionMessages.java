package com.group_3.kanbanboard.exception;

import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionMessages {

    private static final String BUNDLE_BASE_NAME = "messages";

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale)
                .getString(messageKey);
    }
}
