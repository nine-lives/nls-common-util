package com.nls.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.IntFunction;

public final class ArrayUtil {
    private ArrayUtil() {

    }

    public static <T> T[] create(int length, Class<T> type, IntFunction<T> generator) {
        T[] a = (T[]) Array.newInstance(type, length);
        Arrays.parallelSetAll(a, generator);
        return a;
    }
}
