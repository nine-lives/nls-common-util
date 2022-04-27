package com.nls.util;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class Region {
    private final Regions.Name code;
    private final String name;
    private final List<Country> countries;

    public Region(Regions.Name code, String name, String... countries) {
        this(Countries.getDefault(), code, name, countries);
    }

    public Region(Countries countryFilter, Regions.Name code, String name, String... countries) {
        this(code, name, Arrays.stream(countries)
                .map(countryFilter::findIso)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    public Region(Regions.Name code, String name, List<Country> countries) {
        this.code = code;
        this.name = name;
        this.countries = Collections.unmodifiableList(countries);
    }

    public String getName() {
        return name;
    }

    public List<Country> getCountries() {
        return countries.stream()
                .filter(o -> Countries.fromIso(o.getIso()) != null)
                .collect(Collectors.toList());
    }

    public List<Country> getCountries(Locale locale) {
        Collator coll = Collator.getInstance(locale);
        coll.setStrength(Collator.PRIMARY);
        return countries.stream()
                .filter(o -> Countries.fromIso(o.getIso()) != null)
                .sorted((l, r) -> coll.compare(l.getName(locale), r.getName(locale)))
                .collect(Collectors.toList());
    }

    public Regions.Name getCode() {
        return code;
    }
}
