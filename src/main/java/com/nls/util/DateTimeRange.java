package com.nls.util;

import com.google.common.collect.ComparisonChain;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.Objects;
import java.util.Set;

public class DateTimeRange implements Comparable<DateTimeRange> {
    private final DateTime from;
    private final DateTime to;

    public DateTimeRange(DateTime from, DateTime to) {
        this.from = DateTimes.min(from, to);
        this.to = DateTimes.max(from, to);
    }

    public Seconds getSecondsBetween() {
        return Seconds.secondsBetween(from, to);
    }

    public static boolean inRangeSet(Set<DateTimeRange> ranges, DateTime date) {
        return ranges.stream().anyMatch(r -> r.inRange(date));
    }

    public DateTime getFrom() {
        return from;
    }

    public DateTime getTo() {
        return to;
    }

    public boolean inRange(DateTime date) {
        return !date.isBefore(from) && !date.isAfter(to);
    }

    public boolean intersects(DateTimeRange range) {
        return inRange(range.from) || range.inRange(from);
    }

    public DateTimeRange merge(DateTimeRange match) {
        return new DateTimeRange(DateTimes.min(from, match.from), DateTimes.max(to, match.to));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DateTimeRange that = (DateTimeRange) o;
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
    public int compareTo(DateTimeRange o) {
        return ComparisonChain.start()
                .compare(from, o.from)
                .compare(to, o.to)
                .result();
    }

    @Override
    public String toString() {
        return "DateTimeRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

}
