package com.nls.util;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Locales {
    private static LocaleResolver defaultResolver = new LocaleResolver("en");

    private Locales() {

    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static List<Locale> get() {
        return defaultResolver.getLocales();
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static LocaleResolver getDefaultResolver() {
        return defaultResolver;
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static void setDefaultResolver(LocaleResolver resolver) {
        defaultResolver = resolver;
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static Locale resolve(String acceptLanguage, Locale defaultLocale) {
        return defaultResolver.resolve(acceptLanguage, defaultLocale);
    }

    public static List<Locale> forLanguageTags(String... languageTags) {
        return Arrays.stream(languageTags)
                .map(Locales::forLanguageTag)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<Locale> forLanguageTags(String languageTags, String separator) {
        return forLanguageTags(languageTags.split(separator));
    }

    public static Locale forLanguageTag(String tag) {
        String normalised = Strings.nullToEmpty(tag).trim().replaceAll("_", "-");
        return tag == null || normalised.isEmpty() ? null : Locale.forLanguageTag(normalised);
    }
}
