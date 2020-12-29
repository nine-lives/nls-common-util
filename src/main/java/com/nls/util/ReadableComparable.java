package com.nls.util;

public class ReadableComparable<T extends Comparable<? super T>> implements Comparable<ReadableComparable<T>> {
    private final T comparable;

    public ReadableComparable(T comparable) {
        this.comparable = comparable;
    }

    public T get() {
        return comparable;
    }

    public boolean isEqual(T other) {
        return comparable.compareTo(other) == 0;
    }

    public boolean isLessThan(T other) {
        return comparable.compareTo(other) < 0;
    }

    public boolean isLessThanOrEqual(T other) {
        return comparable.compareTo(other) <= 0;
    }

    public boolean isBefore(T other) {
        return isLessThan(other);
    }

    public boolean isGreaterThan(T other) {
        return comparable.compareTo(other) > 0;
    }

    public boolean isGreaterThanOrEqual(T other) {
        return comparable.compareTo(other) >= 0;
    }

    public boolean isAfter(T other) {
        return isGreaterThan(other);
    }

    public boolean isEqual(ReadableComparable<T> other) {
        return compareTo(other) == 0;
    }

    public boolean isLessThan(ReadableComparable<T> other) {
        return compareTo(other) < 0;
    }

    public boolean isLessThanOrEqual(ReadableComparable<T> other) {
        return compareTo(other) <= 0;
    }

    public boolean isBefore(ReadableComparable<T> other) {
        return isLessThan(other);
    }

    public boolean isGreaterThan(ReadableComparable<T> other) {
        return compareTo(other) > 0;
    }

    public boolean isGreaterThanOrEqual(ReadableComparable<T> other) {
        return compareTo(other) >= 0;
    }

    public boolean isAfter(ReadableComparable<T> other) {
        return isGreaterThan(other);
    }

    @Override
    public int compareTo(ReadableComparable<T> o) {
        return comparable.compareTo(o.comparable);
    }
}
