package com.nls.util;

import java.io.Serializable;
import java.util.Locale;

public final class Country implements Comparable<Country>, Serializable {
    private static final long serialVersionUID = 3034071008358122639L;
    private final Locale locale;
    private final String iso;
    private final String code;
    private final String name;

    Country(String iso, String code, String name) {
        this.locale = null;
        this.iso = iso;
        this.code = code;
        this.name = name;
    }

    Country(Locale locale) {
        this.locale = locale;
        this.iso = locale.getISO3Country();
        this.code = locale.getCountry();
        this.name = locale.getDisplayCountry();
    }

    public static Country parse(String iso) {
        return Countries.fromIso(iso);
    }

    public String getIso() {
        return iso;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getName(Locale locale) {
        return this.locale == null || locale == null ? name : this.locale.getDisplayCountry(locale);
    }

    @Override
    public int hashCode() {
        return iso.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Country)) {
            return false;
        }
        return iso.equals(((Country) obj).iso);
    }

    @Override
    public String toString() {
        return name + " " + iso;
    }

    public static Country valueOf(String codeOrIso) {
        Country country = Countries.fromIso(codeOrIso.trim());
        if (country == null) {
            country = Countries.fromCode(codeOrIso.trim());
        }

        if (country == null) {
            throw new IllegalStateException("Invalid country code " + codeOrIso);
        }

        return country;
    }

    @Override
    public int compareTo(Country o) {
        return name.compareToIgnoreCase(o.name);
    }
}
