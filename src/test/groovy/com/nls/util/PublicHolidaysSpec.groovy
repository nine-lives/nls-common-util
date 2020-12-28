package com.nls.util

import org.joda.time.LocalDate
import spock.lang.Specification
import spock.lang.Unroll

class PublicHolidaysSpec extends Specification {
    @Unroll("Is a bank holiday")
    def "IsBankHoliday - #date"() {
        when:
        boolean result = PublicHolidays.isBankHoliday(LocalDate.parse(date))

        then:
        result == expected

        where:
        date         | expected
        '2018-01-01' | true
    }

    def "GetHolidayDaysInRange"() {
        when:
        int result = PublicHolidays.getHolidayDaysInRange(LocalDate.parse(from), LocalDate.parse(to))

        then:
        result == expected

        where:
        from         | to           | expected
        '2018-01-01' | '2018-01-12' | 1
        '2018-01-01' | '2018-12-31' | 8

    }
}
