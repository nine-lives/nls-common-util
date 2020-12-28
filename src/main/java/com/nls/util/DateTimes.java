package com.nls.util;

import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.function.Function;

public final class DateTimes {
    private DateTimes() {

    }

    public static DateTime max(DateTime left, DateTime right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        return left.isAfter(right) ? left : right;
    }

    public static DateTime min(DateTime left, DateTime right) {
        if (left == null) {
            return left;
        }
        if (right == null) {
            return right;
        }
        return left.isBefore(right) ? left : right;
    }

    public static Comparator<DateTime> comparator(boolean nullsFirst) {
        return comparator(Function.identity(), nullsFirst);
    }

    public static <E> Comparator<E> comparator(Function<E, DateTime> mapper, boolean nullsFirst) {
        return (Comparator<E>) (e1, e2) -> {
            DateTime o1 = mapper.apply(e1);
            DateTime o2 = mapper.apply(e2);
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return nullsFirst ? -1 : 1;
            }
            if (o2 == null) {
                return nullsFirst ? 1 : -1;
            }
            return o1.compareTo(o2);
        };
    }

}
