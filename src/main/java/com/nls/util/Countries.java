package com.nls.util;

import com.google.common.base.Strings;

import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Countries {

    private static final List<Country> COUNTRIES = build();
    private static final Map<String, Country> ISO_MAP = COUNTRIES.stream().collect(Collectors.toMap(Country::getIso, Function.identity()));
    private static final Map<String, Country> CODE_MAP = COUNTRIES.stream().collect(Collectors.toMap(Country::getCode, Function.identity()));
    private static final Map<String, Country> NAME_MAP = COUNTRIES.stream().collect(Collectors.toMap(o -> Normalizer.normalize(o.getName().toUpperCase(), Normalizer.Form.NFD), Function.identity()));

    private Countries() {

    }

    private static List<Country> build() {
        List<Country> countries = new ArrayList<>();
        for (String country : Locale.getISOCountries()) {
            Locale locale = new Locale("en", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                countries.add(new Country(iso, code, name));
            }
        }
        countries.sort((l, r) -> {
            Collator collator = Collator.getInstance(Locale.ENGLISH);
            return collator.compare(l.getName(), r.getName());
        });
        return Collections.unmodifiableList(countries);
    }

    public static List<Country> get() {
        return COUNTRIES;
    }

    public static Country fromIso(String iso) {
        return ISO_MAP.get(Strings.nullToEmpty(iso).toUpperCase());
    }

    public static Country fromCode(String code) {
        return CODE_MAP.get(Strings.nullToEmpty(code).toUpperCase());
    }

    public static Country fromName(String name) {
        return NAME_MAP.get(Strings.nullToEmpty(name).toUpperCase());
    }
}
