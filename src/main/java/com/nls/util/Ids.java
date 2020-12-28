package com.nls.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Ids {

    private Ids() {

    }

    public static <T extends HasId> Set<Integer> collect(Collection<T> identifiables) {
        return identifiables.stream().map(HasId::getId).collect(Collectors.toSet());
    }

    public static <T extends HasId> Map<Integer, T> index(Collection<T> identifiables) {
        return identifiables.stream().collect(Collectors.toMap(HasId::getId, Function.identity()));
    }

    public static <T extends HasId> T get(Collection<T> identifiables, int id) {
        return identifiables.stream().filter(o -> o.getId() != null && o.getId() == id).findFirst().orElse(null);
    }
}
