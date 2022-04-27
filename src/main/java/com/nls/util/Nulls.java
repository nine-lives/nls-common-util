package com.nls.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public final class Nulls {

    private Nulls() {

    }

    @SafeVarargs
    public static <T> T coalesce(Supplier<T>... suppliers) {
        return Arrays.stream(suppliers)
                .map(Supplier::get)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @SafeVarargs
    public static <T> T coalesce(T... values) {
        return Arrays.stream(values)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }
}
