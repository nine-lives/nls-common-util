package com.nls.util;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Triplet<T1, T2, T3> implements Serializable {
    private final T1 item1;
    private final T2 item2;
    private final T3 item3;

    public Triplet(T1 item1, T2 item2, T3 item3) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
    }

    public Triplet(Map.Entry<T1, Tuple<T2, T3>> mapEntry) {
        this.item1 = mapEntry.getKey();
        this.item2 = mapEntry.getValue().getKey();
        this.item3 = mapEntry.getValue().getValue();
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    public T3 getItem3() {
        return item3;
    }

    public static <T1, T2, T3> List<Triplet<T1, T2, T3>> fromMap(Map<T1, Tuple<T2, T3>> map) {
        return map.entrySet().stream().map(Triplet::new).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Triplet<?, ?, ?> tuple = (Triplet<?, ?, ?>) o;

        return Objects.equal(item1, tuple.item1)
                && Objects.equal(item2, tuple.item2)
                && Objects.equal(item3, tuple.item3);

    }

    @Override
     public int hashCode() {
        return Objects.hashCode(item1, item2, item3);
    }
}
