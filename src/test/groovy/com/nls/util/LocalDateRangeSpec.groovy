package com.nls.util

import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

class LocalDateRangeSpec extends Specification {

    @Unroll("I can create a date range for the week #date")
    def "I can create a date range for the week"() {

        when:
        LocalDate ld = LocalDate.parse(date)
        LocalDateRange range = LocalDateRange.forWeekStartingMonday(ld)

        then:
        range.from.equals(LocalDate.parse(from))
        range.to.equals(LocalDate.parse(to))
        range.inRange(ld)
        range.equals(LocalDateRange.forWeekStartingMonday(ld))
        range.hashCode() == LocalDateRange.forWeekStartingMonday(ld).hashCode()
        range.compareTo(LocalDateRange.forWeekStartingMonday(ld)) == 0

        where:
        date         | from         | to
        '2017-03-05' | '2017-02-27' | '2017-03-05'
        '2017-03-06' | '2017-03-06' | '2017-03-12'
        '2017-03-07' | '2017-03-06' | '2017-03-12'
        '2017-03-08' | '2017-03-06' | '2017-03-12'
        '2017-03-09' | '2017-03-06' | '2017-03-12'
        '2017-03-10' | '2017-03-06' | '2017-03-12'
        '2017-03-11' | '2017-03-06' | '2017-03-12'
        '2017-03-12' | '2017-03-06' | '2017-03-12'
        '2017-03-13' | '2017-03-13' | '2017-03-19'
    }

    def "I can see when two ranges don't match"() {

        when:
        LocalDate ld1 = LocalDate.parse("2017-03-05")
        LocalDateRange range1 = LocalDateRange.forWeekStartingMonday(ld1)
        LocalDate ld2 = LocalDate.parse("2017-03-06")
        LocalDateRange range2 = LocalDateRange.forWeekStartingMonday(ld2)

        then:
        !range1.equals(range2)
        range1.hashCode() != range2.hashCode()
        range1.compareTo(range2) < 0
        range2.compareTo(range1) > 0
        range1.inRange(ld1)
        !range1.inRange(ld2)
        !range2.inRange(ld1)
        range2.inRange(ld2)
    }

