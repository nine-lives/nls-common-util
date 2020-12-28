package com.nls.util;

import org.joda.time.LocalDate;

public final class LocalMonth {
    private final LocalDate date;

    public LocalMonth() {
        this(LocalDate.now());
    }

    public LocalMonth(LocalDate date) {
        this.date = date.withDayOfMonth(1);
    }

    public LocalDate getFirstOfMonth() {
        return date;
    }

    public LocalDate getLastOfMonth() {
        return date.plusMonths(1).minusDays(1);
    }

    public LocalDateRange getRange() {
        return LocalDateRange.forMonth(date);
    }

    public LocalMonth plusMonths(int months) {
        return new LocalMonth(date.plusMonths(months));
    }

    public LocalMonth plusYears(int years) {
        return new LocalMonth(date.plusYears(years));
    }

    public String toString() {
        return date.toString("yyyy-MM");
    }
}
