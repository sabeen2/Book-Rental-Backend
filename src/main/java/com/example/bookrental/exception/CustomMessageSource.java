package com.example.bookrental.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CustomMessageSource {
    private final MessageSource messageSource;

    @Autowired
    public CustomMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code) {
        return messageSource.getMessage(code, null, getLocal());
    }

    public String get(String code, Object... objects) {
        return messageSource.getMessage(code, objects, getLocal());
    }

    public Locale getLocal() {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale.getDisplayLanguage().equalsIgnoreCase("English")) {
            locale = new Locale("en", "English");
        }
        return locale;
    }
}