    def "I can check a set of ranges"() {
        given:
        LocalDateRange range1 = new LocalDateRange(LocalDate.parse("2017-03-01"), LocalDate.parse("2017-04-01").minusDays(1))
        LocalDateRange range2 = new LocalDateRange(LocalDate.parse("2017-04-01"), LocalDate.parse("2017-05-01").minusDays(1))
        LocalDateRange range3 = new LocalDateRange(LocalDate.parse("2017-05-01"), LocalDate.parse("2017-06-01").minusDays(1))

        when:
        Set<LocalDateRange> rangeSet1 = [range1, range2, range3]

        then:
        !LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-03-01").minusDays(1));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-03-01"));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-03-01").plusDays(1));

        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-04-01").minusDays(1));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-04-01"));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-04-01").plusDays(1));

        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-05-01").minusDays(1));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-05-01"));
        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-05-01").plusDays(1));

        LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-06-01").minusDays(1));
        !LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-06-01"));
        !LocalDateRange.inRangeSet(rangeSet1, LocalDate.parse("2017-06-01").plusDays(1));

        when:
        Set<LocalDateRange> rangeSet2 = [range1, range3]

        then:
        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-03-01").minusDays(1));
        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-03-01"));
        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-03-01").plusDays(1));

        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-04-01").minusDays(1));
        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-04-01"));
        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-04-01").plusDays(1));

        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-05-01").minusDays(1));
        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-05-01"));
        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-05-01").plusDays(1));

        LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-06-01").minusDays(1));
        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-06-01"));
        !LocalDateRange.inRangeSet(rangeSet2, LocalDate.parse("2017-06-01").plusDays(1));
    }

    def "I can get a range for the last full 12 months"() {
        when:
        LocalDateRange range = LocalDateRange.lastFullTwelveMonths()

        then:
        range.getListOfMonths().size() == 12
        range.to == LocalDate.now().withDayOfMonth(1).minusDays(1)
        range.from == range.to.withDayOfMonth(1).minusMonths(11)
    }

    @Unroll("I can check if date ranges intersect - #test")
    def "I can check if date ranges intersect"() {
        given:
        LocalDateRange rangeA = new LocalDateRange(LocalDate.parse("2020-08-14"), LocalDate.parse("2020-08-21"));

        when:
        LocalDateRange rangeB = new LocalDateRange(
                LocalDate.parse(from),
                LocalDate.parse(to));

        then:
        expected == rangeA.intersects(rangeB);
        expected == rangeB.intersects(rangeA);

        where:
        test                               | from         | to           | expected
        'B before A'                       | '2020-08-09' | '2020-08-13' | false
        'B before A overlap from boundary' | '2020-08-09' | '2020-08-14' | true
        'B before A overlap'               | '2020-08-09' | '2020-08-16' | true
        'B before A overlap to boundary'   | '2020-08-09' | '2020-08-21' | true
        'B before A overlap exact'         | '2020-08-14' | '2020-08-21' | true
        'A contains B'                     | '2020-08-15' | '2020-08-20' | true
        'B after A overlap from boundary'  | '2020-08-14' | '2020-08-24' | true
        'B after A overlap boundary'       | '2020-08-20' | '2020-08-24' | true
        'B after A overlap to boundary'    | '2020-08-21' | '2020-08-24' | true
        'B after A'                        | '2020-08-22' | '2020-08-24' | false
        'B contains A'                     | '2020-08-13' | '2020-08-22' | true
    }

    @Unroll("I can check if date ranges are contained - #test")
    def "I can check if date ranges are contained"() {
        given:
        LocalDateRange rangeA = new LocalDateRange(LocalDate.parse("2020-08-14"), LocalDate.parse("2020-08-21"));

        when:
        LocalDateRange rangeB = new LocalDateRange(
                LocalDate.parse(from),
                LocalDate.parse(to));

        then:
        expected == rangeA.contains(rangeB);
        inverseExpect == rangeB.contains(rangeA);

        where:
        test                               | from         | to           | expected | inverseExpect
        'B before A'                       | '2020-08-09' | '2020-08-13' | false    | false
        'B before A overlap from boundary' | '2020-08-09' | '2020-08-14' | false    | false
        'B before A overlap'               | '2020-08-09' | '2020-08-16' | false    | false
        'B before A overlap to boundary'   | '2020-08-09' | '2020-08-21' | false    | true
        'B before A overlap exact'         | '2020-08-14' | '2020-08-21' | true     | true
        'A contains B'                     | '2020-08-15' | '2020-08-20' | true     | false
        'B after A overlap from boundary'  | '2020-08-14' | '2020-08-24' | false    | true
        'B after A overlap boundary'       | '2020-08-20' | '2020-08-24' | false    | false
        'B after A overlap to boundary'    | '2020-08-21' | '2020-08-24' | false    | false
        'B after A'                        | '2020-08-22' | '2020-08-24' | false    | false
        'B contains A'                     | '2020-08-13' | '2020-08-22' | false    | true
    }

    @Unroll("I can check if date ranges are overlapping - #test")
    def "I can check if date ranges are overlapping"() {
        given:
        LocalDateRange rangeA = new LocalDateRange(LocalDate.parse("2020-08-14"), LocalDate.parse("2020-08-21"));

        when:
        LocalDateRange rangeB = new LocalDateRange(
                LocalDate.parse(from),
                LocalDate.parse(to));

        then:
        expected == rangeA.overlaps(rangeB);
        expected == rangeB.overlaps(rangeA);

        where:
        test                               | from         | to           | expected
        'B before A'                       | '2020-08-09' | '2020-08-13' | false
        'B before A overlap from boundary' | '2020-08-09' | '2020-08-14' | true
        'B before A overlap'               | '2020-08-09' | '2020-08-16' | true
        'B before A overlap to boundary'   | '2020-08-09' | '2020-08-21' | false
        'B before A overlap exact'         | '2020-08-14' | '2020-08-21' | false
        'A contains B'                     | '2020-08-15' | '2020-08-20' | false
        'B after A overlap from boundary'  | '2020-08-14' | '2020-08-24' | false
        'B after A overlap boundary'       | '2020-08-20' | '2020-08-24' | true
        'B after A overlap to boundary'    | '2020-08-21' | '2020-08-24' | true
        'B after A'                        | '2020-08-22' | '2020-08-24' | false
        'B contains A'                     | '2020-08-13' | '2020-08-22' | false
    }

    @Unroll("I can get the number of days between - #test")
    def "I can get the number of days between"() {
        when:
        LocalDateRange range = new LocalDateRange(
                LocalDate.parse(from),
                LocalDate.parse(to));

        then:
        expected == range.numberOfDays;

        where:
        test       | from         | to           | expected
        'Same Day' | '2020-08-09' | '2020-08-09' | 1
        'Next Day' | '2020-08-09' | '2020-08-10' | 2
    }

}
