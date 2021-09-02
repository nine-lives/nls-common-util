package com.nls.util

import org.joda.time.DateTimeConstants
import org.joda.time.Days
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
        range.contains(ld)
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
        range1.containsValue(ld1)
        !range1.containsValue(ld2)
        !range2.containsValue(ld1)
        range2.containsValue(ld2)
    }

    @Unroll("I can set the week starting on a given day #dow - #date")
    def "I can set the week starting on a given day"() {

        when:
        LocalDateRange range = LocalDateRange.forWeek(LocalDate.parse(date), dow);

        then:
        range.from == LocalDate.parse(expected)

        where:
        dow                         | date         | expected
        DateTimeConstants.MONDAY    | '2021-06-13' | '2021-06-07'
        DateTimeConstants.MONDAY    | '2021-06-20' | '2021-06-14'
        DateTimeConstants.TUESDAY   | '2021-06-13' | '2021-06-08'
        DateTimeConstants.TUESDAY   | '2021-06-20' | '2021-06-15'
        DateTimeConstants.WEDNESDAY | '2021-06-13' | '2021-06-09'
        DateTimeConstants.WEDNESDAY | '2021-06-20' | '2021-06-16'
        DateTimeConstants.THURSDAY  | '2021-06-13' | '2021-06-10'
        DateTimeConstants.THURSDAY  | '2021-06-20' | '2021-06-17'
        DateTimeConstants.FRIDAY    | '2021-06-13' | '2021-06-11'
        DateTimeConstants.FRIDAY    | '2021-06-20' | '2021-06-18'
        DateTimeConstants.FRIDAY    | '2021-06-14' | '2021-06-11'
        DateTimeConstants.SATURDAY  | '2021-06-13' | '2021-06-12'
        DateTimeConstants.SATURDAY  | '2021-06-20' | '2021-06-19'
        DateTimeConstants.SUNDAY    | '2021-06-13' | '2021-06-13'
        DateTimeConstants.SUNDAY    | '2021-06-20' | '2021-06-20'
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

    def "I can get the range values"() {
        when:
        LocalDateRange range = new LocalDateRange(
                LocalDate.parse('2020-08-10'),
                LocalDate.parse('2020-08-10'))

        List<LocalDate> values = range.values

        then:
        values.size() == 1
        values.get(0) == LocalDate.parse('2020-08-10')

        when:
        range = new LocalDateRange(
                LocalDate.parse('2020-08-10'),
                LocalDate.parse('2020-08-13'))

        values = range.values

        then:
        values.size() == 4
        values.get(0) == LocalDate.parse('2020-08-10')
        values.get(1) == LocalDate.parse('2020-08-11')
        values.get(2) == LocalDate.parse('2020-08-12')
        values.get(3) == LocalDate.parse('2020-08-13')
    }

    def "I can iterate the value"() {
        when:
        LocalDateRange range = new LocalDateRange(
                LocalDate.parse('2020-08-10'),
                LocalDate.parse('2020-08-10'))

        List<LocalDate> values = range.iterator().collect()

        then:
        values.iterator().count({ true }) == 1
        values.get(0) == LocalDate.parse('2020-08-10')

        when:
        range = new LocalDateRange(
                LocalDate.parse('2020-08-10'),
                LocalDate.parse('2020-08-13'))

        values = range.iterator().collect()

        then:
        values.iterator().count({ true }) == 4
        values.get(0) == LocalDate.parse('2020-08-10')
        values.get(1) == LocalDate.parse('2020-08-11')
        values.get(2) == LocalDate.parse('2020-08-12')
        values.get(3) == LocalDate.parse('2020-08-13')
    }

    @Unroll("I can expand to week edges - #date")
    def "I can expand to week edges"() {
        when:
        LocalDateRange result = LocalDateRange.forMonth(LocalDate.parse(date)).expandToWeekEdges

        then:
        result.from == LocalDate.parse(fromExpected)
        result.to == LocalDate.parse(toExpected)

        where:
        date         | fromExpected | toExpected
        '2020-09-01' | '2020-08-31' | '2020-10-04'
        '2020-10-01' | '2020-09-28' | '2020-11-01'
        '2020-11-01' | '2020-10-26' | '2020-12-06'
        '2020-12-01' | '2020-11-30' | '2021-01-03'
        '2021-01-01' | '2020-12-28' | '2021-01-31'
        '2021-02-01' | '2021-02-01' | '2021-02-28'
        '2021-03-01' | '2021-03-01' | '2021-04-04'
        '2021-04-01' | '2021-03-29' | '2021-05-02'
        '2021-05-01' | '2021-04-26' | '2021-06-06'
        '2021-06-01' | '2021-05-31' | '2021-07-04'
        '2021-07-01' | '2021-06-28' | '2021-08-01'
        '2021-08-01' | '2021-07-26' | '2021-09-05'
        '2021-09-01' | '2021-08-30' | '2021-10-03'
        '2020-09-15' | '2020-08-31' | '2020-10-04'
        '2020-10-15' | '2020-09-28' | '2020-11-01'
        '2020-11-15' | '2020-10-26' | '2020-12-06'
        '2020-12-15' | '2020-11-30' | '2021-01-03'
        '2021-01-15' | '2020-12-28' | '2021-01-31'
        '2021-02-15' | '2021-02-01' | '2021-02-28'
        '2021-03-15' | '2021-03-01' | '2021-04-04'
        '2021-04-15' | '2021-03-29' | '2021-05-02'
        '2021-05-15' | '2021-04-26' | '2021-06-06'
        '2021-06-15' | '2021-05-31' | '2021-07-04'
        '2021-07-15' | '2021-06-28' | '2021-08-01'
        '2021-08-15' | '2021-07-26' | '2021-09-05'
        '2021-09-15' | '2021-08-30' | '2021-10-03'
        '2020-09-30' | '2020-08-31' | '2020-10-04'
        '2020-10-31' | '2020-09-28' | '2020-11-01'
        '2020-11-30' | '2020-10-26' | '2020-12-06'
        '2020-12-31' | '2020-11-30' | '2021-01-03'
        '2021-01-31' | '2020-12-28' | '2021-01-31'
        '2021-02-28' | '2021-02-01' | '2021-02-28'
        '2021-03-31' | '2021-03-01' | '2021-04-04'
        '2021-04-30' | '2021-03-29' | '2021-05-02'
        '2021-05-31' | '2021-04-26' | '2021-06-06'
        '2021-06-30' | '2021-05-31' | '2021-07-04'
        '2021-07-31' | '2021-06-28' | '2021-08-01'
        '2021-08-31' | '2021-07-26' | '2021-09-05'
        '2021-09-30' | '2021-08-30' | '2021-10-03'
    }

    @Unroll("Get range for weeks starting in month - #month")
    def "Get range for weeks starting in month"() {
        when:
        LocalDateRange result = LocalDateRange.forMonth(LocalDate.parse(month)).alignToWeeksThatStartInRange()

        then:
        result.getFrom() == LocalDate.parse(from)
        result.getTo() == LocalDate.parse(to)

        where:
        month        | from         | to
        '2021-01-01' | '2021-01-04' | '2021-01-31'
        '2021-02-01' | '2021-02-01' | '2021-02-28'
        '2021-03-01' | '2021-03-01' | '2021-04-04'
        '2021-04-01' | '2021-04-05' | '2021-05-02'
        '2021-05-01' | '2021-05-03' | '2021-06-06'
        '2021-06-01' | '2021-06-07' | '2021-07-04'
        '2021-07-01' | '2021-07-05' | '2021-08-01'
        '2021-08-01' | '2021-08-02' | '2021-09-05'
        '2021-09-01' | '2021-09-06' | '2021-10-03'
        '2021-10-01' | '2021-10-04' | '2021-10-31'
        '2021-11-01' | '2021-11-01' | '2021-12-05'
        '2021-12-01' | '2021-12-06' | '2022-01-02'
    }

    @Unroll("Get range for weeks ending in month - #month")
    def "Get range for weeks ending in month"() {
        when:
        LocalDateRange result = LocalDateRange.forMonth(LocalDate.parse(month)).alignToWeeksThatEndInRange()

        then:
        result.getFrom() == LocalDate.parse(from)
        result.getTo() == LocalDate.parse(to)

        where:
        month        | from         | to
        '2021-01-01' | '2020-12-28' | '2021-01-31'
        '2021-02-01' | '2021-02-01' | '2021-02-28'
        '2021-03-01' | '2021-03-01' | '2021-03-28'
        '2021-04-01' | '2021-03-29' | '2021-04-25'
        '2021-05-01' | '2021-04-26' | '2021-05-30'
        '2021-06-01' | '2021-05-31' | '2021-06-27'
        '2021-07-01' | '2021-06-28' | '2021-07-25'
        '2021-08-01' | '2021-07-26' | '2021-08-29'
        '2021-09-01' | '2021-08-30' | '2021-09-26'
        '2021-10-01' | '2021-09-27' | '2021-10-31'
        '2021-11-01' | '2021-11-01' | '2021-11-28'
        '2021-12-01' | '2021-11-29' | '2021-12-26'
    }

    @Unroll("Get range for weeks contained in month - #month")
    def "Get range for weeks contained in month"() {
        when:
        LocalDateRange result = LocalDateRange.forMonth(LocalDate.parse(month)).alignToWeeksContainedInRange()

        then:
        result.getFrom() == LocalDate.parse(from)
        result.getTo() == LocalDate.parse(to)

        where:
        month        | from         | to
        '2021-01-01' | '2021-01-04' | '2021-01-31'
        '2021-02-01' | '2021-02-01' | '2021-02-28'
        '2021-03-01' | '2021-03-01' | '2021-03-28'
        '2021-04-01' | '2021-04-05' | '2021-04-25'
        '2021-05-01' | '2021-05-03' | '2021-05-30'
        '2021-06-01' | '2021-06-07' | '2021-06-27'
        '2021-07-01' | '2021-07-05' | '2021-07-25'
        '2021-08-01' | '2021-08-02' | '2021-08-29'
        '2021-09-01' | '2021-09-06' | '2021-09-26'
        '2021-10-01' | '2021-10-04' | '2021-10-31'
        '2021-11-01' | '2021-11-01' | '2021-11-28'
        '2021-12-01' | '2021-12-06' | '2021-12-26'
    }

    @Unroll("Get weeks in range - #month")
    def "Get weeks in range"() {
        when:
        List<LocalDateRange> result = LocalDateRange.forMonth(LocalDate.parse(month)).alignToWeeksContainedInRange().getWeeks()

        then:
        result.size() == weeks
        result[0].from == LocalDate.parse(from)
        result[weeks - 1].to == LocalDate.parse(to)
        result.each {
            assert it.from.dayOfWeek == DateTimeConstants.MONDAY
            assert it.to.dayOfWeek == DateTimeConstants.SUNDAY
            assert Days.daysBetween(it.from, it.to).days == 6
        }

        where:
        month        | from         | to           | weeks
        '2021-01-01' | '2021-01-04' | '2021-01-31' | 4
        '2021-02-01' | '2021-02-01' | '2021-02-28' | 4
        '2021-03-01' | '2021-03-01' | '2021-03-28' | 4
        '2021-04-01' | '2021-04-05' | '2021-04-25' | 3
        '2021-05-01' | '2021-05-03' | '2021-05-30' | 4
        '2021-06-01' | '2021-06-07' | '2021-06-27' | 3
        '2021-07-01' | '2021-07-05' | '2021-07-25' | 3
        '2021-08-01' | '2021-08-02' | '2021-08-29' | 4
        '2021-09-01' | '2021-09-06' | '2021-09-26' | 3
        '2021-10-01' | '2021-10-04' | '2021-10-31' | 4
        '2021-11-01' | '2021-11-01' | '2021-11-28' | 4
        '2021-12-01' | '2021-12-06' | '2021-12-26' | 3
    }

}
