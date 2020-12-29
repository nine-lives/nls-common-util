package com.nls.util;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class DateTimeRange extends Range<DateTime> {

    public DateTimeRange(DateTime from, DateTime to) {
        super(from, to);
    }

    public Seconds getSecondsBetween() {
        return Seconds.secondsBetween(getFrom(), getTo());
    }

    public DateTimeRange merge(DateTimeRange match) {
        return new DateTimeRange(
                DateTimes.min(getFrom(), match.getFrom()),
                DateTimes.max(getTo(), match.getTo()));
    }

    protected Range<DateTime> make(DateTime from, DateTime to) {
        return new DateTimeRange(from, to);
    }
}
