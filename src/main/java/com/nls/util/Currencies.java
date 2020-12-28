package com.nls.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class Currencies {
    private static Map<String, Currency> countryCodeToCurrencyMap;

    static {
        build();
    }

    private Currencies() {

    }

    public static Comparator<Currency> getDisplayNameComparator(Locale locale) {
        return (l, r) -> l.getDisplayName(locale).compareToIgnoreCase(r.getDisplayName(locale));
    }

    public static Set<Currency> getCurrencies(List<Country> countries) {
        return countries.stream()
                .map(o -> countryCodeToCurrencyMap.get(o.getCode()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static void build() {
        Map<String, Currency> currencyMap = new HashMap<>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale locale : locales) {
            try {
                Currency currency = Currency.getInstance(locale);
                currencyMap.put(locale.getCountry(), currency);
            } catch (Exception ignore) {
            }
        }
        countryCodeToCurrencyMap = Collections.unmodifiableMap(currencyMap);
    }
}
