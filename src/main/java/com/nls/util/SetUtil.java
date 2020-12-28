package com.nls.util;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SetUtil {

    private SetUtil() {
    }

    public static <T, R> Set<R> map(Collection<T> collection, Function<T, R> mapper) {
        return map(collection.stream(), mapper);
    }

    public static <T, R> Set<R> map(Stream<T> stream, Function<T, R> mapper) {
        return stream.map(mapper).collect(Collectors.toSet());
    }
}
