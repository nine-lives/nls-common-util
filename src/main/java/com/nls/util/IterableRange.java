package com.nls.util;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class IterableRange<T extends Comparable<? super T>> extends Range<T> implements Iterable<T> {

    public IterableRange(T from, T to) {
        super(from, to);
    }

    protected abstract T step(T element, int count);

    public T getToExclusive() {
        return step(getTo(), 1);
    }

    public List<T> getValues() {
        return StreamSupport.stream(spliterator(), false).collect(Collectors.toList());
    }

    public int getSize() {
        return (int) StreamSupport.stream(spliterator(), true).count();
    }

    @Override
    public Iterator<T> iterator() {
        return new RangeIterator();
    }

    private class RangeIterator implements Iterator<T> {
        private T cursor;

        RangeIterator() {
            cursor = getFrom();
        }

        @Override
        public boolean hasNext() {
            return cursor.compareTo(getTo()) <= 0;
        }

        @Override
        public T next() {
            T current = cursor;
            cursor = step(cursor, 1);
            return current;
        }
    }
}
