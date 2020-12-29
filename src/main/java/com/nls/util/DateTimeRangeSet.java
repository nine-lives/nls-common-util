package com.nls.util;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class DateTimeRangeSet extends RangeSet<DateTime> {
    public DateTimeRangeSet() {
    }

    public Seconds getSeconds() {
        Seconds total = Seconds.seconds(0);
        for (Range<DateTime> range : getRanges()) {
            total = total.plus(Seconds.secondsBetween(range.getFrom(), range.getTo()));
        }
        return total;
    }

    protected RangeSet<DateTime> make() {
        return new DateTimeRangeSet();
    }
}
