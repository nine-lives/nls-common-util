package com.nls.util;

public final class Country {
    private final String iso;
    private final String code;
    private final String name;

    Country(String iso, String code, String name) {
        this.iso = iso;
        this.code = code;
        this.name = name;
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
}
