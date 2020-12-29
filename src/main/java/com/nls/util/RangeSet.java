package com.nls.util;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class RangeSet<T extends Comparable<? super T>> {
    private final Set<Range<T>> ranges = new TreeSet<>();

    public RangeSet() {
    }

    public RangeSet(Collection<Range<T>> ranges) {
        for (Range<T> range : ranges) {
            add(range);
        }
    }

    public boolean contains(T element) {
        return getContainingRange(element).isPresent();
    }

    public Optional<Range<T>> getContainingRange(T element) {
        return ranges.stream().filter(r -> r.containsValue(element)).findAny();
    }

    public Set<Range<T>> getRanges() {
        return ranges;
    }

    public void add(Range<T> range) {
        Set<Range<T>> matches = ranges.stream().filter(r -> r.intersects(range)).collect(Collectors.toSet());
        ranges.removeAll(matches);

        Range<T> merged = range;
        for (Range<T> match : matches) {
            merged = range.merge(match);
        }

        ranges.add(merged);
    }

    public RangeSet<T> merge(RangeSet<T> graph) {
        RangeSet<T> merge = make();
        merge.ranges.addAll(ranges);
        for (Range<T> range : graph.ranges) {
            merge.add(range);
        }
        return merge;
    }

    protected RangeSet<T> make() {
        return new RangeSet<>();
    }
}
