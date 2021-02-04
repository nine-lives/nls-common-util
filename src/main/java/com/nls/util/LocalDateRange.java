package com.nls.util;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

public class LocalDateRange extends IterableRange<LocalDate> {
    public LocalDateRange(LocalDate from, LocalDate to) {
        super(from, to);
    }


    public static LocalDateRange forMonth(LocalDate date) {
        LocalDate from = date.withDayOfMonth(1);
        return new LocalDateRange(from, from.plusMonths(1).minusDays(1));
    }

    public static LocalDateRange forMonth(YearMonth date) {
        LocalDate from = date.toLocalDate(1);
        return new LocalDateRange(from, from.plusMonths(1).minusDays(1));
    }

    public static LocalDateRange forWeekStartingMonday(LocalDate date) {
        return forWeek(date, DateTimeConstants.MONDAY);
    }

    public static LocalDateRange forWeek(LocalDate date, int startOfWeek) {
        LocalDate from = date.withDayOfWeek(startOfWeek);
        if (date.isBefore(from)) {
            from = from.plusWeeks(1);
        }

        return new LocalDateRange(from, from.plusDays(6));
    }

    public static LocalDateRange forDay(LocalDate day) {
        return new LocalDateRange(day, day);
    }

    public static List<LocalDateRange> getListOfMonths(LocalDate now, int months) {
        List<LocalDateRange> list = new ArrayList<>();
        LocalDate epoch = now.withDayOfMonth(1);
        if (months < 0) {
            for (int i = 0; i > months; --i) {
                list.add(LocalDateRange.forMonth(epoch.plusMonths(i)));
            }
        } else {
            for (int i = 0; i < months; ++i) {
                list.add(LocalDateRange.forMonth(epoch.plusMonths(i)));
            }
        }
        return list;
    }

    public static LocalDateRange lastFullTwelveMonths() {
        LocalDate end = LocalDate.now().withDayOfMonth(1);
        return new LocalDateRange(end.minusMonths(12), end.minusDays(1));
    }

    public List<LocalDateRange> getListOfMonths() {
        List<LocalDateRange> list = new ArrayList<>();
        LocalDate cursor = getFrom().withDayOfMonth(1);
        while (!cursor.isAfter(getTo())) {
            list.add(LocalDateRange.forMonth(cursor));
            cursor = cursor.plusMonths(1);
        }
        return list;
    }

    public List<LocalDate> getDates() {
        return getValues();
    }

    public int getNumberOfDays() {
        return getSize();
    }

    @Override
    public int getSize() {
        return Days.daysBetween(getFrom(), getTo()).getDays() + 1;
    }

    @Override
    protected Range<LocalDate> make(LocalDate from, LocalDate to) {
        return new LocalDateRange(from, to);
    }

    @Override
    protected LocalDate step(LocalDate element, int count) {
        return element.plusDays(count);
    }
}
