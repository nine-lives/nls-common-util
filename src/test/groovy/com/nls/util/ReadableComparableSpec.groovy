package com.nls.util

import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

class ReadableComparableSpec extends Specification {
    @Unroll("IsEqual - #x and #y")
    def "IsEqual"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isEqual(r) == expectedL
        l.isEqual(y) == expectedL
        r.isEqual(l) == expectedR
        r.isEqual(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | true      | true
        0 | 1 | false     | false
        1 | 0 | false     | false
    }

    def "IsEqualLocalDate"() {
        when:
        ReadableComparable<LocalDate> l = new ReadableComparable(LocalDate.parse(x));
        ReadableComparable<LocalDate> r = new ReadableComparable(LocalDate.parse(y));

        then:
        l.isEqual(r) == expectedL
        l.isEqual(LocalDate.parse(y)) == expectedL
        r.isEqual(l) == expectedR
        r.isEqual(LocalDate.parse(x)) == expectedR

        where:
        x | y | expectedL | expectedR
        '2020-01-02' | '2020-01-02' | true      | true
        '2020-01-01' | '2020-01-02' | false     | false
        '2020-01-02' | '2020-01-01' | false     | false
    }

    @Unroll("IsLessThan - #x and #y")
    def "IsLessThan"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isLessThan(r) == expectedL
        l.isLessThan(y) == expectedL
        r.isLessThan(l) == expectedR
        r.isLessThan(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | false     | false
        0 | 1 | true      | false
        1 | 0 | false     | true
    }

    @Unroll("IsLessThanOrEqual - #x and #y")
    def "IsLessThanOrEqual"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isLessThanOrEqual(r) == expectedL
        l.isLessThanOrEqual(y) == expectedL
        r.isLessThanOrEqual(l) == expectedR
        r.isLessThanOrEqual(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | true      | true
        0 | 1 | true      | false
        1 | 0 | false     | true
    }

    @Unroll("IsBefore - #x and #y")
    def "IsBefore"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isBefore(r) == expectedL
        l.isBefore(y) == expectedL
        r.isBefore(l) == expectedR
        r.isBefore(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | false     | false
        0 | 1 | true      | false
        1 | 0 | false     | true
    }

    @Unroll("IsGreaterThan - #x and #y")
    def "IsGreaterThan"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isGreaterThan(r) == expectedL
        l.isGreaterThan(y) == expectedL
        r.isGreaterThan(l) == expectedR
        r.isGreaterThan(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | false     | false
        0 | 1 | false     | true
        1 | 0 | true      | false
    }

    @Unroll("IsGreaterThanOrEqual - #x and #y")
    def "IsGreaterThanOrEqual"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isGreaterThanOrEqual(r) == expectedL
        l.isGreaterThanOrEqual(y) == expectedL
        r.isGreaterThanOrEqual(l) == expectedR
        r.isGreaterThanOrEqual(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | true      | true
        0 | 1 | false     | true
        1 | 0 | true      | false
    }

    @Unroll("IsAfter - #x and #y")
    def "IsAfter"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.isAfter(r) == expectedL
        l.isAfter(y) == expectedL
        r.isAfter(l) == expectedR
        r.isAfter(x) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | false     | false
        0 | 1 | false     | true
        1 | 0 | true      | false
    }

    @Unroll("CompareTo - #x and #y")
    def "CompareTo"() {
        when:
        ReadableComparable<Integer> l = new ReadableComparable(x);
        ReadableComparable<Integer> r = new ReadableComparable(y);

        then:
        l.compareTo(r) == expectedL
        r.compareTo(l) == expectedR

        where:
        x | y | expectedL | expectedR
        1 | 1 | 0         | 0
        0 | 1 | -1        | 1
        1 | 0 | 1         | -1
    }
}
