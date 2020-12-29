package com.nls.util

import spock.lang.Specification
import spock.lang.Unroll

class RangeSpec extends Specification {
    @Unroll("In range set - #from1, #to1, #from2, #to2, #value")
    def "InRangeSet"() {
        when:
        Range<Integer> range1 = new Range<>(from1, to1);
        Range<Integer> range2 = new Range<>(from2, to2);

        then:
        Range.inRangeSet([range1, range2] as Set, value) == expected

        where:
        from1 | to1 | from2 | to2 | value | expected
        1     | 2   | 4     | 5   | 0 | false
        1     | 2   | 4     | 5   | 1 | true
        1     | 2   | 4     | 5   | 2 | true
        1     | 2   | 4     | 5   | 3 | false
        1     | 2   | 4     | 5   | 4 | true
        1     | 2   | 4     | 5   | 5 | true
        1     | 2   | 4     | 5   | 6 | false
    }

    def "GetFrom"() {
        when:
        Range<Integer> range = new Range<>(1, 2);

        then:
        range.from == 1
    }

    def "GetTo"() {
        when:
        Range<Integer> range = new Range<>(1, 2);

        then:
        range.to == 2
    }

    @Unroll("Contains element - #from, #to, #value")
    def "Contains"() {
        when:
        Range<Integer> range = new Range<>(from, to);

        then:
        range.containsValue(value) == expected

        where:
        from | to | value | expected
        1    | 3  | 0     | false
        1    | 3  | 1     | true
        3    | 1  | 2     | true
        1    | 3  | 3     | true
        1    | 3  | 4     | false
        1    | 1  | 0     | false
        1    | 1  | 1     | true
        1    | 1  | 2     | false
    }

    @Unroll("Intersects range - #from1, #to1, #from2, #to2")
    def "Intersects"() {
        when:
        Range<Integer> range1 = new Range<>(from1, to1);
        Range<Integer> range2 = new Range<>(from2, to2);

        then:
        range1.intersects(range2) == expected
        range2.intersects(range1) == expected

        where:
        from1 | to1 | from2 | to2 | expected
        2     | 4   | 0     | 1   | false
        2     | 4   | 0     | 2   | true
        2     | 4   | 0     | 3   | true
        2     | 4   | 0     | 4   | true
        2     | 4   | 0     | 5   | true
        2     | 4   | 2     | 2   | true
        2     | 4   | 2     | 3   | true
        2     | 4   | 2     | 4   | true
        2     | 4   | 3     | 3   | true
        2     | 4   | 3     | 4   | true
        2     | 4   | 3     | 5   | true
        2     | 4   | 4     | 4   | true
        2     | 4   | 1     | 6   | true
        2     | 4   | 2     | 6   | true
        2     | 4   | 3     | 6   | true
        2     | 4   | 4     | 6   | true
        2     | 4   | 5     | 6   | false
    }

    @Unroll("Contains range - #from1, #to1, #from2, #to2")
    def "ContainsRange"() {
        when:
        Range<Integer> range1 = new Range<>(from1, to1);
        Range<Integer> range2 = new Range<>(from2, to2);

        then:
        range1.contains(range2) == expected1
        range2.contains(range1) == expected2

        where:
        from1 | to1 | from2 | to2 | expected1 | expected2
        2     | 4   | 0     | 1   | false     | false
        2     | 4   | 0     | 2   | false     | false
        2     | 4   | 0     | 3   | false     | false
        2     | 4   | 0     | 4   | false     | true
        2     | 4   | 0     | 5   | false     | true
        2     | 4   | 2     | 2   | true      | false
        2     | 4   | 2     | 3   | true      | false
        2     | 4   | 2     | 4   | true      | true
        2     | 4   | 3     | 3   | true      | false
        2     | 4   | 3     | 4   | true      | false
        2     | 4   | 3     | 5   | false     | false
        2     | 4   | 4     | 4   | true      | false
        2     | 4   | 1     | 6   | false     | true
        2     | 4   | 2     | 6   | false     | true
        2     | 4   | 3     | 6   | false     | false
        2     | 4   | 4     | 6   | false     | false
        2     | 4   | 5     | 6   | false     | false
    }

    @Unroll("Overlaps range - #from1, #to1, #from2, #to2")
    def "Overlaps"() {
        when:
        Range<Integer> range1 = new Range<>(from1, to1);
        Range<Integer> range2 = new Range<>(from2, to2);

        then:
        range1.overlaps(range2) == expected
        range2.overlaps(range1) == expected

        where:
        from1 | to1 | from2 | to2 | expected
        2     | 4   | 0     | 1   | false
        2     | 4   | 0     | 2   | true
        2     | 4   | 0     | 3   | true
        2     | 4   | 0     | 4   | false
        2     | 4   | 0     | 5   | false
        2     | 4   | 2     | 2   | false
        2     | 4   | 2     | 3   | false
        2     | 4   | 2     | 4   | false
        2     | 4   | 3     | 3   | false
        2     | 4   | 3     | 4   | false
        2     | 4   | 3     | 5   | true
        2     | 4   | 4     | 4   | false
        2     | 4   | 1     | 6   | false
        2     | 4   | 2     | 6   | false
        2     | 4   | 3     | 6   | true
        2     | 4   | 4     | 6   | true
        2     | 4   | 5     | 6   | false
    }

    def "Equals"() {
        when:
        Range<Integer> range1 = new Range<>(1, 3);
        Range<Integer> range2 = new Range<>(3, 1);
        Range<Integer> range3 = new Range<>(2, 3);

        then:
        range1.equals(range2)
        !range1.equals(range3)
        range2.equals(range1)
        !range2.equals(range3)
        !range3.equals(range1)
        !range3.equals(range2)
        range1.equals(range1)
    }

    def "HashCode"() {
        when:
        Range<Integer> range1 = new Range<>(1, 3);
        Range<Integer> range2 = new Range<>(3, 1);
        Range<Integer> range3 = new Range<>(2, 3);

        then:
        range1.hashCode() == range2.hashCode()
        range1.hashCode() != range3.hashCode()
        range2.hashCode() != range3.hashCode()
    }

    def "CompareTo"() {
        when:
        Range<Integer> range1 = new Range<>(1, 3);
        Range<Integer> range2 = new Range<>(3, 1);
        Range<Integer> range3 = new Range<>(2, 3);
        Range<Integer> range4 = new Range<>(1, 4);

        then:
        range1.compareTo(range2) == 0
        range2.compareTo(range1) == 0
        range1.compareTo(range3) < 0
        range3.compareTo(range1) > 0
        range1.compareTo(range4) < 0
        range4.compareTo(range1) > 0
    }
}
