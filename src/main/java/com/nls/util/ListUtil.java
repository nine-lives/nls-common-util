package com.nls.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ListUtil {

    private ListUtil() {
    }

    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return map(collection.stream(), mapper);
    }

    public static <T, R> List<R> map(Stream<T> stream, Function<T, R> mapper) {
        return stream.map(mapper).collect(Collectors.toList());
    }
}
