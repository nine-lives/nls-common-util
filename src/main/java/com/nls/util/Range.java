package com.nls.util;

import com.google.common.collect.ComparisonChain;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Range<T extends Comparable<? super T>> implements Comparable<Range<T>> {
    private final ReadableComparable<T> from;
    private final ReadableComparable<T> to;

    public Range(T from, T to) {
        this.from = new ReadableComparable<>(from.compareTo(to) > 0 ? to : from);
        this.to = new ReadableComparable<>(from.compareTo(to) > 0 ? from : to);
    }

    public static <T extends Comparable<T>> boolean inRangeSet(Set<Range<T>> ranges, T element) {
        return ranges.stream().anyMatch(r -> r.containsValue(element));
    }

    public T getFrom() {
        return from.get();
    }

    public T getTo() {
        return to.get();
    }

    public boolean containsValue(T element) {
        return from.isLessThanOrEqual(element) && to.isGreaterThanOrEqual(element);
    }

    public boolean intersects(Range<T> that) {
        return from.isLessThanOrEqual(that.to) && to.isGreaterThanOrEqual(that.from);
    }

    public boolean contains(Range<T> that) {
        return from.isLessThanOrEqual(that.from) && to.isGreaterThanOrEqual(that.to);
    }

    public boolean overlaps(Range<T> that) {
        return this.intersects(that) && !this.contains(that) && !that.contains(this);
    }

    public Range<T> merge(Range<T> other) {
        return new Range<>(
                from.get().compareTo(other.from.get()) < 0 ? from.get() : other.from.get(),
                to.get().compareTo(other.to.get()) > 0 ? to.get() : other.to.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Range that = (Range) o;
        return Objects.equals(this.from.get(), that.from.get())
                && Objects.equals(this.to.get(), that.to.get());
    }

    @Override
    public int hashCode() {
        int result = from.get().hashCode();
        result = 31 * result + to.get().hashCode();
        return result;
    }

    @Override
    public int compareTo(Range<T> o) {
        return ComparisonChain.start()
                .compare(from.get(), o.from.get())
                .compare(to.get(), o.to.get())
                .result();
    }

    @Override
    public String toString() {
        return "Range{" +
                "from=" + from.get() +
                ", to=" + to.get() +
                '}';
    }

    protected Range<T> make(T from, T to) {
        return new Range<T>(from, to);
    }
}
