package com.nls.util;

import com.google.common.base.Strings;

import java.util.List;
import java.util.Locale;

public class LocaleResolver {
    private final List<Locale> locales;
    private final Locale defaultLocale;

    public LocaleResolver(List<Locale> locales) {
        this(Locale.getDefault(), locales);
    }

    public LocaleResolver(Locale defaultLocale, List<Locale> locales) {
        this.locales = locales;
        this.defaultLocale = defaultLocale;
    }

    public LocaleResolver(String... languageTags) {
        this(Locales.forLanguageTags(languageTags));
    }

    public LocaleResolver(Locale defaultLocale, String... languageTags) {
        this(defaultLocale, Locales.forLanguageTags(languageTags));
    }

    public LocaleResolver(String languageTags, String separator) {
        this(Locales.forLanguageTags(languageTags, separator));
    }

    public LocaleResolver(Locale defaultLocale, String languageTags, String separator) {
        this(defaultLocale, Locales.forLanguageTags(languageTags, separator));
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public Locale resolve(String acceptLanguage) {
        return resolve(acceptLanguage, defaultLocale);
    }

    public Locale resolve(String acceptLanguage, Locale defaultLocale) {
        if (Strings.nullToEmpty(acceptLanguage).trim().isEmpty()) {
            return defaultLocale;
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(acceptLanguage);
        Locale locale = Locale.lookup(list, locales);
        return locale == null ? defaultLocale : locale;
    }
}
