package com.nls.util

import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

class LocalDatesSpec extends Specification {
    @Unroll("I can get the max for a set of dates - #left and #right")
    def "I can get the max for a set of dates"() {
        when:
        LocalDate result = LocalDates.max((LocalDate) left, (LocalDate) right);

        then:
        result == expected

        where:
        left                          | right                         | expected
        null                          | null                          | null
        LocalDate.parse('2020-08-01') | null                          | LocalDate.parse('2020-08-01')
        null                          | LocalDate.parse('2020-08-01') | LocalDate.parse('2020-08-01')
        LocalDate.parse('2020-08-01') | LocalDate.parse('2020-08-01') | LocalDate.parse('2020-08-01')
        LocalDate.parse('2020-09-01') | LocalDate.parse('2020-08-01') | LocalDate.parse('2020-09-01')
        LocalDate.parse('2020-08-01') | LocalDate.parse('2020-09-01') | LocalDate.parse('2020-09-01')
    }

    @Unroll("I can roll to the right day in the same week - #date and #day")
    def "I can roll to the right day in the same week"() {
        when:
        LocalDate result = LocalDates.rollToDayOfWeek(LocalDate.parse(date), day);

        then:
        result == LocalDate.parse(expected)

        where:
        date         | day                      | expected
        '2020-11-02' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-03' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-04' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-05' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-06' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-07' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-08' | DateTimeConstants.MONDAY | '2020-11-02'
        '2020-11-02' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-03' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-04' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-05' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-06' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-07' | DateTimeConstants.FRIDAY | '2020-11-06'
        '2020-11-08' | DateTimeConstants.FRIDAY | '2020-11-06'
    }

}
