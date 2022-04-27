package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

import java.util.function.Supplier

class NullsSpec extends Specification {
    def "I can test if an object is null"() {
        when:
        boolean result = Objects.isNull(null);

        then:
        result

        when:
        result = Objects.isNull("Hi");

        then:
        !result
    }

    @Unroll("I can return the first non null - #test")
    def "I can return the first non null"() {
        when:
        Integer result = Nulls.<Integer> coalesce(list as Integer[]);

        then:
        result == expected

        where:
        test     | list               | expected
        "First"  | [1, 2, 3]          | 1
        "Second" | [null, 2, 3]       | 2
        "Third"  | [null, null, 3]    | 3
        "None"   | [null, null, null] | null
    }

    @Unroll("I can return the first non null from a supplier - #test")
    def "I can return the first non null from a supplier"() {
        when:
        Integer result = Nulls.<Integer> coalesce(list as Supplier<Integer>[]);

        then:
        result == expected

        where:
        test     | list                                 | expected
        "First"  | [() -> 1, () -> 2, () -> 3]          | 1
        "Second" | [() -> null, () -> 2, () -> 3]       | 2
        "Third"  | [() -> null, () -> null, () -> 3]    | 3
        "None"   | [() -> null, () -> null, () -> null] | null
    }
}
