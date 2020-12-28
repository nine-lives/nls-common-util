package com.nls.util;

import com.google.common.base.Strings;

public final class VatUtil {

    private static final String STANDARD = "(GB)?\\d{9}";
    private static final String BRANCH = "(GB)?\\d{12}";
    private static final String GOVERNMENT = "(GB)?GD\\d{3}";
    private static final String HEALTH = "(GB)?HA\\d{3}";

    private VatUtil() {
    }

    public static boolean valid(String vatNumberRaw) {
        if (vatNumberRaw == null) {
            return false;
        }

        String vatNumber = Strings.nullToEmpty(vatNumberRaw).replaceAll(" ", "");

        if (vatNumber.matches(STANDARD)) {
            return true;
        }
        if (vatNumber.matches(BRANCH)) {
            return true;
        }
        if (vatNumber.matches(GOVERNMENT)) {
            return true;
        }
        return vatNumber.matches(HEALTH);
    }

    public static String prettify(String vatNumberRaw) {
        if (vatNumberRaw == null || !valid(vatNumberRaw)) {
            return vatNumberRaw;
        }

        String vatNumber = Strings.nullToEmpty(vatNumberRaw).replaceAll(" ", "");
        if (!vatNumber.startsWith("GB")) {
            vatNumber = "GB" + vatNumber;
        }

        if (vatNumber.matches(STANDARD)) {
            return vatNumber.substring(0, 5) + " " + vatNumber.substring(5, 9) + " " + vatNumber.substring(9);
        }
        if (vatNumber.matches(BRANCH)) {
            return vatNumber.substring(0, 5) + " " + vatNumber.substring(5, 9) + " " + vatNumber.substring(9, 11) + " " + vatNumber.substring(11);
        }

        return vatNumber;
    }
}
