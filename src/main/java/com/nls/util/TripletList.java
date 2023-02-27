package com.nls.util;

import java.util.ArrayList;
import java.util.Collection;

public class TripletList<T1, T2, T3> extends ArrayList<Triplet<T1, T2, T3>> {
    public TripletList() {
        super();
    }

    public TripletList(Collection<? extends Triplet<T1, T2, T3>> collection) {
        super(collection);
    }

    public boolean add(T1 v1, T2 v2, T3 v3) {
        return add(new Triplet<>(v1, v2, v3));
    }

    public void add(int index, T1 v1, T2 v2, T3 v3) {
        add(index, new Triplet<>(v1, v2, v3));
    }

    public boolean contains(T1 v1, T2 v2, T3 v3) {
        return super.contains(new Triplet<>(v1, v2, v3));
    }

    public int indexOf( T1 v1, T2 v2, T3 v3) {
        return super.indexOf(new Triplet<>(v1, v2, v3));
    }

    public Triplet<T1, T2, T3> set(int index,  T1 v1, T2 v2, T3 v3) {
        return super.set(index, new Triplet<>(v1, v2, v3));
    }

    public boolean remove(T1 v1, T2 v2, T3 v3) {
        return super.remove(new Triplet<>(v1, v2, v3));
    }
}
