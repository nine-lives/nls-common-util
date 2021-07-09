package com.nls.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TupleList<K, V> extends ArrayList<Tuple<K, V>> {
    public TupleList(Collection<? extends Tuple<K, V>> collection) {
        super(collection);
    }

    public TupleList(Map<K, V> map) {
        this(Tuple.fromMap(map));
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
