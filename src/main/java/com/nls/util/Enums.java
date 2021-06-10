package com.nls.util;

public final class Enums {
    private Enums() {

    }

    public static <E extends Enum<E>> E tryParse(String name, Class<E> enumClass) {
        try {
            return Enum.valueOf(enumClass, name);
        } catch (Exception ignore) {
            return null;
        }
    }
}
