package com.nls.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionDifference<T> {
    private final Collection<T> oldCollection;
    private final Collection<T> newCollection;
    private final Function<T, ?> comparisonMapper;

    public CollectionDifference(Collection<T> oldCollection, Collection<T> newCollection) {
        this(oldCollection, newCollection, Function.identity());
    }

    public CollectionDifference(Collection<T> oldCollection, Collection<T> newCollection, Function<T, ?> comparisonMapper) {
        this.oldCollection = oldCollection;
        this.newCollection = newCollection;
        this.comparisonMapper = comparisonMapper;
    }

    public List<T> added() {
        Set<?> newValues = newCollection.stream().map(comparisonMapper).collect(Collectors.toSet());
        Set<?> oldValues = oldCollection.stream().map(comparisonMapper).collect(Collectors.toSet());
        newValues.removeAll(oldValues);
        return newCollection.stream().filter(o -> newValues.contains(comparisonMapper.apply(o))).collect(Collectors.toList());
    }

    public List<T> removed() {
        Set<?> newValues = newCollection.stream().map(comparisonMapper).collect(Collectors.toSet());
        Set<?> oldValues = oldCollection.stream().map(comparisonMapper).collect(Collectors.toSet());
        oldValues.removeAll(newValues);
        return oldCollection.stream().filter(o -> oldValues.contains(comparisonMapper.apply(o))).collect(Collectors.toList());
    }

    public <C extends Collection<T>> C addToCollection(C collection) {
        collection.addAll(added());
        return collection;
    }

    public <C extends Collection<T>> C removeFromCollection(C collection) {
        collection.removeAll(removed());
        return collection;
    }

    public <C extends Collection<T>> C updateCollection(C collection) {
        addToCollection(collection);
        removeFromCollection(collection);
        return collection;
    }
}
