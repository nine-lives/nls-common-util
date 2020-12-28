package com.nls.util;

import com.google.common.collect.ComparisonChain;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LocalDateRange implements Comparable<LocalDateRange>, Iterable<LocalDate> {
    private final LocalDate from;
    private final LocalDate to;

    public LocalDateRange(LocalDate from, LocalDate to) {
        this.from = from.isAfter(to) ? to : from;
        this.to = from.isAfter(to) ? from : to;
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


    public static boolean inRangeSet(Set<LocalDateRange> ranges, LocalDate date) {
        return ranges.stream().anyMatch(r -> r.inRange(date));
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
        LocalDate cursor = from.withDayOfMonth(1);
        while (!cursor.isAfter(to)) {
            list.add(LocalDateRange.forMonth(cursor));
            cursor = cursor.plusMonths(1);
        }
        return list;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public LocalDate getToExclusive() {
        return to.plusDays(1);
    }

    public boolean inRange(LocalDate date) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    public boolean intersects(LocalDateRange that) {
        return !this.from.isAfter(that.to) && !this.to.isBefore(that.from);
    }

    public boolean contains(LocalDateRange that) {
        return !this.from.isAfter(that.from) && !this.to.isBefore(that.to);
    }

    public boolean overlaps(LocalDateRange that) {
        return this.intersects(that) && !this.contains(that) && !that.contains(this);
    }

    public List<LocalDate> getDates() {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate cursor = from; !cursor.isAfter(to); cursor = cursor.plusDays(1)) {
            dates.add(cursor);
        }
        return dates;
    }

    public int getNumberOfDays() {
        return Days.daysBetween(from, to).getDays() + 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocalDateRange that = (LocalDateRange) o;
        return Objects.equals(this.from, that.from)
                && Objects.equals(this.to, that.to);
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        return result;
    }

    @Override
    public int compareTo(LocalDateRange o) {
        return ComparisonChain.start()
                .compare(from, o.from)
                .compare(to, o.to)
                .result();
    }

    @Override
    public String toString() {
        return "LocalDateRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return getDates().iterator();
    }
}
