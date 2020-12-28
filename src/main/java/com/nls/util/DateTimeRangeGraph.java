package com.nls.util;

import org.joda.time.Seconds;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DateTimeRangeGraph {
    private final Set<DateTimeRange> ranges = new TreeSet<>();

    public DateTimeRangeGraph() {
    }

    public Set<DateTimeRange> getRanges() {
        return ranges;
    }

    public void add(DateTimeRange range) {
        Set<DateTimeRange> matches = ranges.stream().filter(r -> r.intersects(range)).collect(Collectors.toSet());
        ranges.removeAll(matches);
        DateTimeRange merged = range;
        for (DateTimeRange match : matches) {
            merged = range.merge(match);
        }
        ranges.add(merged);
    }

    public DateTimeRangeGraph merge(DateTimeRangeGraph graph) {
        DateTimeRangeGraph merge = new DateTimeRangeGraph();
        merge.ranges.addAll(ranges);
        for (DateTimeRange range : graph.ranges) {
            merge.add(range);
        }
        return merge;
    }

    public Seconds getSeconds() {
        Seconds total = Seconds.seconds(0);
        for (DateTimeRange range : ranges) {
            total = total.plus(range.getSecondsBetween());
        }
        return total;
    }
}
