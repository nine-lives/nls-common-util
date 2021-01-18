package com.nls.util;

import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Locales {
    private static LocaleResolver DEFAULT_RESOLVER = new LocaleResolver("en");

    private Locales() {

    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static List<Locale> get() {
        return DEFAULT_RESOLVER.getLocales();
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static LocaleResolver getDefaultResolver() {
        return DEFAULT_RESOLVER;
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static void setDefaultResolver(LocaleResolver resolver) {
        DEFAULT_RESOLVER = resolver;
    }

    /**
     * @deprecated legacy method
     */
    @Deprecated
    public static Locale resolve(String acceptLanguage, Locale defaultLocale) {
        return DEFAULT_RESOLVER.resolve(acceptLanguage, defaultLocale);
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
