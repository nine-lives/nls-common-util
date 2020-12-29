package com.nls.util;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Countries {

    public static final Country NO_COUNTRY = new Country("___", "__", "Global");
    private static final Logger LOGGER = LoggerFactory.getLogger(Countries.class);

    private static Countries defaultCountries = build();

    private final List<Country> countries;
    private final Map<String, Country> isoMap;
    private final Map<String, Country> codeMap;
    private final Map<String, Country> nameMap;
    private final Map<String, Country> wordMap;


    private Countries(List<Country> countries) {
        this.countries = countries;
        this.isoMap = countries.stream().collect(Collectors.toMap(Country::getIso, Function.identity()));
        this.codeMap = countries.stream().collect(Collectors.toMap(Country::getCode, Function.identity()));
        this.nameMap = countries.stream().collect(Collectors.toMap(o -> Normalizer.normalize(o.getName().toUpperCase(), Normalizer.Form.NFD), Function.identity()));
        this.wordMap = buildWordMap(this.nameMap);
    }

    public static Countries build() {
        List<Country> countries = new ArrayList<>();

        for (String country : Locale.getISOCountries()) {
            Locale locale = new Locale("en", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                countries.add(new Country(locale));
            }
        }
        countries.sort(getCountryComparator(Locale.UK));
        countries.add(0, NO_COUNTRY);

        return new Countries(countries);
    }

    public static Countries build(Set<String> countryRestrictions) {
        List<Country> countries = new ArrayList<>();
        Set<String> notfound = new HashSet<>(countryRestrictions);

        for (String country : Locale.getISOCountries()) {
            Locale locale = new Locale("en", country);
            String iso = locale.getISO3Country();
            String code = locale.getCountry();
            String name = locale.getDisplayCountry();

            if (!"".equals(iso) && !"".equals(code) && !"".equals(name) && countryRestrictions.contains(iso)) {
                countries.add(new Country(locale));
                notfound.remove(iso);
            }
        }
        countries.sort(getCountryComparator(Locale.UK));
        countries.add(0, NO_COUNTRY);

        if (!notfound.isEmpty()) {
            LOGGER.error("Country restriction list values that were not found = " + notfound);
        }

        return new Countries(countries);
    }

    public static void setDefault(Countries countries) {
        defaultCountries = countries;
    }

    public static Comparator<Country> getCountryComparator(Locale locale) {
        Collator collator = Collator.getInstance(locale);
        collator.setStrength(Collator.PRIMARY);
        return (l, r) -> {
            if (l == NO_COUNTRY) {
                return -1;
            }
            if (r == NO_COUNTRY) {
                return 1;
            }
            return collator.compare(l.getName(locale), r.getName(locale));
        };
    }

    private static Map<String, Country> buildWordMap(Map<String, Country> nameMap) {
        Set<String> seenWords = new HashSet<>();
        Map<String, Country> wordMap = new HashMap<>();
        for (Map.Entry<String, Country> entry : nameMap.entrySet()) {
            String name = entry.getKey();
            Country country = entry.getValue();

            for (String word : name.split(" ")) {
                if (!seenWords.contains(word)) {
                    seenWords.add(word);
                    wordMap.put(word, country);
                    continue;
                }

                if (!wordMap.containsKey(word)) { // stripped it out already
                    continue;
                }

                if (wordMap.get(word).getCode().equals(country.getCode())) {
                    continue;
                }

                wordMap.remove(word);
            }
        }

        return wordMap;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Country> getCountries(Locale locale) {
        return countries.stream()
                .sorted(getCountryComparator(locale))
                .collect(Collectors.toList());
    }

    public Country findIso(String iso) {
        return isoMap.get(Normalizer.normalize(Strings.nullToEmpty(iso).toUpperCase(), Normalizer.Form.NFD));
    }

    public Country findCode(String code) {
        return codeMap.get(Normalizer.normalize(Strings.nullToEmpty(code).toUpperCase(), Normalizer.Form.NFD));
    }

    public Country findName(String name) {
        return nameMap.get(Normalizer.normalize(Strings.nullToEmpty(name).toUpperCase(), Normalizer.Form.NFD));
    }

    public Country findWordMap(String name) {
        return wordMap.get(Normalizer.normalize(Strings.nullToEmpty(name).toUpperCase(), Normalizer.Form.NFD));
    }

    public Country find(String token) {
        if (fromIso(token) != null) {
            return fromIso(token);
        }

        if (fromCode(token) != null) {
            return fromCode(token);
        }

        if (fromName(token) != null) {
            return fromName(token);
        }

        if (fromWordMap(token) != null) {
            return fromWordMap(token);
        }

        return null;
    }


    public static List<Country> get() {
        return defaultCountries.getCountries();
    }

    public static List<Country> get(Locale locale) {
        return defaultCountries.getCountries(locale);
    }

    public static Country fromIso(String iso) {
        return defaultCountries.findIso(iso);
    }

    public static Country fromCode(String code) {
        return defaultCountries.findCode(code);
    }

    public static Country fromName(String name) {
        return defaultCountries.findName(name);
    }

    public static Country fromWordMap(String name) {
        return defaultCountries.findWordMap(name);
    }

    public static Country from(String token) {
        return defaultCountries.find(token);
    }
}
