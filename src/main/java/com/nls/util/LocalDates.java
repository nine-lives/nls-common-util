package com.nls.util;

import com.google.common.base.Strings;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public final class LocalDates {
    private LocalDates() {

    }

    public static <E> LocalDate max(Collection<E> collection, Function<E, LocalDate> mapper) {
        return max(collection.stream(), mapper);
    }

    public static <E> LocalDate max(Stream<E> stream, Function<E, LocalDate> mapper) {
        return stream.map(mapper).filter(Objects::nonNull).max(Comparator.comparing(Function.identity())).orElse(null);
    }

    public static LocalDate max(LocalDate left, LocalDate right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return left.isAfter(right) ? left : right;
    }

    public static <E> LocalDate min(Collection<E> collection, Function<E, LocalDate> mapper) {
        return min(collection.stream(), mapper);
    }

    public static <E> LocalDate min(Stream<E> stream, Function<E, LocalDate> mapper) {
        return stream.map(mapper).filter(Objects::nonNull).min(Comparator.comparing(Function.identity())).orElse(null);
    }

    public static LocalDate min(LocalDate left, LocalDate right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return right.isAfter(left) ? left : right;
    }

    public static LocalDate rollToDayOfWeek(LocalDate d, int dayOfWeek) {
        if (d.getDayOfWeek() == dayOfWeek) {
            return d;
        }

        LocalDate d2 = d.withDayOfWeek(dayOfWeek);
        if (d.getDayOfWeek() < dayOfWeek && d.isAfter(d2)) {
            return d2.minusWeeks(1);
        } else if (d.getDayOfWeek() > dayOfWeek && d.isBefore(d2)) {
            return d2.plusWeeks(1);
        }

        return d2;
    }

    public static LocalDate tryParse(String string) {
        if (Strings.isNullOrEmpty(string)) {
            return null;
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("yyyy-MM-dd"));
        } catch (Throwable ignore) {
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("yyyy/MM/dd"));
        } catch (Throwable ignore) {
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("dd/MM/yyyy"));
        } catch (Throwable ignore) {
        }

        try {
            return LocalDate.parse(string.trim(), DateTimeFormat.forPattern("MM/dd/yyyy"));
        } catch (Throwable ignore) {
            return null;
        }
    }
}
