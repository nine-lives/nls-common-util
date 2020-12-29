package com.nls.util;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class Tuple<K, V> implements Serializable {
    private final K key;
    private final V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public K getKey() {
        return key;
    }

    public static <K, V> Map<K, V> toMap(Collection<Tuple<K, V>> tuples) {
        return tuples.stream().filter(t -> t.value != null).collect(Collectors.toMap(Tuple::getKey, Tuple::getValue));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        return Objects.equal(key, tuple.key)
                && Objects.equal(value, tuple.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key, value);
    }
}
