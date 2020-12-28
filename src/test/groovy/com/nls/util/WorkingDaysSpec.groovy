package com.nls.util

import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

class WorkingDaysSpec extends Specification {

    @Unroll("I can increment working days - #date #days")
    def "I can increment working days"() {
        when:
        LocalDate result = WorkingDays.addWorkingDays(LocalDate.parse(date), days)

        then:
        result == LocalDate.parse(expected)

        where:
        date         | days | expected
        '2019-04-05' | 0    | '2019-04-05'
        '2019-04-05' | 1    | '2019-04-08'
        '2019-04-05' | 2    | '2019-04-09'
        '2019-04-06' | 0    | '2019-04-06'
        '2019-04-06' | 1    | '2019-04-08'
        '2019-04-06' | 2    | '2019-04-09'
        '2019-04-08' | 1    | '2019-04-09'
    }

    @Unroll("I can check if a day is working day - #date")
    def "I can check if a day is working day"() {
        when:
        boolean result = WorkingDays.isWorkingDay(LocalDate.parse(date))

        then:
        result == expected

        where:
        date         | expected
        '2020-12-22' | true
        '2020-12-23' | true
        '2020-12-24' | true
        '2020-12-25' | false
        '2020-12-26' | false
        '2020-12-27' | false
        '2020-12-28' | false
        '2020-12-29' | true
    }

    @Unroll("I can find first working date - #date")
    def "I can find first working date"() {
        when:
        LocalDate result = WorkingDays.getFirstWorkingDay(LocalDate.parse(date))

        then:
        result == LocalDate.parse(expected)

        where:
        date         | expected
        '2020-12-22' | '2020-12-22'
        '2020-12-23' | '2020-12-23'
        '2020-12-24' | '2020-12-24'
        '2020-12-25' | '2020-12-29'
        '2020-12-26' | '2020-12-29'
        '2020-12-27' | '2020-12-29'
        '2020-12-28' | '2020-12-29'
        '2020-12-29' | '2020-12-29'
    }

    @Unroll("I can find last working date - #date")
    def "I can find last working date"() {
        when:
        LocalDate result = WorkingDays.getLastWorkingDay(LocalDate.parse(date))

        then:
        result == LocalDate.parse(expected)

        where:
        date         | expected
        '2020-12-22' | '2020-12-22'
        '2020-12-23' | '2020-12-23'
        '2020-12-24' | '2020-12-24'
        '2020-12-25' | '2020-12-24'
        '2020-12-26' | '2020-12-24'
        '2020-12-27' | '2020-12-24'
        '2020-12-28' | '2020-12-24'
        '2020-12-29' | '2020-12-29'
    }

    def "I can get the number of working days"() {
        when:
        int result = WorkingDays.getWorkingDays(new LocalDateRange(
                LocalDate.parse("2020-12-20"),
                LocalDate.parse("2021-01-03")))

        then:
        result == 7
    }

}
