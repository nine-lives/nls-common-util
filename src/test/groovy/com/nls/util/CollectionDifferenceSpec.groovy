package com.nls.util

import spock.lang.Specification

class CollectionDifferenceSpec extends Specification {
    def "I can get the difference for two lists"() {
        given:
        List<MyClass> oldList = [
                new MyClass(1, "One"),
                new MyClass(2, "Two")]

        List<MyClass> newList = [
                new MyClass(2, "Two"),
                new MyClass(3, "Three")]

        CollectionDifference<MyClass> result = new CollectionDifference<>(oldList, newList);

        when:
        List<MyClass> added = result.added();

        then:
        added.size() == 1
        added.get(0).id == 3

        when:
        List<MyClass> removed = result.removed();

        then:
        removed.size() == 1
        removed.get(0).id == 1

        when:
        List<MyClass> updated = result.updateCollection(oldList);

        then:
        updated.size() == 2
        updated.get(0).id == 2
        updated.get(1).id == 3
    }

    def "I can get the difference for two lists using a mapper"() {
        given:
        List<MyClass> oldList = [
                new MyClass(1, "One"),
                new MyClass(3, "Two")]

        List<MyClass> newList = [
                new MyClass(2, "Two"),
                new MyClass(3, "Three")]

        CollectionDifference<MyClass> result = new CollectionDifference<>(oldList, newList, MyClass::getValue);

        when:
        List<MyClass> added = result.added();

        then:
        added.size() == 1
        added.get(0).id == 3

        when:
        List<MyClass> removed = result.removed();

        then:
        removed.size() == 1
        removed.get(0).id == 1

        when:
        List<MyClass> updated = result.updateCollection(oldList);

        then:
        updated.size() == 2
        updated.get(0).id == 3
        updated.get(1).id == 3
        updated.get(0).value == 'Two'
        updated.get(1).value == 'Three'
    }

    private static class MyClass implements HasId {
        private final Integer id
        private final String value

        MyClass(int id, String value) {
            this.id = id
            this.value = value
        }

        Integer getId() {
            return id
        }

        String getValue() {
            return value
        }

        boolean equals(o) {
            return Objects.equals(id, ((MyClass) o).id);
        }

        int hashCode() {
            return id
        }

        String toString() {
            return value + "(" + id + ")";
        }
    }
}
