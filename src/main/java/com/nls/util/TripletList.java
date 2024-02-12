package com.nls.util;

import java.util.ArrayList;
import java.util.Collection;

public class TripletList<T, U, V> extends ArrayList<Triplet<T, U, V>> {
    public TripletList() {
        super();
    }

    public TripletList(Collection<? extends Triplet<T, U, V>> collection) {
        super(collection);
    }

    public boolean add(T v1, U v2, V v3) {
        return add(new Triplet<>(v1, v2, v3));
    }

    public void add(int index, T v1, U v2, V v3) {
        add(index, new Triplet<>(v1, v2, v3));
    }

    public boolean contains(T v1, U v2, V v3) {
        return super.contains(new Triplet<>(v1, v2, v3));
    }

    public int indexOf(T v1, U v2, V v3) {
        return super.indexOf(new Triplet<>(v1, v2, v3));
    }

    public Triplet<T, U, V> set(int index,  T v1, U v2, V v3) {
        return super.set(index, new Triplet<>(v1, v2, v3));
    }

    public boolean remove(T v1, U v2, V v3) {
        return super.remove(new Triplet<>(v1, v2, v3));
    }
}
