package com.nls.util;

import com.google.common.collect.ImmutableSet;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.util.Set;

public final class WorkingDays {
    private static final Set<Integer> WEEKEND_DAYS = ImmutableSet.of(DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY);

    private WorkingDays() {
    }

    public static boolean isWorkingDay(LocalDate date) {
        return !WEEKEND_DAYS.contains(date.getDayOfWeek()) && !PublicHolidays.isBankHoliday(date);
    }

    public static int getWorkingDays(LocalDateRange range) {
        int count = 0;
        for (LocalDate date : range) {
            if (!isWorkingDay(date)) {
                continue;
            }
            count++;
        }
        return count;
    }

    public static LocalDate getFirstWorkingDay(LocalDate date) {
        LocalDate cursor = date;
        while (!isWorkingDay(cursor)) {
            cursor = cursor.plusDays(1);
        }
        return cursor;
    }

    public static LocalDate getLastWorkingDay(LocalDate date) {
        LocalDate cursor = date;
        while (!isWorkingDay(cursor)) {
            cursor = cursor.minusDays(1);
        }
        return cursor;
    }

    public static LocalDate addWorkingDays(LocalDate start, int days) {
        int count = 0;
        LocalDate result = start;
        while (count < days) {
            result = result.plusDays(1);

            if (!isWorkingDay(result)) {
                continue;
            }

            count++;
        }
        return result;
    }
}
