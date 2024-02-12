package com.nls.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TupleList<K, V> extends ArrayList<Tuple<K, V>> {
    public TupleList() {
        super();
    }

    public TupleList(Collection<? extends Tuple<K, V>> collection) {
        super(collection);
    }

    public TupleList(Map<K, V> map) {
        this(Tuple.fromMap(map));
    }

    public boolean add(K v1, V v2) {
        return add(new Tuple<>(v1, v2));
    }

    public void add(int index, K v1, V v2) {
        add(index, new Tuple<>(v1, v2));
    }

    public boolean contains(K v1, V v2) {
        return super.contains(new Tuple<>(v1, v2));
    }

    public int indexOf(K v1, V v2) {
        return super.indexOf(new Tuple<>(v1, v2));
    }

    public Tuple<K, V> set(int index,  K v1, V v2) {
        return super.set(index, new Tuple<>(v1, v2));
    }

    public boolean remove(K v1, V v2) {
        return super.remove(new Tuple<>(v1, v2));
    }

    public TupleList<V, K> reverseTuples() {
        return new TupleList<V, K>(this.stream().map(Tuple::reverse).collect(Collectors.toList()));
    }

    public Map<K, V> toMap() {
        return Tuple.toMap(this);
    }

    public Map<K, List<V>> toGroupedMap() {
        return Tuple.toGroupedMap(this);
    }
}
