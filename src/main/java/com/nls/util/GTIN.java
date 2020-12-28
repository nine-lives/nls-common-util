package com.nls.util;

import java.util.Objects;
import java.util.regex.Pattern;

public class GTIN {
    private static final Pattern FORMAT = Pattern.compile("^\\d{12,14}|\\d{8}$");

    private final String gtin;

    public GTIN(String gtin) {
        validate(gtin);
        this.gtin = gtin;
    }

    protected GTIN(String gtin, int length) {
        validate(gtin);
        this.gtin = resize(gtin, length);
    }

    public static boolean valid(String gtin) {
        return gtin != null
                && FORMAT.matcher(gtin).matches()
                && checksumValid(gtin);
    }

    public static String validate(String gtin) {
        Objects.requireNonNull(gtin, "GTIN is null");
        if (!valid(gtin)) {
            throw new IllegalArgumentException("Invalid GTIN - " + gtin);
        }
        return gtin;
    }

    public boolean isGTIN14() {
        return gtin.length() == 14;
    }

    public GTIN14 toGTIN14() {
        return new GTIN14(gtin);
    }

    public boolean isGTIN13() {
        return gtin.length() == 13;
    }

    public GTIN13 toGTIN13() {
        return new GTIN13(gtin);
    }

    public boolean isGTIN13Compatible() {
        return compatible(13);
    }

    public boolean isGTIN12() {
        return gtin.length() == 12;
    }

    public boolean isGTIN12Compatible() {
        return compatible(12);
    }

    public GTIN12 toGTIN12() {
        return new GTIN12(gtin);
    }

    public boolean isGTIN8() {
        return gtin.length() == 8;
    }

    public boolean isGTIN8Compatible() {
        return compatible(8);
    }

    public GTIN8 toGTIN8() {
        return new GTIN8(gtin);
    }

    /**
     * Returns the normal form for a GTIN by shortening it to its shortest possible length and if it is a variable
     * measure item normalizes it by removing the weight or price. Does not validate the check digit and only
     * recalculates it if it is a variable measure item.
     */
    public GTIN normalize() {
        return isVariableMeasureItem()
                ? new GTIN(trim(normalizeVariableMeasureItem()))
                : new GTIN(trim(gtin));
    }

    @Override
    public String toString() {
        return gtin;
    }

    private boolean compatible(int length) {
        try {
            resize(gtin, length);
            return true;
        } catch (IllegalArgumentException ignore) {
            return false;
        }
    }

    private static String trim(String gtin) {
        return gtin.replaceAll("^0*", "");
    }

    private static String resize(String gtin, int length) {
        Objects.requireNonNull(gtin, "GTIN is null");
        String resized = trim(gtin);
        if (resized.length() > length) {
            throw new IllegalArgumentException("Couldn't convert to GTIN" + length);
        }
        return resized.length() < length
                ? String.format("%1$0%2$s" + (length - resized.length()) + "d", 0, resized)
                : resized;
    }

    /**
     * Determines if a GTIN is a variable measure item (contains either weight or price).
     *
     * @link http://www.gs1.se/sv/vara-standarder/identifiera/Viktvarunummer/
     * @link http://www.gs1.se/globalassets/pub/artiklar_med_varierande_vikt.pdf
     */
    private boolean isVariableMeasureItem() {
        if (!isGTIN13Compatible()) {
            return false;
        }

        String s = resize(gtin, 13);
        return s.charAt(0) == '2' && s.charAt(1) >= '0' && s.charAt(1) <= '5';
    }

    private String normalizeVariableMeasureItem() {
        return gtin.substring(0, gtin.length() - 5) + "0000" + checksum(gtin);
    }

    private static char checksum(String gtin) {
        int sum = 0;
        int[] digits = gtin.chars().map(c -> c - '0').toArray();
        for (int i = 1; i < digits.length; ++i) {
            int n = digits[digits.length - i - 1];
            sum += (i & 1) == 0 ? n : 3 * n;
        }
        return Character.forDigit((10 - (sum % 10)) % 10, 10);
    }

    private static boolean checksumValid(String gtin) {
        return checksum(gtin) == gtin.charAt(gtin.length() - 1);
    }

    public static class GTIN8 extends GTIN {
        public GTIN8(String gtin) {
            super(gtin, 8);
        }

        public GTIN8 toGTIN8() {
            return this;
        }
    }

    public static class GTIN12 extends GTIN {
        public GTIN12(String gtin) {
            super(gtin, 12);
        }

        public GTIN12 toGTIN12() {
            return this;
        }
    }

    public static class GTIN13 extends GTIN {
        public GTIN13(String gtin) {
            super(gtin, 13);
        }

        public GTIN13 toGTIN13() {
            return this;
        }
    }

    public static class GTIN14 extends GTIN {
        public GTIN14(String gtin) {
            super(gtin, 14);
        }

        public GTIN14 toGTIN14() {
            return this;
        }
    }
}
