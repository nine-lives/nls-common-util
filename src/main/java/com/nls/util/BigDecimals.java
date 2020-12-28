package com.nls.util;

import com.google.common.base.Strings;

import java.math.BigDecimal;

public final class BigDecimals {
    private BigDecimals() {

    }

    public static boolean isZero(BigDecimal d) {
        return BigDecimal.ZERO.compareTo(d) == 0;
    }

    public static boolean isNotZero(BigDecimal d) {
        return !isZero(d);
    }

    public static boolean equal(BigDecimal left, BigDecimal right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.compareTo(right) == 0;
    }

    public static BigDecimal tryParse(String string) {
        try {
            return Strings.isNullOrEmpty(string) ? null : new BigDecimal(string.trim());
        } catch (NumberFormatException ignore) {
            return null;
        }
    }

    public static BigDecimal nullToZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
