package com.nls.util;

import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

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

    public static boolean isPositive(BigDecimal value) {
        return value.signum() == 1;
    }

    public static boolean isNegative(BigDecimal value) {
        return value.signum() == -1;
    }

    public static boolean isPositiveOrZero(BigDecimal value) {
        return !isNegative(value);
    }

    public static boolean isNegativeOrZero(BigDecimal value) {
        return !isPositive(value);
    }

    public static <E> BigDecimal sum(Collection<E> collection, Function<E, BigDecimal> mapper) {
        return sum(collection.stream(), mapper);
    }

    public static <E> BigDecimal sum(Stream<E> stream, Function<E, BigDecimal> mapper) {
        return stream.map(mapper).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
