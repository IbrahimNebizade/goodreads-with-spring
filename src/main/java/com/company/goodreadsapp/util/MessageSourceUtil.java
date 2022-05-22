package com.company.goodreadsapp.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageSourceUtil {
    private final MessageSource messageSource;

    private static final Set<Locale> supportedLocales = Set.of(
            Locale.forLanguageTag("az"),
            Locale.ENGLISH
    );

    public String getMessage(String code, Object... params) {
        var locale = LocaleContextHolder.getLocale();
        if (!supportedLocales.contains(locale)) {
            locale = Locale.ENGLISH;
        }
        try {
            return messageSource.getMessage(code, params, Locale.forLanguageTag(locale.getLanguage()));
        } catch (NoSuchMessageException e) {
            return code;
        }
    }
}
